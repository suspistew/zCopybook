package com.github.zthulj.zcopybook.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor(access=AccessLevel.PROTECTED)
@Getter
@ToString(exclude="parentNode")
@EqualsAndHashCode(exclude="parentNode")
public abstract class Node<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = -3514066933920187128L;
	private final ParentNode<T> parentNode;
	private final boolean parent;

    public abstract int copyInto(ParentNode<T> destination, int cursorPosition, String name);
    public abstract List<ValueNode<T>> getAllValueNodes();

}
