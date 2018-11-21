package com.zthulj.zcopybook.engine;


import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ZLoader {

    private static Logger logger = LoggerFactory.getLogger(ZLoader.class);

    private static final Pattern parentPattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)");
    private static final Pattern levelNbPattern = Pattern.compile("([^ ]*?)( {1})(.*?)");
    private static final Pattern simpleValuePattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)( {1})([^ ]*?)( {1})([^ ]*)");
    private static final Pattern parentArrayPattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)( {1})(OCCURS)( {1})([^ ]*)");
    private static final Pattern redefineParentPattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)( {1})(REDEFINES)( {1})([^ ]*)");

    private static final Pattern picX_n_Pattern = Pattern.compile("(X|9)(\\({1})([^\\)]*?)(\\){1})");
    private static final Pattern picS9_n_v99_Pattern = Pattern.compile("(S9)(\\({1})([^\\)]*?)(\\){1})(V)(9*)");
    private static final Pattern picS9_n_Pattern = Pattern.compile("(S9)(\\({1})([^\\)]*?)(\\){1})");
    private static final Pattern picX_pattern = Pattern.compile("X*");

    class Cursor{
        int cursorPosition;
        ParentNode lastParent;

        public Cursor(ParentNode lastParent) {
            this.lastParent = lastParent;
            this.cursorPosition = 0;
        }
    }

    /**
     * @param copybook a file containing the copybook format. Will be read with StandardCharsets.UTF_8 charset
     * @return the root node containing the converted copybook
     * @throws IOException When copybook can't be read
     */
    public ZCopyBook load(@NotNull final File copybook) throws IOException {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");

        return this.load(copybook, StandardCharsets.UTF_8);
    }

    /**
     * @param copybook a file containing the copybook format
     * @param charset  to use to read the copybook file.
     * @return the root node containing the converted copybook
     * @throws IOException When copybook can't be read
     */
    public ZCopyBook load(@NotNull final File copybook, @NotNull final Charset charset) throws IOException {
        if (null == copybook || null == charset)
            throw new IllegalArgumentException("copybook and charset can't be null !");

        return this.load(FileUtils.readFileToString(copybook, charset));
    }


    /**
     * @param copybook a String containing the copybook format
     * @return the root node containing the converted copybook
     */
    public ZCopyBook load(@NotNull final String copybook) {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");

        if (logger.isDebugEnabled())
            logger.debug(String.format("Started the conversion of the copybook : \n[\n%s\n]", copybook));

        RootNode root = NodeFactory.createRootNode();
        Cursor cursor = new Cursor(root);

       List<String> cleanedCopybook = this.cleanCopybook(copybook);

        /* WIP : Temporary flags to ignore the redefines */
        boolean inANodeToIgnore = false;
        int levelToIgnore = 0;
        /* End WIP */

        for (String line : cleanedCopybook) {
            int levelNb = this.getLevelNbFromLine(line);

            /* WIP : Temporary ignore the redefines */
            if (inANodeToIgnore && levelNb <= levelToIgnore)
                inANodeToIgnore = false;

            Matcher redefineParentMatcher = this.redefineParentPattern.matcher(line);

            if (redefineParentMatcher.matches()) {
                inANodeToIgnore = true;
                levelToIgnore = levelNb;
            }

            if (inANodeToIgnore) {
                logger.debug("Ignoring line : {}.", line);
                continue;
            }
            /* End WIP */

            this.updateCursorWithCurrentLevelNb(cursor, levelNb);

            boolean handled = this.handleSimpleParent(line,cursor,levelNb);

            if(!handled){
                handled = this.handleOccursParent(line,cursor,levelNb);
            }
            if(!handled){
                this.handleValue(line,cursor);
            }
        }

        if (cursor.lastParent instanceof ParentArrayNode)
            ((ParentArrayNode) cursor.lastParent).duplicateOccurs(cursor.cursorPosition);

        return ZCopyBook.from(root);
    }

    private List<String> cleanCopybook(final String copybook) {
        String cleanedLinedCopybook = this.cleanLines(copybook);
        List<String> cleanedCopybook = new ArrayList<>();
        for (String field:cleanedLinedCopybook.split("\\.")) {
            cleanedCopybook.add(field.trim());
        }
        return cleanedCopybook;
    }

    private String cleanLines(final String copybook) {
        StringBuilder builder = new StringBuilder();

        for (String line : copybook.split("\\n")) {
            if (lineShouldBeAdded(line)) {
                builder.append(" ").append(line.trim());
            }
        }
        return builder.toString().replaceAll(" +", " ");
    }

    private boolean lineShouldBeAdded(final String line) {
        boolean shouldBeIgnored =
                line.trim().startsWith("*") || line.trim().startsWith("88");

        if (shouldBeIgnored) {
            logger.debug("Ignoring line : {}", line);
        }

        return !shouldBeIgnored;
    }

    private int getLevelNbFromLine(final String line) {
        Matcher matcher = levelNbPattern.matcher(line);
        if (!matcher.matches())
            return 0;
        return Integer.parseInt(matcher.group(1));
    }

    private void updateCursorWithCurrentLevelNb(Cursor cursor, final int levelNb) {
        while (cursor.lastParent != null && levelNb <= cursor.lastParent.getLevelNumber()) {
            if (cursor.lastParent instanceof ParentArrayNode)
                cursor.cursorPosition = ((ParentArrayNode) cursor.lastParent).duplicateOccurs(cursor.cursorPosition);
            cursor.lastParent = cursor.lastParent.getParentNode();
        }
    }

    private boolean handleSimpleParent(final String line, Cursor cursor, final int levelNb) {
        Matcher nodeMatcher = parentPattern.matcher(line);
        if (nodeMatcher.matches()) {
            ParentNode newParent = NodeFactory.createParentNode(cursor.lastParent, levelNb);
            cursor.lastParent.addChild(newParent, nodeMatcher.group(3));
            cursor.lastParent = newParent;
            return true;
        }
        return false;
    }

    private boolean handleOccursParent(final String line, Cursor cursor, final int levelNb) {
        Matcher occursMatcher = this.parentArrayPattern.matcher(line);
        if (occursMatcher.matches()) {
            int occursNb = Integer.parseInt(occursMatcher.group(7));
            ParentNode newParent = NodeFactory.createParentNodeArray(cursor.lastParent, levelNb, occursNb);
            cursor.lastParent.addChild(newParent, occursMatcher.group(3));
            cursor.lastParent = newParent;
            return true;
        }
        return false;
    }

    private void handleValue(final String line, Cursor cursor) {
        Matcher valueMatcher = this.simpleValuePattern.matcher(line);

        if (valueMatcher.matches()) {
            cursor.cursorPosition = addValueNode(cursor.lastParent, cursor.cursorPosition, valueMatcher);
        }
    }

    private int addValueNode(ParentNode lastParent, int nextStart, Matcher valueMatcher) {

        int fieldSize = 0;
        ValueNode.ValueType type = ValueNode.ValueType.STRING;

        String dataType = valueMatcher.group(7);

        Matcher defaultMatcher = this.picX_n_Pattern.matcher(dataType);
        Matcher signedIntMatcher = this.picS9_n_Pattern.matcher(dataType);
        Matcher signedFloatMatcher = this.picS9_n_v99_Pattern.matcher(dataType);
        Matcher picXMatcher = this.picX_pattern.matcher(dataType);

        if (defaultMatcher.matches()) {
            fieldSize = Integer.parseInt(defaultMatcher.group(3));
        } else if (signedFloatMatcher.matches()) {
            fieldSize = Integer.parseInt(signedFloatMatcher.group(3)) + signedFloatMatcher.group(6).length();
            type = ValueNode.ValueType.SIGNED_FLOAT;
        } else if (signedIntMatcher.matches()) {
            fieldSize = Integer.parseInt(signedIntMatcher.group(3));
            type = ValueNode.ValueType.SIGNED_INT;
        } else if (picXMatcher.matches()) {
            fieldSize = dataType.length();
        }

        Node node = NodeFactory.createValueNode(lastParent, Coordinates.create(nextStart, nextStart + fieldSize), type);
        lastParent.addChild(node, valueMatcher.group(3));

        nextStart += fieldSize;
        return nextStart;
    }


}
