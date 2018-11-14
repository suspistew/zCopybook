package com.zthulj.zcopybook.model;

import org.junit.Assert;
import org.junit.Test;

public class ValueNodeTest {
    @Test
    public void createChildNode_shouldReturnChildNode(){
        ParentNode<Object> parent = Node.createRootNode();
        Object t = new String("test");
        ValueNode<Object> child = Node.createValueNode(parent, Coordinates.from(0,1));
        child.setValue(t);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(t,child.getValue());

    }

    @Test
    public void isParent_valueNode_shouldReturnFalse(){
        ParentNode<Object> parent = Node.createRootNode();
        Node<Object> child = Node.createValueNode(parent,  Coordinates.from(0,1));
        Assert.assertEquals(false, child.isParent());
    }
}
