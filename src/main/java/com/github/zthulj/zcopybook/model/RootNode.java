package com.github.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.zthulj.zcopybook.serializer.ParentNodeSerializer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@JsonSerialize(using = ParentNodeSerializer.class)
@EqualsAndHashCode(callSuper=true)
@ToString
public class RootNode<T extends Serializable> extends ParentNode<T>{

	private static final long serialVersionUID = 1555257535111710430L;

	public RootNode(Map<String, Node<T>> childs) {
		super(null, childs, 0);
    }
}
