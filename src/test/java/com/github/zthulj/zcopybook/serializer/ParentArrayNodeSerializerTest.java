package com.github.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zthulj.zcopybook.factory.NodeFactory;
import com.github.zthulj.zcopybook.model.*;
import org.junit.Assert;
import org.junit.Test;

public class ParentArrayNodeSerializerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serialize_parentArrayWithChilds_shouldReturnJsonArray() throws JsonProcessingException {
        ParentArrayNode parent = NodeFactory.createParentNodeArray(null,1,3);
        parent.addChild(NodeFactory.createValueNode(parent, Coordinates.create(0,2)),"Hello");
        parent.duplicateOccurs(3);
        for (ParentNode parenNode : parent.getChildArray()) {
            ValueNode value = (ValueNode) parenNode.getChilds().get("Hello");
            value.setValue("Val");
        }

        String result = objectMapper.writeValueAsString(parent);

        Assert.assertEquals("[{\"Hello\":\"Val\"},{\"Hello\":\"Val\"},{\"Hello\":\"Val\"}]",result);
    }

    @Test
    public void serialize_rootWithParentArrayWithChilds_shouldReturnJsonArray() throws JsonProcessingException {
        RootNode root = NodeFactory.createRootNode();

        ParentArrayNode parent = NodeFactory.createParentNodeArray(root,1,3);
        root.addChild(parent,"arraynode");

        parent.addChild(NodeFactory.createValueNode(parent,Coordinates.create(0,2)),"Hello");

        parent.duplicateOccurs(3);
        for (ParentNode parenNode : parent.getChildArray()) {
            ValueNode value = (ValueNode) parenNode.getChilds().get("Hello");
            value.setValue("Val");

        }

        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"arraynode\":[{\"Hello\":\"Val\"},{\"Hello\":\"Val\"},{\"Hello\":\"Val\"}]}",result);
    }
}
