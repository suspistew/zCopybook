package com.zthulj.zcopybook.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@ToString(exclude="parent")
@EqualsAndHashCode(exclude="parent")
public abstract class Node<T> implements Serializable {
    
	private static final long serialVersionUID = -3514066933920187128L;
	private ParentNode<T> parent;

	
    public abstract boolean isParent();

    public abstract int copyInto(ParentNode<T> destination, int cursorPosition, String name);
}
