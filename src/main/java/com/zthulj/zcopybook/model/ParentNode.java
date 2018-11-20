package com.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.serializer.ParentNodeSerializer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

    @Override
    public int copyInto(ParentNode destination, int cursorPosition, String name) {
        ParentNode current = NodeFactory.createParentNode(destination,this.levelNumber);
        destination.addChild(current,name);
        for (Map.Entry<String, Node<T>> childEntry : getChilds().entrySet()) {
            cursorPosition = childEntry.getValue().copyInto(current,cursorPosition,childEntry.getKey());
        }
        return cursorPosition;
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
