package com.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.serializer.ParentArrayNodeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@JsonSerialize(using = ParentArrayNodeSerializer.class)
public class ParentArrayNode<T> extends ParentNode<T> {
    private static Logger logger = LoggerFactory.getLogger(ParentArrayNode.class);

    public ParentArrayNode(ParentNode parent, int levelNumber, int occursNumber) {
        super(parent, null, levelNumber);
        if (occursNumber < 1)
            throw new IllegalArgumentException("OccursNumber can't be less than 1");

        childArray = new ParentNode[occursNumber];
        for (int i = 0; i < occursNumber; i++) {
            childArray[i] = NodeFactory.createParentNode(parent, levelNumber);
        }
    }

    private ParentNode<T>[] childArray;

    /**
     * Will populate all the data in registered in the first occurs (0) in the following.
     *
     * @param nextStart the current cursor position when creating the substrings
     * @return the modified nextStart after all the occurs traitment
     */
    public int duplicateOccurs(int nextStart) {
        ParentNode model = this.childArray[0];
        for (int i = 1; i < this.childArray.length ; i++) {
            for(Map.Entry<String,Node> entry : (Set<Map.Entry<String,Node>>)model.getChilds().entrySet()){
                nextStart = entry.getValue().copyInto(this.childArray[i], nextStart, entry.getKey());
            }
        }
        return nextStart;
    }

    @Override
    public int copyInto(ParentNode destination, int cursorPosition, String name) {
        ParentArrayNode newParentArray = NodeFactory.createParentNodeArray(destination,this.levelNumber,this.childArray.length);
        destination.addChild(newParentArray,name);

        for(int i = 0; i < getChildArray().length; i++){
            cursorPosition = copyChild(cursorPosition, getChildArray()[i], newParentArray.getChildArray()[i]);
        }
        return cursorPosition;
    }

    private int copyChild(int cursorPosition, ParentNode<T> source, ParentNode destination) {
        for (Map.Entry<String, Node<T>> childEntry : source.getChilds().entrySet()) {
            cursorPosition = childEntry.getValue().copyInto(destination,cursorPosition,childEntry.getKey());
        }
        return cursorPosition;
    }

    @Override
    public void addChild(Node node, String nodeName) {
        this.childArray[0].addChild(node,nodeName);
    }

    public ParentNode<T>[] getChildArray() {
        return childArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentArrayNode)) return false;
        if (!super.equals(o)) return false;
        ParentArrayNode<?> that = (ParentArrayNode<?>) o;
        return Arrays.equals(this.childArray, this.childArray);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(this.childArray);
        return result;
    }
}
