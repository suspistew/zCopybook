package com.zthulj.zcopybook.model;

import com.zthulj.zcopybook.factory.NodeFactory;
import org.junit.Assert;
import org.junit.Test;

public class ParentArrayNodeTest {



    @Test
    public void isParent_ParentArrayNode_shouldReturnFalse() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 1);

        Assert.assertEquals(true, parent.isParent());
    }

    @Test
    public void constructor_occurs3_shouldHave3ArrayInitialized() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 3);

        Assert.assertEquals(3, parent.getChildArray().length);
        Assert.assertNotNull(parent.getChildArray()[0]);
        Assert.assertNotNull(parent.getChildArray()[1]);
        Assert.assertNotNull(parent.getChildArray()[2]);
    }

    @Test
    public void addValueChild_occurs3_shouldAddValueInTheFirstArrayNode() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 3);

        parent.addChild(NodeFactory.createValueNode(parent, Coordinates.create(0, 1)),"TEST");

        Assert.assertEquals(1, parent.getChildArray()[0].getChilds().size());
        Assert.assertEquals(0, parent.getChildArray()[1].getChilds().size());
        Assert.assertEquals(0, parent.getChildArray()[2].getChilds().size());

    }

    @Test
    public void addParentNode_occurs3_shouldAddTheParentInTheFirstArrayNode() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 3);

        ParentNode parent1 = NodeFactory.createParentNode(parent,1);
        parent.addChild(parent1,"PARENT");

        Assert.assertNotNull(parent1);
        Assert.assertEquals(parent1, parent.getChildArray()[0].getChilds().get("PARENT"));
        Assert.assertEquals(0, parent.getChildArray()[1].getChilds().size());
        Assert.assertEquals(0, parent.getChildArray()[2].getChilds().size());

    }

    @Test
    public void addParentArrayNode_occurs3_shouldAddTheParentArrayInTheFirstArrayNode() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 3);


        ParentArrayNode parent1 = NodeFactory.createParentNodeArray(parent,1,2);
        parent.addChild(parent1,"PARENTARRAY");

        Assert.assertNotNull(parent1);
        Assert.assertEquals(parent1, parent.getChildArray()[0].getChilds().get("PARENTARRAY"));
        Assert.assertEquals(0, parent.getChildArray()[1].getChilds().size());
        Assert.assertEquals(0, parent.getChildArray()[2].getChilds().size());

    }


    @Test
    public void populateOccurs_occurs3OneChild_shouldPopulateOccursWithOKCoords() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 3);
        parent.addChild(NodeFactory.createValueNode(parent,Coordinates.create(0, 1)),"TEST");

        parent.duplicateOccurs(1);

        ValueNode child2 = (ValueNode) parent.getChildArray()[1].getChilds().get("TEST");
        ValueNode child3 = (ValueNode) parent.getChildArray()[2].getChilds().get("TEST");

        Assert.assertNotNull(child2);
        Assert.assertNotNull(child3);
        Assert.assertEquals(Coordinates.create(1, 2), child2.getCoordinates());
        Assert.assertEquals(Coordinates.create(2, 3), child3.getCoordinates());
    }

    @Test
    public void populateOccurs_3occurs_AParentWithTwoChilds_shouldPopulateOccursWithOKCoords() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 3);

        ParentNode parent1 = NodeFactory.createParentNode(parent,1);
                parent.addChild(parent1,"PARENT1");
        parent1.addChild(NodeFactory.createValueNode(parent1,Coordinates.create(0, 2)),"VALUE1");
        parent1.addChild(NodeFactory.createValueNode(parent1,Coordinates.create(2, 4)),"VALUE2");

        parent.duplicateOccurs(4);

        ParentNode parent2 = (ParentNode) parent.getChildArray()[1].getChilds().get("PARENT1");
        ParentNode parent3 = (ParentNode) parent.getChildArray()[2].getChilds().get("PARENT1");


        Assert.assertNotNull(parent2);
        Assert.assertNotNull(parent3);

        Assert.assertEquals(Coordinates.create(4, 6), ((ValueNode) parent2.getChilds().get("VALUE1")).getCoordinates());
        Assert.assertEquals(Coordinates.create(6, 8), ((ValueNode) parent2.getChilds().get("VALUE2")).getCoordinates());

        Assert.assertEquals(Coordinates.create(8, 10), ((ValueNode) parent3.getChilds().get("VALUE1")).getCoordinates());
        Assert.assertEquals(Coordinates.create(10, 12), ((ValueNode) parent3.getChilds().get("VALUE2")).getCoordinates());
    }

    @Test
    public void populateOccurs_3occurs_AParentWithTwoParentChildWithTwoParentChildWithTwoChilds_shouldPopulateOccursWithOKCoords() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root, 0, 3);

        ParentNode<String> parent_1 = NodeFactory.createParentNode(parent,0);
                parent.addChild(parent_1,"PARENT1");
        ParentNode<String> parent_2 = NodeFactory.createParentNode(parent,0);
                parent.addChild(parent_2,"PARENT2");

        ParentNode<String> parent_1_1 = NodeFactory.createParentNode(parent_1,0);
                parent_1.addChild(parent_1_1,"PARENT1_1");
        ParentNode<String> parent_1_2 = NodeFactory.createParentNode(parent_1,0);
                parent_1.addChild(parent_1_2,"PARENT1_2");
        ParentNode<String> parent_2_1 = NodeFactory.createParentNode(parent_2,0);
                parent_2.addChild(parent_2_1,"PARENT2_1");
        ParentNode<String> parent_2_2 = NodeFactory.createParentNode(parent_2,0);
                parent_2.addChild(parent_2_2,"PARENT2_2");

        parent_1_1.addChild(NodeFactory.createValueNode(parent_1_1, Coordinates.create(0, 2)),"VALUE1");
        parent_1_1.addChild(NodeFactory.createValueNode(parent_1_1, Coordinates.create(2, 4)),"VALUE2");
        parent_1_2.addChild(NodeFactory.createValueNode(parent_1_2, Coordinates.create(4, 6)),"VALUE3");
        parent_1_2.addChild(NodeFactory.createValueNode(parent_1_2, Coordinates.create(6, 8)),"VALUE4");
        parent_2_1.addChild(NodeFactory.createValueNode(parent_2_1, Coordinates.create(8, 10)),"VALUE5");
        parent_2_1.addChild(NodeFactory.createValueNode(parent_2_1, Coordinates.create(10, 13)),"VALUE6");
        parent_2_2.addChild(NodeFactory.createValueNode(parent_2_2, Coordinates.create(13, 23)),"VALUE7");
        parent_2_2.addChild(NodeFactory.createValueNode(parent_2_2, Coordinates.create(23, 35)),"VALUE8");

        parent.duplicateOccurs(35);

        // We'll get directly the 8 values of each occurs and test them

        ValueNode value_2_1_1_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT1")).getChilds().get("PARENT1_1")).getChilds().get("VALUE1");
        ValueNode value_2_1_1_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT1")).getChilds().get("PARENT1_1")).getChilds().get("VALUE2");
        ValueNode value_2_1_2_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT1")).getChilds().get("PARENT1_2")).getChilds().get("VALUE3");
        ValueNode value_2_1_2_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT1")).getChilds().get("PARENT1_2")).getChilds().get("VALUE4");
        ValueNode value_2_2_1_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT2")).getChilds().get("PARENT2_1")).getChilds().get("VALUE5");
        ValueNode value_2_2_1_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT2")).getChilds().get("PARENT2_1")).getChilds().get("VALUE6");
        ValueNode value_2_2_2_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT2")).getChilds().get("PARENT2_2")).getChilds().get("VALUE7");
        ValueNode value_2_2_2_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[1].getChilds().get("PARENT2")).getChilds().get("PARENT2_2")).getChilds().get("VALUE8");

        ValueNode value_3_1_1_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT1")).getChilds().get("PARENT1_1")).getChilds().get("VALUE1");
        ValueNode value_3_1_1_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT1")).getChilds().get("PARENT1_1")).getChilds().get("VALUE2");
        ValueNode value_3_1_2_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT1")).getChilds().get("PARENT1_2")).getChilds().get("VALUE3");
        ValueNode value_3_1_2_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT1")).getChilds().get("PARENT1_2")).getChilds().get("VALUE4");
        ValueNode value_3_2_1_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT2")).getChilds().get("PARENT2_1")).getChilds().get("VALUE5");
        ValueNode value_3_2_1_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT2")).getChilds().get("PARENT2_1")).getChilds().get("VALUE6");
        ValueNode value_3_2_2_1 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT2")).getChilds().get("PARENT2_2")).getChilds().get("VALUE7");
        ValueNode value_3_2_2_2 = (ValueNode) ((ParentNode) ((ParentNode) parent.getChildArray()[2].getChilds().get("PARENT2")).getChilds().get("PARENT2_2")).getChilds().get("VALUE8");

        Assert.assertEquals(Coordinates.create(35, 37), value_2_1_1_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(37, 39), value_2_1_1_2.getCoordinates());
        Assert.assertEquals(Coordinates.create(39, 41), value_2_1_2_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(41, 43), value_2_1_2_2.getCoordinates());
        Assert.assertEquals(Coordinates.create(43, 45), value_2_2_1_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(45, 48), value_2_2_1_2.getCoordinates());
        Assert.assertEquals(Coordinates.create(48, 58), value_2_2_2_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(58, 70), value_2_2_2_2.getCoordinates());

        Assert.assertEquals(Coordinates.create(70, 72), value_3_1_1_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(72, 74), value_3_1_1_2.getCoordinates());
        Assert.assertEquals(Coordinates.create(74, 76), value_3_1_2_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(76, 78), value_3_1_2_2.getCoordinates());
        Assert.assertEquals(Coordinates.create(78, 80), value_3_2_1_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(80, 83), value_3_2_1_2.getCoordinates());
        Assert.assertEquals(Coordinates.create(83, 93), value_3_2_2_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(93, 105), value_3_2_2_2.getCoordinates());
    }

    @Test
    public void populateOccurs_3occurs_ParentChildWithArrayParentChildWithAChildAnd2occurs_shouldPopulateOccursWithOKCoords() {
        ParentNode root = NodeFactory.createRootNode();
        ParentArrayNode<String> parent = NodeFactory.createParentNodeArray(root,0,3);
                root.addChild(parent,"ARRAY");
        ParentArrayNode<String> parent2 = NodeFactory.createParentNodeArray(root,1,2);
                parent.addChild(parent2,"ARRAY_CHILD");

        parent2.addChild(NodeFactory.createValueNode(parent2, Coordinates.create(0, 2)),"VALUE");
        int nextStart = 2;
        nextStart = parent2.duplicateOccurs(nextStart);
        parent.duplicateOccurs(nextStart);

        ValueNode value_1 = (ValueNode) ((ParentNode) ((ParentArrayNode) parent.getChildArray()[0].getChilds().get("ARRAY_CHILD")).getChildArray()[0]).getChilds().get("VALUE");
        ValueNode value_2 = (ValueNode) ((ParentNode) ((ParentArrayNode) parent.getChildArray()[0].getChilds().get("ARRAY_CHILD")).getChildArray()[1]).getChilds().get("VALUE");

        ValueNode value_3 = (ValueNode) ((ParentNode) ((ParentArrayNode) parent.getChildArray()[1].getChilds().get("ARRAY_CHILD")).getChildArray()[0]).getChilds().get("VALUE");
        ValueNode value_4 = (ValueNode) ((ParentNode) ((ParentArrayNode) parent.getChildArray()[1].getChilds().get("ARRAY_CHILD")).getChildArray()[1]).getChilds().get("VALUE");
        ValueNode value_5 = (ValueNode) ((ParentNode) ((ParentArrayNode) parent.getChildArray()[2].getChilds().get("ARRAY_CHILD")).getChildArray()[0]).getChilds().get("VALUE");
        ValueNode value_6 = (ValueNode) ((ParentNode) ((ParentArrayNode) parent.getChildArray()[2].getChilds().get("ARRAY_CHILD")).getChildArray()[1]).getChilds().get("VALUE");

        Assert.assertEquals(Coordinates.create(0, 2), value_1.getCoordinates());
        Assert.assertEquals(Coordinates.create(2, 4), value_2.getCoordinates());
        Assert.assertEquals(Coordinates.create(4, 6), value_3.getCoordinates());
        Assert.assertEquals(Coordinates.create(6, 8), value_4.getCoordinates());
        Assert.assertEquals(Coordinates.create(8, 10), value_5.getCoordinates());
        Assert.assertEquals(Coordinates.create(10, 12), value_6.getCoordinates());
    }

}
