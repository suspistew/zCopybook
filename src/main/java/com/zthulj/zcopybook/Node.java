package com.zthulj.zcopybook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node<T> {
    private List<Pair<String, Node<T>>> childs;
    private Node<T> parent;
    private T value;

    private Node(List<Pair<String, Node<T>>> childs, Node<T> parent, T value) {
        this.childs = childs;
        this.parent = parent;
        this.value = value;
    }

    public static <T> Node<T> createRootNode() {
      return new Node(new ArrayList<>(), null, null);
    }

    public static <T> Node<T> createParentNode(Node<Object> parent) {
        return new Node(new ArrayList<>(), parent, null);
    }

    public static <T>  Node<T> createChildNode(Node<Object> parent, T value) {
        return new Node(null, parent, value);
    }

    public List<Pair<String, Node<T>>> getChilds() {
        return childs;
    }

    public Node<T> getParent() {
        return parent;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(getChilds(), node.getChilds()) &&
                Objects.equals(getParent(), node.getParent()) &&
                Objects.equals(getValue(), node.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChilds(), getParent(), getValue());
    }

    public boolean isParent() {
        return childs != null && value == null;
    }
}
