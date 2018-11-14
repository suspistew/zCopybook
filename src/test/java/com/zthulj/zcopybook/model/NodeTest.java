package com.zthulj.zcopybook.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class NodeTest {

    @Test
    public void createRootNode_shouldReturnRootParentNode(){
        Node<Object> node = Node.createRootNode();
        Assert.assertEquals(new HashMap<>(),node.getChilds());
        Assert.assertNull(node.getParent());
        Assert.assertNull(node.getValue());
    }

    @Test
    public void createParentNode_shouldReturnParentNode(){
        Node<Object> root = Node.createRootNode();
        Node<Object> node = Node.createParentNode(root, "1");

        Assert.assertEquals(new HashMap<>(),node.getChilds());
        Assert.assertEquals(root,node.getParent());
        Assert.assertNull(node.getValue());
        Assert.assertEquals("1", node.getLevelNumber());
    }

    @Test
    public void createChildNode_shouldReturnChildNode(){
        Node<Object> parent = Node.createRootNode();
        Object t = new String("test");
        Node<Object> child = Node.createChildNode(parent, t, Coordinates.from(0,1));

        Assert.assertEquals(parent, child.getParent());
        Assert.assertNull(child.getChilds());
        Assert.assertEquals(t,child.getValue());

        Assert.assertNull(child.getLevelNumber());
    }

    @Test
    public void addParentNode_shouldAddAParentNodeToChilds(){
        Node<Object> root = Node.createRootNode();
        root.addParentNode("parentName", null);
        Assert.assertEquals(true,root.getChilds().get("parentName").isParent());

    }

    @Test
    public void addChildNode_shouldAddAChildNodeToChilds(){
        Node<Object> root = Node.createRootNode();
        root.addChildNode("child", "value",  Coordinates.from(0,1));
        Assert.assertEquals(false,root.getChilds().get("child").isParent());
    }

    @Test
    public void isParent_rootNode_shouldReturnTrue(){
        Node<Object> root = Node.createRootNode();
        Node<Object> parent = Node.createParentNode(root, null);
        Assert.assertEquals(true, parent.isParent());
    }

    @Test
    public void isParent_parentNode_shouldReturnTrue(){
        Node<Object> root = Node.createRootNode();
        Assert.assertEquals(true, root.isParent());
    }

    @Test
    public void isParent_childNode_shouldReturnFalse(){
        Node<Object> parent = Node.createRootNode();
        Node<Object> child = Node.createChildNode(parent, "Test",  Coordinates.from(0,1));
        Assert.assertEquals(false, child.isParent());
    }
}
