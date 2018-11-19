package com.zthulj.zcopybook.model;

import com.zthulj.zcopybook.factory.NodeFactory;
import org.junit.Assert;
import org.junit.Test;

public class ParentNodeTest {

    @Test
    public void addParentNode_shouldAddAParentNodeToChilds(){
        ParentNode<Object> root = NodeFactory.createRootNode();
        root.addChildOfTypeParentNode("parentName", 0);
        Assert.assertEquals(true,root.getChilds().get("parentName").isParent());
    }

    @Test
    public void addValueNode_shouldAddAValueNodeToChilds(){
        ParentNode<Object> root = NodeFactory.createRootNode();
        root.addChildOfTypeValueNode("child",  Coordinates.from(0,1));
        Assert.assertEquals(false,root.getChilds().get("child").isParent());
    }

    @Test
    public void isParent_rootNode_shouldReturnTrue(){
        ParentNode<Object> root = NodeFactory.createRootNode();
        Node<Object> parent = NodeFactory.createParentNode(root, 0);
        Assert.assertEquals(true, parent.isParent());
    }

    @Test
    public void isParent_parentNode_shouldReturnTrue(){
        Node<Object> root = NodeFactory.createRootNode();
        Assert.assertEquals(true, root.isParent());
    }
}
