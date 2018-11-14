package com.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.serializer.NodeSerializer;

import java.util.HashMap;
import java.util.Objects;

@JsonSerialize(using = NodeSerializer.class)
public final class Node<T> {
    private HashMap<String,Node<T>> childs;
    private Node<T> parent;
    private String levelNumber;
    private T value;
    private Coordinates coordinates;

    private Node(HashMap<String,Node<T>> childs, Node<T> parent, T value, String levelNumber, Coordinates coordinates) {
        this.childs = childs;
        this.parent = parent;
        this.value = value;
        this.levelNumber = levelNumber;
        this.coordinates = coordinates;
    }

    public static <T> Node<T> createRootNode() {
      return new Node(new HashMap<>(), null, null, "", null);
    }

    public static <T> Node<T> createParentNode(Node<T> parent, String indentation) {
        return new Node(new HashMap<>(), parent, null, indentation, null);
    }

    public static <T>  Node<T> createChildNode(Node<T> parent, T value, Coordinates coords) {
        return new Node(null, parent, value, null, coords);
    }

    public Node<T> addParentNode(String nodeName, String indentation) {
        Node newParent = Node.createParentNode(this, indentation);
        this.getChilds().put(nodeName,newParent);
        return newParent;
    }

    public Node<T> addChildNode(String nodeName, T value, Coordinates coords) {
        Node newChild = Node.createChildNode(this, value, coords);
        this.getChilds().put(nodeName, newChild);
        return newChild;
    }

    public HashMap<String,Node<T>> getChilds() {
        return childs;
    }

    public Node<T> getParent() {
        return parent;
    }

    public T getValue() {
        return value;
    }

    public boolean isParent() {
        return childs != null && value == null;
    }

    public String getLevelNumber() {
        return levelNumber;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(getChilds(), node.getChilds()) &&
                Objects.equals(isParent(), node.isParent()) &&
                Objects.equals(getLevelNumber(), node.getLevelNumber()) &&
                Objects.equals(getValue(), node.getValue()) &&
                Objects.equals(getCoordinates(), node.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChilds(), isParent(), getLevelNumber(), getValue(), getCoordinates());
    }
}
