package com.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.model.*;
import org.junit.Assert;
import org.junit.Test;

public class ParentArrayNodeSerializerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serialize_parentArrayWithChilds_shouldReturnJsonArray() throws JsonProcessingException {
        ParentArrayNode parent = NodeFactory.createParentNodeArray(null,1,3);
        parent.addChildOfTypeValueNode("Hello", Coordinates.from(0,2));
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
        ParentArrayNode parent = root.addChildOfTypeParentArrayNode("arraynode",1,3);
        parent.addChildOfTypeValueNode("Hello", Coordinates.from(0,2));
        parent.duplicateOccurs(3);
        for (ParentNode parenNode : parent.getChildArray()) {
            ValueNode value = (ValueNode) parenNode.getChilds().get("Hello");
            value.setValue("Val");

        }

        String result = objectMapper.writeValueAsString(root);
        Assert.assertEquals("{\"arraynode\":[{\"Hello\":\"Val\"},{\"Hello\":\"Val\"},{\"Hello\":\"Val\"}]}",result);
    }
}
