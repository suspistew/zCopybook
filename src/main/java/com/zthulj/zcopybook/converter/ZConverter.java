package com.zthulj.zcopybook.converter;


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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ZConverter {

    private static Logger logger = LoggerFactory.getLogger(ZConverter.class);

    private final Pattern nodePattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)");
    private final Pattern levelNbPattern = Pattern.compile("([^ ]*?)( {1})(.*?)");
    private final Pattern simpleValuePattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)( {1})([^ ]*?)( {1})([^ ]*)");
    private final Pattern occursParentPattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)( {1})(OCCURS)( {1})([^ ]*)");
    private final Pattern redefineParentPattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)( {1})(REDEFINES)( {1})([^ ]*)");

    private final Pattern picX_n_Pattern = Pattern.compile("(X|9)(\\({1})([^\\)]*?)(\\){1})");
    private final Pattern picS9_n_v99_Pattern = Pattern.compile("(S9)(\\({1})([^\\)]*?)(\\){1})(V)(9*)");
    private final Pattern picS9_n_Pattern = Pattern.compile("(S9)(\\({1})([^\\)]*?)(\\){1})");
    private final Pattern picX_pattern = Pattern.compile("X*");


    /**
     * @param copybook a file containing the copybook format. Will be read with StandardCharsets.UTF_8 charset
     * @return the root node containing the converted copybook
     * @throws IOException When copybook can't be read
     */
    public RootNode convert(@NotNull File copybook) throws IOException {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");

        return convert(copybook, null);
    }

    /**
     * @param copybook a file containing the copybook format
     * @param charset  to use to read the copybook file. Default is StandardCharsets.UTF_8
     * @return the root node containing the converted copybook
     * @throws IOException When copybook can't be read
     */
    public RootNode convert(@NotNull File copybook, Charset charset) throws IOException {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");

        return convert(FileUtils.readFileToString(copybook, charset != null ? charset : StandardCharsets.UTF_8));
    }


    /**
     * @param copybook a String containing the copybook format
     * @return the root node containing the converted copybook
     */
    public RootNode convert(@NotNull String copybook) {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");

        if (logger.isDebugEnabled())
            logger.debug(String.format("Started the conversion of the copybook : \n[\n%s\n]", copybook));

        String cleanedCopybook = cleanCopybook(copybook);

        RootNode node = NodeFactory.createRootNode();
        ParentNode lastParent = node;

        int nextStart = 0;
        String[] array = cleanedCopybook.split("\\.");
        boolean inANodeToIgnore = false;
        int levelToIgnore = 0;

        for (String line : array) {
            line = line.trim();
            int levelNb = getLevelNbFromLine(line);


            /* WIP : Temporary ignore the redefines */
            if (inANodeToIgnore && levelNb <= levelToIgnore)
                inANodeToIgnore = false;

            Matcher redefineParentMatcher = redefineParentPattern.matcher(line);

            if (redefineParentMatcher.matches()) {
                inANodeToIgnore = true;
                levelToIgnore = levelNb;
            }

            if (inANodeToIgnore) {
                if (logger.isDebugEnabled()) logger.debug("Ignoring line : " + line + ".");
                continue;
            }
            /* End WIP */

            while (lastParent != null && levelNb <= lastParent.getLevelNumber()) {
                if (lastParent instanceof ParentArrayNode)
                    nextStart = ((ParentArrayNode) lastParent).duplicateOccurs(nextStart);
                lastParent = lastParent.getParent();
            }

            Matcher nodeMatcher = nodePattern.matcher(line);
            Matcher occursMatcher = occursParentPattern.matcher(line);
            Matcher valueMatcher = simpleValuePattern.matcher(line);

            if (nodeMatcher.matches())
                lastParent = lastParent.addChildOfTypeParentNode(nodeMatcher.group(3), levelNb);
            else if (occursMatcher.matches()) {
                int occursNb = Integer.parseInt(occursMatcher.group(7));
                lastParent = lastParent.addChildOfTypeParentArrayNode(occursMatcher.group(3), levelNb, occursNb);
            } else if (valueMatcher.matches()) {
                nextStart = addValueNode(lastParent, nextStart, valueMatcher);
            }

        }

        if (lastParent instanceof ParentArrayNode)
            ((ParentArrayNode) lastParent).duplicateOccurs(nextStart);

        return node;
    }

    private String cleanCopybook(String copybook) {
        String result = "";
        for (String line : copybook.split("\\n")) {
            if (lineShouldBeIgnored(line))
                continue;

            result += " " + line.replaceAll(" +", " ");
        }
        return result;
    }

    private int addValueNode(ParentNode lastParent, int nextStart, Matcher valueMatcher) {

        int fieldSize = 0;
        ValueNode.ValueType type = ValueNode.ValueType.STRING;

        String dataType = valueMatcher.group(7);

        Matcher defaultMatcher = picX_n_Pattern.matcher(dataType);
        Matcher signedIntMatcher = picS9_n_Pattern.matcher(dataType);
        Matcher signedFloatMatcher = picS9_n_v99_Pattern.matcher(dataType);
        Matcher picXMatcher = picX_pattern.matcher(dataType);

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

        try {
            lastParent.addChildOfTypeValueNode(valueMatcher.group(3), Coordinates.from(nextStart, nextStart + fieldSize - 1), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nextStart += fieldSize;
        return nextStart;
    }

    private int getLevelNbFromLine(String line) {
        Matcher matcher = levelNbPattern.matcher(line);
        if (!matcher.matches())
            return 0;
        return Integer.parseInt(matcher.group(1));
    }

    private boolean lineShouldBeIgnored(String line) {
        boolean shouldBeIgnored =
                line.startsWith("*") || line.startsWith("88");

        if (shouldBeIgnored && logger.isDebugEnabled())
            logger.debug("Ignoring line : " + line);

        return shouldBeIgnored;
    }


}
