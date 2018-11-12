package com.zthulj.zcopybook;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NodeSerializer<T> extends JsonSerializer<Node<T>> {

    @Override
    public void serialize(Node<T> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }

}
