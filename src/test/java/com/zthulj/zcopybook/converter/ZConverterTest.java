package com.zthulj.zcopybook.converter;

import com.zthulj.zcopybook.model.Coordinates;
import com.zthulj.zcopybook.model.Node;
import com.zthulj.zcopybook.model.ParentNode;
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

        ParentNode expected = Node.createRootNode();
        expected.addParentNode("CLIENT",1);

        Assert.assertEquals(expected, node);
    }

    @Test
    public void convert_aSingleParentWithComments_shouldReturnASingleParentModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/singleParentWithComments.cbl"));

        ParentNode expected = Node.createRootNode();
        expected.addParentNode("CLIENT",1);

        Assert.assertEquals(expected, node);
    }


    @Test
    public void convert_SingleParentOneChildValue_shouldReturnASingleParentModelWithAChild() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/singleParentOneChildValue.cbl"));

        ParentNode expected = Node.createRootNode();
        ParentNode parent = expected.addParentNode("CLIENT",1);
        parent.addValueNode("CLIENT-NAME", Coordinates.from(0,17));

        Assert.assertEquals(expected, node);
    }

    @Test
    public void convert_ManyParentManyChilds_shouldReturnAManyParentWithManyChildModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/manyParentManyChilds.cbl"));

        ParentNode rootExpected = Node.createRootNode();
        ParentNode firstParent = rootExpected.addParentNode("CLIENT", 1);

        ParentNode commonParent = firstParent.addParentNode("CLIENT-COMMON-INFOS", 3);
        commonParent.addValueNode("FIRSTNAME",Coordinates.from(0,17));
        commonParent.addValueNode("LASTNAME",Coordinates.from(18,29));

        ParentNode advancedParent = firstParent.addParentNode("CLIENT-ADVANCED-INFOS", 3);
        advancedParent.addValueNode("GENDER",Coordinates.from(30,31));
        advancedParent.addValueNode("AGE",Coordinates.from(32,34));

        Assert.assertEquals(rootExpected, node);

    }

    @Test
    public void convert_ParentChildsWith88Level_ShouldIgnore88Level() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/parentManyChildWith88Level.cbl"));
        ParentNode rootExpected = Node.createRootNode();
        ParentNode firstParent = rootExpected.addParentNode("CLIENT", 1);

        ParentNode commonParent = firstParent.addParentNode("CLIENT-COMMON-INFOS", 3);
        commonParent.addValueNode("FIRSTNAME",Coordinates.from(0,17));
        commonParent.addValueNode("LASTNAME",Coordinates.from(18,29));
        commonParent.addValueNode("GENDER",Coordinates.from(30,30));

        Assert.assertEquals(rootExpected, node);

    }

    private File fileFromResource(String path){
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }
}
