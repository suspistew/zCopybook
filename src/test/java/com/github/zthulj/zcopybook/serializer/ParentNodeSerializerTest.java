package com.github.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zthulj.zcopybook.factory.NodeFactory;
import com.github.zthulj.zcopybook.model.Node;
import com.github.zthulj.zcopybook.model.ParentNode;
import com.github.zthulj.zcopybook.model.ValueNode;
import org.junit.Assert;
import org.junit.Test;

public class ParentNodeSerializerTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serialize_rootNodeNoChild_shouldReturnEmptyJson() throws JsonProcessingException {
        Node<String> root = NodeFactory.createRootNode();
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{}", result);
    }

    @Test
    public void serialize_rootNodeParentChild_shouldReturnJsonWithEmptyChild() throws JsonProcessingException {
        ParentNode<String> root = NodeFactory.createRootNode();
        ParentNode parentNode = NodeFactory.createParentNode(root,0);
        root.addChild(parentNode,"parentName");
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{}}", result);
    }

    @Test
    public void serialize_rootNodeParentChildWithOneChildValue_shouldReturnJsonWithChild() throws JsonProcessingException {
        ParentNode<String> root = NodeFactory.createRootNode();
        ParentNode<String> parent = NodeFactory.createParentNode(root,0);
        root.addChild(parent,"parentName");
        ValueNode<String> child = NodeFactory.createValueNode(parent,null);
                parent.addChild(child,"aValueChild");
        child.setValue("theValue");
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{\"aValueChild\":\"theValue\"}}", result);
    }


}
