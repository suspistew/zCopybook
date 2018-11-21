package com.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.serializer.ParentNodeSerializer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedHashMap;

@JsonSerialize(using = ParentNodeSerializer.class)
@EqualsAndHashCode(callSuper=true)
@ToString
public class RootNode<T> extends ParentNode<T>{

	private static final long serialVersionUID = 1555257535111710430L;

	public RootNode(LinkedHashMap<String, Node<T>> childs) {
		super(null, childs, 0);
    }
}
