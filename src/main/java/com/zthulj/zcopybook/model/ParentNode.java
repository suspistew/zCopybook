package com.zthulj.zcopybook.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.serializer.ParentNodeSerializer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper=true)
@Getter
@JsonSerialize(using = ParentNodeSerializer.class)
@ToString
public class ParentNode<T> extends Node<T> {

	private static final long serialVersionUID = -7011113266458098086L;
	private LinkedHashMap<String,Node<T>> childs;
    protected int levelNumber;


    public ParentNode(ParentNode<T> parent, LinkedHashMap<String,Node<T>> childs, int levelNumber) {
		super(parent);
		this.childs=childs;
		this.levelNumber=levelNumber;
	}

	public void addChild(Node<T> child, String name) {
        this.getChilds().put(getFinalName(name),child);
    }

    @Override
    public int copyInto(ParentNode<T> destination, int cursorPosition, String name) {
        ParentNode<T> current = NodeFactory.createParentNode(destination,this.levelNumber);
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

    @Override
    public List<ValueNode<T>> getAllValueNodes() {
        List<ValueNode<T>> allValueNodes = new ArrayList<>();
        childs.forEach((k,v)->allValueNodes.addAll(v.getAllValueNodes()));
        return allValueNodes;
    }

    @Override
    public boolean isParent() {
        return true;
    }
 }
