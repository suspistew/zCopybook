package com.zthulj.zcopybook.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Objects;


public abstract class Node<T> implements Serializable {
    private ParentNode<T> parent;

    protected Node(ParentNode<T> parent) {
        this.parent = parent;
    }

    public ParentNode<T> getParent() {
        return parent;
    }

    public abstract boolean isParent();

    public abstract int copyInto(ParentNode destination, int cursorPosition, String name);
}
