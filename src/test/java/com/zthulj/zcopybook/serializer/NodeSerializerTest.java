package com.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zthulj.zcopybook.model.Coordinates;
import com.zthulj.zcopybook.model.Node;
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
        Node<String> parent = Node.createRootNode();
        Node<String> child = Node.createChildNode(parent, "myValue", null);
        String result = objectMapper.writeValueAsString(child);
        Assert.assertEquals("\"myValue\"", result);
    }

    @Test
    public void serialize_rootNodeParentChild_shouldReturnJsonWithEmptyChild() throws JsonProcessingException {
        Node<String> root = Node.createRootNode();
        root.addParentNode("parentName", null);
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{}}", result);
    }

    @Test
    public void serialize_rootNodeParentChildWithOneChildValue_shouldReturnJsonWithChild() throws JsonProcessingException {
        Node<String> root = Node.createRootNode();
        Node<String> parent = root.addParentNode("parentName", null);
        parent.addChildNode("aValueChild","theValue", null);
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{\"aValueChild\":\"theValue\"}}", result);
    }

}
