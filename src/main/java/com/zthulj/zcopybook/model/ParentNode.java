package com.zthulj.zcopybook.model;

import java.util.LinkedHashMap;
import java.util.Objects;

public class ParentNode<T> extends Node<T> {
    private LinkedHashMap<String,Node<T>> childs;
    protected int levelNumber;

    public ParentNode(ParentNode<T> parent, LinkedHashMap<String, Node<T>> childs, int levelNumber) {
        super(parent);
        this.childs = childs;
        this.levelNumber = levelNumber;
    }

    public ParentNode<T> addParentNode(String nodeName, int lvlNumber) {
        ParentNode newParent = Node.createParentNode(this, lvlNumber);
        this.getChilds().put(nodeName,newParent);
        return newParent;
    }

    public ValueNode<T> addValueNode(String nodeName, Coordinates coords) {
        ValueNode newChild = Node.createValueNode(this, coords);
        this.getChilds().put(nodeName, newChild);
        return newChild;
    }

    public ParentArrayNode<T> addParentArrayNode(String nodeName, int lvlNumber, int occursNumber) {
        ParentArrayNode newParent = Node.createParentNodeArray(this, lvlNumber, occursNumber);
        this.getChilds().put(nodeName, newParent);
        return newParent;
    }

    public LinkedHashMap<String, Node<T>> getChilds() {
        return childs;
    }

    public int getLevelNumber() {
        return levelNumber;
    }


    @Override
    public boolean isParent() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentNode)) return false;
        ParentNode<?> that = (ParentNode<?>) o;
        return getLevelNumber() == that.getLevelNumber() &&
                Objects.equals(getChilds(), that.getChilds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChilds(), getLevelNumber());
    }
}
