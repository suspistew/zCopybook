package com.zthulj.zcopybook.converter;


import com.zthulj.zcopybook.model.Node;
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
    Pattern nodePattern = Pattern.compile("([^ ]*?)( {1})([^ ]*?)");


    /**
     *
     * @param copybook a file containing the copybook format. Will be read with StandardCharsets.UTF_8 charset
     * @return the root node containing the converted copybook
     * @throws IOException When copybook can't be read
     */
    public Node convert(@NotNull File copybook) throws IOException {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");

        return convert(copybook, null);
    }

    /**
     *
     * @param copybook a file containing the copybook format
     * @param charset to use to read the copybook file. Default is StandardCharsets.UTF_8
     * @return the root node containing the converted copybook
     * @throws IOException When copybook can't be read
     */
    public Node convert(@NotNull File copybook, Charset charset) throws IOException {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");

        return convert(FileUtils.readFileToString(copybook, charset != null ? charset : StandardCharsets.UTF_8));
    }


    /**
     *
     * @param copybook a String containing the copybook format
     * @return the root node containing the converted copybook
     */
    public Node convert(@NotNull String copybook) {
        if (null == copybook)
            throw new IllegalArgumentException("copybook can't be null !");


        if (logger.isDebugEnabled())
            logger.debug(String.format("Started the conversion of the copybook : \n[\n%s\n]",copybook));

        String cleanedCopybook = cleanCopybook(copybook);

        Node node = Node.createRootNode();
        Node lastParent = null;
        String[] array = cleanedCopybook.split("\\.");
        for (String line : array) {
            line = line.trim().replaceAll(" +", " ");

            Matcher nodeMatcher = nodePattern.matcher(line);

            if(nodeMatcher.matches())
                node.addParentNode(nodeMatcher.group(3), nodeMatcher.group(1));
        }

        return node;
    }

    private String cleanCopybook(String copybook) {
        String result = "";
        for (String line: copybook.split("\\n")) {
            line = line.trim();
            if(lineShouldBeIgnored(line))
                continue;
            result += line;
        }
        return result;
    }

    private boolean lineShouldBeIgnored(String line) {
        boolean shouldBeIgnored = line.startsWith("*");

        if (shouldBeIgnored && logger.isDebugEnabled())
            logger.debug("Ignoring line : " + line);

        return shouldBeIgnored;
    }


}
