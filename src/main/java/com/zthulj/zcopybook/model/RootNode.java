package com.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.serializer.ParentNodeSerializer;

import java.util.LinkedHashMap;

@JsonSerialize(using = ParentNodeSerializer.class)
public class RootNode<T> extends ParentNode<T>{

    public RootNode(  LinkedHashMap childs) {
        super(null, childs, 0);
    }

}
