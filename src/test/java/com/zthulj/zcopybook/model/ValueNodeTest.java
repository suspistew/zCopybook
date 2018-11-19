package com.zthulj.zcopybook.model;

import com.zthulj.zcopybook.factory.NodeFactory;
import org.junit.Assert;
import org.junit.Test;

public class ValueNodeTest {


    @Test
    public void isParent_valueNode_shouldReturnFalse(){
        ParentNode<Object> parent = NodeFactory.createRootNode();
        Node<Object> child = NodeFactory.createValueNode(parent,  Coordinates.from(0,1));
        Assert.assertEquals(false, child.isParent());
    }
}
