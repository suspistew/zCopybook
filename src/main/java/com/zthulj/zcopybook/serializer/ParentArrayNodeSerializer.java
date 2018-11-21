package com.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zthulj.zcopybook.model.ParentArrayNode;

import java.io.IOException;
import java.io.Serializable;

public class ParentArrayNodeSerializer<T extends Serializable> extends JsonSerializer<ParentArrayNode<T>> {

    @Override
    public void serialize(ParentArrayNode<T> parentArrayNode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(parentArrayNode.getChildArray());
    }
}
