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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(isParent(), node.isParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isParent());
    }
}
