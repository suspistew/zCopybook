package com.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zthulj.zcopybook.model.ValueNode;

import java.io.IOException;

public class ValueNodeSerializer<T> extends JsonSerializer<ValueNode<T>> {

    @Override
    public void serialize(ValueNode<T> valueNode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(valueNode.getValue().toString());
    }
    
}
