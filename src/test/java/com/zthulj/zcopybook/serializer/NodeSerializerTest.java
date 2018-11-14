package com.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zthulj.zcopybook.model.Coordinates;
import com.zthulj.zcopybook.model.Node;
import com.zthulj.zcopybook.model.ParentNode;
import com.zthulj.zcopybook.model.ValueNode;
import org.junit.Assert;
import org.junit.Test;

public class NodeSerializerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serialize_rootNodeNoChild_shouldReturnEmptyJson() throws JsonProcessingException {
        Node<String> root = Node.createRootNode();
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{}", result);
    }

    @Test
    public void serialize_childNode_shouldReturnValue() throws JsonProcessingException {
        ParentNode<String> parent = Node.createRootNode();
        ValueNode<String> child = Node.createValueNode(parent, null);
        child.setValue("myValue");
        String result = objectMapper.writeValueAsString(child);
        Assert.assertEquals("\"myValue\"", result);
    }

    @Test
    public void serialize_rootNodeParentChild_shouldReturnJsonWithEmptyChild() throws JsonProcessingException {
        ParentNode<String> root = Node.createRootNode();
        root.addParentNode("parentName", 0);
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{}}", result);
    }

    @Test
    public void serialize_rootNodeParentChildWithOneChildValue_shouldReturnJsonWithChild() throws JsonProcessingException {
        ParentNode<String> root = Node.createRootNode();
        ParentNode<String> parent = root.addParentNode("parentName", 0);
        ValueNode<String> child = parent.addValueNode("aValueChild", null);
        child.setValue("theValue");
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{\"aValueChild\":\"theValue\"}}", result);
    }

}
