package com.zthulj.zcopybook.model;

import org.junit.Assert;
import org.junit.Test;

public class ParentArrayNodeTest {

    @Test(expected = IllegalArgumentException.class)
    public void factory_occursNumber0_shouldThrowIllegalArg(){
        ParentNode root = Node.createRootNode();
        Node.createParentNodeArray(root, 0,0);
    }

    @Test
    public void isParent_ParentArrayNode_shouldReturnFalse(){
        ParentNode root = Node.createRootNode();
        ParentArrayNode<Object> parent = Node.createParentNodeArray(root, 0,1);

        Assert.assertEquals(true, parent.isParent());
    }

    @Test
    public void constructor_occurs3_shouldHave3ArrayInitialized(){
        ParentNode root = Node.createRootNode();
        ParentArrayNode<Object> parent = Node.createParentNodeArray(root, 0,3);

        Assert.assertEquals(3,parent.getArray().length);
        Assert.assertNotNull(parent.getArray()[0]);
        Assert.assertNotNull(parent.getArray()[1]);
        Assert.assertNotNull(parent.getArray()[2]);
    }

    @Test
    public void addValueChild_occurs3_shouldAddValueInTheFirstArrayNode(){
        ParentNode root = Node.createRootNode();
        ParentArrayNode<Object> parent = Node.createParentNodeArray(root, 0,3);

        ValueNode child = parent.addValueNode("TEST",Coordinates.from(0,1));
        Assert.assertNotNull(child);
        Assert.assertEquals(child, parent.getArray()[0].getChilds().get("TEST"));
        Assert.assertEquals(0,parent.getArray()[1].getChilds().size());
        Assert.assertEquals(0,parent.getArray()[2].getChilds().size());

    }

    @Test
    public void populateOccurs_occurs3OneChild_shouldPoppulateOccursAndCalculateCorrespondingCoordinates(){
        ParentNode root = Node.createRootNode();
        ParentArrayNode<Object> parent = Node.createParentNodeArray(root, 0,3);
        ValueNode child = parent.addValueNode("TEST",Coordinates.from(0,1));

        parent.populateOccurs(2);

        Assert.assertEquals(child, parent.getArray()[0].getChilds().get("TEST"));
        ValueNode child2 = (ValueNode)parent.getArray()[1].getChilds().get("TEST");
        ValueNode child3 = (ValueNode)parent.getArray()[2].getChilds().get("TEST");

        Assert.assertNotNull(child2);
        Assert.assertNotNull(child3);
        Assert.assertEquals(Coordinates.from(2,3), child2.getCoordinates());
        Assert.assertEquals(Coordinates.from(4,5), child3.getCoordinates());
    }
}
