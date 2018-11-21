package com.github.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.zthulj.zcopybook.factory.NodeFactory;
import com.github.zthulj.zcopybook.serializer.ParentNodeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper=true)
@Getter
@JsonSerialize(using = ParentNodeSerializer.class)
@ToString
public class ParentNode<T extends Serializable> extends Node<T> {

	private static final long serialVersionUID = -7011113266458098086L;
	private final Map<String,Node<T>> childs;
    private final int levelNumber;


    public ParentNode(ParentNode<T> parent, Map<String,Node<T>> childs, int levelNumber) {
		super(parent, true);
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
 }
