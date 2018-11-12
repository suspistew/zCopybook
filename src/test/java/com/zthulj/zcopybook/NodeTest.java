package com.zthulj.zcopybook;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class NodeTest {

    @Test
    public void createRootNode_shouldReturnRootParentNode(){
        Node<Object> node = Node.createRootNode();
        Assert.assertEquals(new ArrayList<Pair<String, Node<Object>>>(),node.getChilds());
        Assert.assertNull(node.getParent());
        Assert.assertNull(node.getValue());
    }

    @Test
    public void createParentNode_shouldReturnParentNode(){
        Node<Object> root = Node.createRootNode();
        Node<Object> node = Node.createParentNode(root);

        Assert.assertEquals(new ArrayList<Pair<String, Node<Object>>>(),node.getChilds());
        Assert.assertEquals(root,node.getParent());
        Assert.assertNull(node.getValue());
    }

    @Test
    public void createChildNode_shouldReturnChildNode(){
        Node<Object> parent = Node.createRootNode();
        Object t = new String("test");
        Node<Object> child = Node.createChildNode(parent, t);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertNull(child.getChilds());
        Assert.assertEquals(t,child.getValue());
    }

    @Test
    public void isParent_parentNode_shouldReturnTrue(){
        Node<Object> parent = Node.createRootNode();
        Assert.assertEquals(true, parent.isParent());
    }
}
