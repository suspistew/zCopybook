package com.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.serializer.ParentNodeSerializer;

import java.util.LinkedHashMap;
import java.util.Objects;

@JsonSerialize(using = ParentNodeSerializer.class)
public class ParentNode<T> extends Node<T> {
    private LinkedHashMap<String,Node<T>> childs;
    protected int levelNumber;

    public ParentNode(ParentNode<T> parent, LinkedHashMap<String, Node<T>> childs, int levelNumber) {
        super(parent);
        this.childs = childs;
        this.levelNumber = levelNumber;
    }

    public void addChild(Node child, String name) {
        this.getChilds().put(getFinalName(name),child);
    }

    public ParentNode<T> addChildOfTypeParentNode(String nodeName, int lvlNumber) {
        String name = getFinalName(nodeName);
        ParentNode newParent = NodeFactory.createParentNode(this, lvlNumber);
        this.getChilds().put(name,newParent);
        return newParent;
    }



    public ValueNode<T> addChildOfTypeValueNode(String nodeName, Coordinates coords) {
        return addChildOfTypeValueNode(nodeName, coords, ValueNode.ValueType.STRING);
    }

    public ValueNode<T> addChildOfTypeValueNode(String nodeName, Coordinates coords, ValueNode.ValueType type) {
        String name = getFinalName(nodeName);
        ValueNode newChild = NodeFactory.createValueNode(this, coords, type);
        this.getChilds().put(name, newChild);
        return newChild;
    }

    public ParentArrayNode<T> addChildOfTypeParentArrayNode(String nodeName, int lvlNumber, int occursNumber) {
        String name = getFinalName(nodeName);
        ParentArrayNode newParent = NodeFactory.createParentNodeArray(this, lvlNumber, occursNumber);
        this.getChilds().put(name, newParent);
        return newParent;
    }

    private String getFinalName(String nodeName) {
        String name = nodeName;
        int i = 1;
        while(this.getChilds().containsKey(name))
            name = nodeName + (++i);
        return name;
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
