package com.zthulj.zcopybook.converter;

import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.model.*;
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
        Assert.assertEquals(NodeFactory.createRootNode(), node);
    }

    @Test
    public void convert_emptyFile_shouldReturnEmptyModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/empty.cbl"));
        Assert.assertEquals(NodeFactory.createRootNode(), node);
    }

    @Test
    public void convert_aSingleParent_shouldReturnASingleParentModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/singleParent.cbl"));

        ParentNode expected = NodeFactory.createRootNode();
        expected.addChildOfTypeParentNode("CLIENT",1);

        Assert.assertEquals(expected, node);
    }

    @Test
    public void convert_aSingleParentWithComments_shouldReturnASingleParentModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/singleParentWithComments.cbl"));

        ParentNode expected = NodeFactory.createRootNode();
        expected.addChildOfTypeParentNode("CLIENT",1);

        Assert.assertEquals(expected, node);
    }


    @Test
    public void convert_SingleParentOneChildValue_shouldReturnASingleParentModelWithAChild() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/singleParentOneChildValue.cbl"));

        ParentNode expected = NodeFactory.createRootNode();
        ParentNode parent = expected.addChildOfTypeParentNode("CLIENT",1);
        parent.addChildOfTypeValueNode("CLIENT-NAME", Coordinates.from(0,17));

        Assert.assertEquals(expected, node);
    }

    @Test
    public void convert_ManyParentManyChilds_shouldReturnAManyParentWithManyChildModel() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/manyParentManyChilds.cbl"));

        ParentNode rootExpected = NodeFactory.createRootNode();
        ParentNode firstParent = rootExpected.addChildOfTypeParentNode("CLIENT", 1);

        ParentNode commonParent = firstParent.addChildOfTypeParentNode("CLIENT-COMMON-INFOS", 3);
        commonParent.addChildOfTypeValueNode("FIRSTNAME",Coordinates.from(0,17));
        commonParent.addChildOfTypeValueNode("LASTNAME",Coordinates.from(18,29));

        ParentNode advancedParent = firstParent.addChildOfTypeParentNode("CLIENT-ADVANCED-INFOS", 3);
        advancedParent.addChildOfTypeValueNode("GENDER",Coordinates.from(30,31));
        advancedParent.addChildOfTypeValueNode("AGE",Coordinates.from(32,34));

        Assert.assertEquals(rootExpected, node);

    }

    @Test
    public void convert_ParentChildsWith88Level_ShouldIgnore88Level() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/parentManyChildWith88Level.cbl"));
        ParentNode rootExpected = NodeFactory.createRootNode();
        ParentNode firstParent = rootExpected.addChildOfTypeParentNode("CLIENT", 1);

        ParentNode commonParent = firstParent.addChildOfTypeParentNode("CLIENT-COMMON-INFOS", 3);
        commonParent.addChildOfTypeValueNode("FIRSTNAME",Coordinates.from(0,17));
        commonParent.addChildOfTypeValueNode("LASTNAME",Coordinates.from(18,29));
        commonParent.addChildOfTypeValueNode("GENDER",Coordinates.from(30,30));

        Assert.assertEquals(rootExpected, node);

    }

    @Test
    public void convert_ParentWithOccurs_shouldReturnModelWithOccurs() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/oneParentWithOccurs.cbl"));
        ParentNode rootExpected = NodeFactory.createRootNode();
        ParentNode firstParent = rootExpected.addChildOfTypeParentNode("CLIENT",1);
        ParentArrayNode parentArray = firstParent.addChildOfTypeParentArrayNode("CLIENT-COMMON-INFOS",3,3);
        parentArray.addChildOfTypeValueNode("FIRSTNAME", Coordinates.from(0,17));
        parentArray.addChildOfTypeValueNode("LASTNAME", Coordinates.from(18,29));
        parentArray.duplicateOccurs(30);
        Assert.assertEquals(node,rootExpected);
    }

    @Test
    public void convert_NodeWithAParentAndSomeValues_shouldReturnModelOK() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/aParentAndSomeChildsAtSameLevel.cbl"));

        ParentNode rootExpected = NodeFactory.createRootNode();
        ParentNode firstParent = rootExpected.addChildOfTypeParentNode("CLIENT",1);
        ParentNode secondParent = firstParent.addChildOfTypeParentNode("CLIENT-COMMON-INFOS",3);
        secondParent.addChildOfTypeValueNode("FIRSTNAME",Coordinates.from(0,17));
        secondParent.addChildOfTypeValueNode("LASTNAME",Coordinates.from(18,29));
        firstParent.addChildOfTypeValueNode("SOMETHING",Coordinates.from(30,47));
        firstParent.addChildOfTypeValueNode("ELSE",Coordinates.from(48,65));

        Assert.assertEquals(rootExpected,node);


    }

    @Test
    public void convert_NodeWithAParentArrayAndSomeValuesAtSameLevel_shouldReturnModelOK() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/aParentOccursAndSomeChildsAtSameLevel.cbl"));

        ParentNode rootExpected = NodeFactory.createRootNode();
        ParentNode firstParent = rootExpected.addChildOfTypeParentNode("CLIENT",1);
        ParentNode secondParent = firstParent.addChildOfTypeParentArrayNode("CLIENT-COMMON-INFOS",3,2);
        secondParent.addChildOfTypeValueNode("FIRSTNAME",Coordinates.from(0,17));
        secondParent.addChildOfTypeValueNode("LASTNAME",Coordinates.from(18,29));
        ((ParentArrayNode) secondParent).duplicateOccurs(30);

        firstParent.addChildOfTypeValueNode("SOMETHING",Coordinates.from(60,77));
        firstParent.addChildOfTypeValueNode("ELSE",Coordinates.from(78,95));

        Assert.assertEquals(rootExpected,node);
    }

    @Test
    public void convert_NodeWithRedefineField_ShouldIgnoreRedefine() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/aNodeWithARedefine.cbl"));

        ParentNode rootExpected = NodeFactory.createRootNode();
        ParentNode firstParent = rootExpected.addChildOfTypeParentNode("CLIENT",1);
        ParentNode secondParent = firstParent.addChildOfTypeParentNode("CLIENT-COMMON-INFOS",3);
        secondParent.addChildOfTypeValueNode("FIRSTNAME",Coordinates.from(0,17));
        secondParent.addChildOfTypeValueNode("LASTNAME",Coordinates.from(18,29));
        firstParent.addChildOfTypeValueNode("SOMETHING",Coordinates.from(30,47));
        firstParent.addChildOfTypeValueNode("ELSE",Coordinates.from(48,65));

        Assert.assertEquals(rootExpected,node);

    }

    @Test
    public void convert_NodeWithRedefineStruct_ShouldIgnoreRedefine() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/aNodeWithARedefineStruct.cbl"));

        ParentNode rootExpected = NodeFactory.createRootNode();
        ParentNode firstParent = rootExpected.addChildOfTypeParentNode("CLIENT",1);
        ParentNode secondParent = firstParent.addChildOfTypeParentNode("CLIENT-COMMON-INFOS",3);
        secondParent.addChildOfTypeValueNode("FIRSTNAME",Coordinates.from(0,17));
        secondParent.addChildOfTypeValueNode("LASTNAME",Coordinates.from(18,29));
        firstParent.addChildOfTypeValueNode("SOMETHING",Coordinates.from(30,47));
        firstParent.addChildOfTypeValueNode("ELSE",Coordinates.from(48,65));

        Assert.assertEquals(rootExpected,node);
    }

    @Test
    public void convert_NodeWithSignedValue_ShouldDetectSigned() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/simplecopybook.cbl"));

        ParentNode parent = (ParentNode) ((ParentNode)node).getChilds().get("CLIENT");
        ValueNode value = (ValueNode)parent. getChilds().get("SIGNEDINT");

        Assert.assertEquals(ValueNode.ValueType.SIGNED_INT, value.getValueType());

    }

    @Test
    public void convert_NodeWithSignedFloatValue_ShouldDetectSigned() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/simplecopybook.cbl"));

        ParentNode parent = (ParentNode) ((ParentNode)node).getChilds().get("CLIENT");
        ValueNode value = (ValueNode)parent. getChilds().get("SIGNEDFLOAT");

        Assert.assertEquals(ValueNode.ValueType.SIGNED_FLOAT, value.getValueType());

    }

    @Test
    public void convert_NodeWithPicX_ShouldDetectDefault() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/simplecopybook.cbl"));

        ParentNode parent = (ParentNode) ((ParentNode)node).getChilds().get("CLIENT");
        ValueNode value = (ValueNode)parent. getChilds().get("PIC-X");

        Assert.assertEquals(ValueNode.ValueType.STRING, value.getValueType());
    }

    @Test
    public void convert_duplicateKey_ShouldRenameKeys() throws IOException {
        Node node = converter.convert(fileFromResource("copybook/simplecopybook.cbl"));

        ParentNode parent = (ParentNode) ((ParentNode)node).getChilds().get("CLIENT");
        ValueNode value = (ValueNode)parent. getChilds().get("DUPLICATE2");
        ValueNode value2 = (ValueNode)parent. getChilds().get("DUPLICATE3");

        Assert.assertNotNull(value);
        Assert.assertNotNull(value2);

    }



    private File fileFromResource(String path){
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }
}
