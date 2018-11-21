package com.github.zthulj.zcopybook.engine;

import com.github.zthulj.zcopybook.Utils;
import com.github.zthulj.zcopybook.model.ZCopyBook;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ZConverterTest {

    private ZLoader loader = new ZLoader();

    @Test
    public void convertToJson_simpleCopybook_correspondingPositional_shouldReturnCorrectJson() throws IOException {
        ZCopyBook copybook = loader.load(Utils.fileFromResource("copybook/simplecopybook.cbl"));
        ZConverter converter = new ZConverter(copybook);
        String json = converter.convertToJson("AZERTYUIOPMLKJHGFDAZERTYUIOPML1234567890°POIULKJHGFHDJSKQLITITAJDNCBFGTYRUEIJFKR");
        Assert.assertEquals("{\"CLIENT\":{\"CLIENT-COMMON-INFOS\":{\"FIRSTNAME\":\"AZERTYUIOPMLKJHGFD\",\"LASTNAME\":\"AZERTYUIOPML\"},\"SIGNEDFLOAT\":\"1234567890°POIU\",\"SIGNEDINT\":\"LKJHGFHDJSKQL\",\"PIC-X\":\"I\",\"DUPLICATE\":\"T\",\"DUPLICATE2\":\"I\",\"DUPLICATE3\":\"T\",\"ELSE\":\"AJDNCBFGTYRUEIJFKR\"}}",json);
    }

    @Test (expected = IllegalArgumentException.class)
    public void convertToJson_simpleCopybook_tooShortInput_shouldThrowIllegalArgExeption() throws IOException{
        ZCopyBook copybook = loader.load(Utils.fileFromResource("copybook/simplecopybook.cbl"));
        ZConverter converter = new ZConverter(copybook);
        String json = converter.convertToJson("AZERTYUIOPM");
        Assert.assertEquals("{\"CLIENT\":{\"CLIENT-COMMON-INFOS\":{\"FIRSTNAME\":\"AZERTYUIOPMLKJHGFD\",\"LASTNAME\":\"AZERTYUIOPML\"},\"SIGNEDFLOAT\":\"1234567890°POIU\",\"SIGNEDINT\":\"LKJHGFHDJSKQL\",\"PIC-X\":\"I\",\"DUPLICATE\":\"T\",\"DUPLICATE2\":\"I\",\"DUPLICATE3\":\"T\",\"ELSE\":\"AJDNCBFGTYRUEIJFKR\"}}",json);

    }

    @Test (expected = IllegalArgumentException.class)
    public void convertToJson_simpleCopybook_tooBigInput_shouldThrowIllegalArgExeption() throws IOException{
        ZCopyBook copybook = loader.load(Utils.fileFromResource("copybook/simplecopybook.cbl"));
        ZConverter converter = new ZConverter(copybook);
        String json = converter.convertToJson("AZERTYUIOPMAZERTYUIOPMAZERTYUIOPMAZERTYUIOPMAZERTYUIOPMAZERTYUIOPMAZERTYUIOPMAZERTYUIOPM");
        Assert.assertEquals("{\"CLIENT\":{\"CLIENT-COMMON-INFOS\":{\"FIRSTNAME\":\"AZERTYUIOPMLKJHGFD\",\"LASTNAME\":\"AZERTYUIOPML\"},\"SIGNEDFLOAT\":\"1234567890°POIU\",\"SIGNEDINT\":\"LKJHGFHDJSKQL\",\"PIC-X\":\"I\",\"DUPLICATE\":\"T\",\"DUPLICATE2\":\"I\",\"DUPLICATE3\":\"T\",\"ELSE\":\"AJDNCBFGTYRUEIJFKR\"}}",json);

    }
}
