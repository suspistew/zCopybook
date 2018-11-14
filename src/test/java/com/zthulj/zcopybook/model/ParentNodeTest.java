package com.zthulj.zcopybook.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class ParentNodeTest {

    @Test
    public void createRootNode_shouldReturnRootParentNode(){
        ParentNode<Object> node = Node.createRootNode();
        Assert.assertEquals(new HashMap<>(),node.getChilds());
        Assert.assertNull(node.getParent());
    }

    @Test
    public void createParentNode_shouldReturnParentNode(){
        ParentNode<Object> root = Node.createRootNode();
        ParentNode<Object> node = Node.createParentNode(root, 1);

        Assert.assertEquals(new HashMap<>(),node.getChilds());
        Assert.assertEquals(root,node.getParent());
        Assert.assertEquals(1, node.getLevelNumber());
    }

    @Test
    public void addParentNode_shouldAddAParentNodeToChilds(){
        ParentNode<Object> root = Node.createRootNode();
        root.addParentNode("parentName", 0);
        Assert.assertEquals(true,root.getChilds().get("parentName").isParent());

    }

    @Test
    public void addValueNode_shouldAddAValueNodeToChilds(){
        ParentNode<Object> root = Node.createRootNode();
        root.addValueNode("child",  Coordinates.from(0,1));
        Assert.assertEquals(false,root.getChilds().get("child").isParent());
    }

    @Test
    public void isParent_rootNode_shouldReturnTrue(){
        ParentNode<Object> root = Node.createRootNode();
        Node<Object> parent = Node.createParentNode(root, 0);
        Assert.assertEquals(true, parent.isParent());
    }

    @Test
    public void isParent_parentNode_shouldReturnTrue(){
        Node<Object> root = Node.createRootNode();
        Assert.assertEquals(true, root.isParent());
    }
}
