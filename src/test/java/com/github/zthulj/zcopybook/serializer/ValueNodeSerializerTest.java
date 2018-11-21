package com.github.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zthulj.zcopybook.factory.NodeFactory;
import com.github.zthulj.zcopybook.model.ValueNode;
import org.junit.Assert;
import org.junit.Test;

public class ValueNodeSerializerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serialize_valueNodeWithValue_shouldReturnTheValue() throws JsonProcessingException {
        ValueNode<String> child = NodeFactory.createValueNode(null, null);
        child.setValue("myValue");
        String result = objectMapper.writeValueAsString(child);
        Assert.assertEquals("\"myValue\"", result);
    }
}
