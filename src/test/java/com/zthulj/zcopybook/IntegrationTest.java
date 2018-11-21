package com.zthulj.zcopybook;

import com.zthulj.zcopybook.engine.ZConverter;
import com.zthulj.zcopybook.engine.ZLoader;
import com.zthulj.zcopybook.model.ZCopyBook;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IntegrationTest {


    private ZLoader loader = new ZLoader();

    @Test
    public void convertToJson_simpleCopybook_correspondingPositional_shouldReturnCorrectJson2() throws IOException {
        ZCopyBook copybook = loader.load(Utils.fileFromResource("integration/integration1.cbl"));
        ZConverter converter = new ZConverter(copybook);

        String path = "integration/integration1.txt";
        String toConvert = FileUtils.readFileToString(Utils.fileFromResource(path), StandardCharsets.UTF_8).replaceAll(" ","X");
        converter.convertToJson(toConvert);
    }
}
