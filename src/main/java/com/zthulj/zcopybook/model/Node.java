package com.zthulj.zcopybook.model;

import java.io.Serializable;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@ToString(exclude="parentNode")
@EqualsAndHashCode(exclude="parentNode")
public abstract class Node<T> implements Serializable {

	private static final long serialVersionUID = -3514066933920187128L;
	private final ParentNode<T> parentNode;
	private final boolean parent;

    public abstract int copyInto(ParentNode<T> destination, int cursorPosition, String name);

    public abstract List<ValueNode<T>> getAllValueNodes();
}
