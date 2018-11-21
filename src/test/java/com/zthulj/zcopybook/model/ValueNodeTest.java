package com.zthulj.zcopybook.model;

import com.zthulj.zcopybook.factory.NodeFactory;
import org.junit.Assert;
import org.junit.Test;

public class ValueNodeTest {


    @Test
    public void isParent_valueNode_shouldReturnFalse(){
        ParentNode<String> parent = NodeFactory.createRootNode();
        Node<String> child = NodeFactory.createValueNode(parent,  Coordinates.create(0,1));
        Assert.assertEquals(false, child.isParent());
    }
}
