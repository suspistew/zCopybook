package com.zthulj.zcopybook.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.serializer.ParentArrayNodeSerializer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(callSuper=true)
@Getter
@JsonSerialize(using = ParentArrayNodeSerializer.class)
@ToString
public class ParentArrayNode<T> extends ParentNode<T> {

	private static final long serialVersionUID = 943387055163029210L;

	private final ParentNode<T>[] childArray;

    public ParentArrayNode(ParentNode<T> parent, int levelNumber, int occursNumber) {
        super(parent, null, levelNumber);
        if (occursNumber < 1)
            throw new IllegalArgumentException("OccursNumber can't be less than 1");
        childArray = new ParentNode[occursNumber];
        for (int i = 0; i < occursNumber; i++) {
            childArray[i] = NodeFactory.createParentNode(parent, levelNumber);
        }
    }

    /**
     * Will populate all the data in registered in the first occurs (0) in the following.
     *
     * @param nextStart the current cursor position when creating the substrings
     * @return the modified nextStart after all the occurs traitment
     */
    public int duplicateOccurs(int nextStart) {
        ParentNode<T> model = this.childArray[0];
        for (int i = 1; i < this.childArray.length ; i++) {
            for(Map.Entry<String,Node<T>> entry : model.getChilds().entrySet()){
                nextStart = entry.getValue().copyInto(this.childArray[i], nextStart, entry.getKey());
            }
        }
        return nextStart;
    }

    @Override
    public int copyInto(ParentNode<T> destination, int cursorPosition, String name) {
        ParentArrayNode<T> newParentArray = NodeFactory.createParentNodeArray(destination,this.getLevelNumber(),this.childArray.length);
        destination.addChild(newParentArray,name);
        for(int i = 0; i < getChildArray().length; i++){
            cursorPosition = copyChild(cursorPosition, getChildArray()[i], newParentArray.getChildArray()[i]);
        }
        return cursorPosition;
    }

    private int copyChild(int cursorPosition, ParentNode<T> source, ParentNode<T> destination) {
        for (Map.Entry<String, Node<T>> childEntry : source.getChilds().entrySet()) {
            cursorPosition = childEntry.getValue().copyInto(destination,cursorPosition,childEntry.getKey());
        }
        return cursorPosition;
    }

    @Override
    public void addChild(Node<T> node, String nodeName) {
        this.childArray[0].addChild(node,nodeName);
    }

    @Override
    public List<ValueNode<T>> getAllValueNodes() {
        List<ValueNode<T>> allValueNodes = new ArrayList<>();
        for (int i = 0; i < childArray.length; i++) {
            allValueNodes.addAll(childArray[i].getAllValueNodes());
        }
        return allValueNodes;
    }
}
