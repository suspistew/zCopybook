package com.zthulj.zcopybook.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zthulj.zcopybook.model.ParentNode;

import java.io.IOException;
import java.io.Serializable;

public class ParentNodeSerializer<T extends Serializable> extends JsonSerializer<ParentNode<T>> {
    @Override
    public void serialize(ParentNode<T> parentNode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(parentNode.getChilds());
    }
}
