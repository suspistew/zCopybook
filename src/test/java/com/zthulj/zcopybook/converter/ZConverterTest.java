package com.zthulj.zcopybook.converter;

import com.zthulj.zcopybook.model.Coordinates;
import com.zthulj.zcopybook.model.Node;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ZConverterTest {

    ZConverter converter = new ZConverter();

    @Test (expected = IllegalArgumentException.class)
    public void convert_nullString_shouldThrowIllegalArgExc(){
        String s = null ;
        converter.convert(s);
    }

    @Test (expected = IllegalArgumentException.class)
    public void convert_nullFile_shouldThrowIllegalArgExc() throws IOException {
        File f = null;
        converter.convert(f);
    }

    @Test
    public void convert_emptyString_shouldReturnEmptyModel(){
        Node node = converter.convert("");
        Assert.assertEquals(Node.createRootNode(), node);
    }

    @Test
    public void convert_emptyFile_shouldReturnEmptyModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/empty.cbl"));
        Assert.assertEquals(Node.createRootNode(), node);
    }

    @Test
    public void convert_aSingleParent_shouldReturnASingleParentModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/singleParent.cbl"));

        Node expected = Node.createRootNode();
        expected.addParentNode("CLIENT","01");

        Assert.assertEquals(expected, node);
    }

    @Test
    public void convert_aSingleParentWithComments_shouldReturnASingleParentModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/singleParentWithComments.cbl"));

        Node expected = Node.createRootNode();
        expected.addParentNode("CLIENT","01");

        Assert.assertEquals(expected, node);
    }



    private File fileFromResource(String path){
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }
}
