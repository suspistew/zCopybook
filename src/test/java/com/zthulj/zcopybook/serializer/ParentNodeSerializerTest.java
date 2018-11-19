package com.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.model.Node;
import com.zthulj.zcopybook.model.ParentNode;
import com.zthulj.zcopybook.model.ValueNode;
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
        root.addChildOfTypeParentNode("parentName", 0);
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{}}", result);
    }

    @Test
    public void serialize_rootNodeParentChildWithOneChildValue_shouldReturnJsonWithChild() throws JsonProcessingException {
        ParentNode<String> root = NodeFactory.createRootNode();
        ParentNode<String> parent = root.addChildOfTypeParentNode("parentName", 0);
        ValueNode<String> child = parent.addChildOfTypeValueNode("aValueChild", null);
        child.setValue("theValue");
        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"parentName\":{\"aValueChild\":\"theValue\"}}", result);
    }


}
