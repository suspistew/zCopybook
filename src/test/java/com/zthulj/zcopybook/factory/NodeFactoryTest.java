package com.zthulj.zcopybook.factory;

import com.zthulj.zcopybook.model.Coordinates;
import com.zthulj.zcopybook.model.Node;
import com.zthulj.zcopybook.model.ParentNode;
import com.zthulj.zcopybook.model.ValueNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class NodeFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void factory_occursNumber0_shouldThrowIllegalArg() {
        ParentNode root = NodeFactory.createRootNode();
        NodeFactory.createParentNodeArray(root, 0, 0);
    }

    @Test
    public void createRootNode_shouldReturnRootParentNode(){
        ParentNode<Object> node = NodeFactory.createRootNode();
        Assert.assertEquals(new HashMap<>(),node.getChilds());
        Assert.assertNull(node.getParent());
    }

    @Test
    public void createParentNode_shouldReturnParentNode(){
        ParentNode<Object> root = NodeFactory.createRootNode();
        ParentNode<Object> node = NodeFactory.createParentNode(root, 1);

        Assert.assertEquals(new HashMap<>(),node.getChilds());
        Assert.assertEquals(root,node.getParent());
        Assert.assertEquals(1, node.getLevelNumber());
    }

    @Test
    public void createChildNode_shouldReturnChildNode(){
        ParentNode<Object> parent = NodeFactory.createRootNode();
        Object t = new String("test");
        ValueNode<Object> child = NodeFactory.createValueNode(parent, Coordinates.from(0,1));
        child.setValue(t);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(t,child.getValue());

    }
}
