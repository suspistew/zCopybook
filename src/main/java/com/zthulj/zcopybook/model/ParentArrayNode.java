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
                if(entry.getValue() instanceof ValueNode){
                    nextStart = populateValueNode(nextStart, this.childArray[i], entry);
                }else if(entry.getValue() instanceof ParentArrayNode){
                    nextStart = populateParentArrayNode(nextStart, this.childArray[i],entry);
                }else if(entry.getValue() instanceof ParentNode){
                   nextStart = populateParentNode(nextStart, this.childArray[i],entry);
                }

            }
        }
        return nextStart;
    }

    private int populateParentArrayNode(int nextStart, ParentNode<T> toPopulate, Map.Entry<String, Node> entry) {

        ParentArrayNode parentArrayNode = (ParentArrayNode)entry.getValue();
        ParentArrayNode parentArrayToPopulate = toPopulate.addChildOfTypeParentArrayNode(entry.getKey(),parentArrayNode.levelNumber,parentArrayNode.childArray.length);

        for(int i = 0; i < parentArrayNode.getChildArray().length; i++){
            ParentNode source = parentArrayNode.getChildArray()[i];
            ParentNode destination = parentArrayToPopulate.getChildArray()[i];
            nextStart = populateParentNodeChilds(nextStart, source, destination);
        }

        return nextStart;
    }

    private int populateParentNode(int nextStart, ParentNode<T> toPopulate, Map.Entry<String, Node> entry) {
        ParentNode parentNode = (ParentNode)entry.getValue();
        ParentNode current = toPopulate.addChildOfTypeParentNode(entry.getKey(),parentNode.levelNumber);
        nextStart = populateParentNodeChilds(nextStart, parentNode, current);

        return nextStart;
    }

    private int populateParentNodeChilds(int nextStart, ParentNode source, ParentNode destination) {
        for (Map.Entry<String, Node> childEntry : (Set<Map.Entry<String,Node>>) source.getChilds().entrySet()) {
            if(childEntry.getValue() instanceof ValueNode){
                nextStart = populateValueNode(nextStart, destination, childEntry);
            }else if(childEntry.getValue() instanceof ParentArrayNode){
                nextStart = populateParentArrayNode(nextStart, destination, childEntry);
            }else if(childEntry.getValue() instanceof ParentNode){
                nextStart = populateParentNode(nextStart,destination,childEntry);
            }
        }
        return nextStart;
    }


    private int populateValueNode(int nextStart, ParentNode<T> tParentNode, Map.Entry<String, Node> entry) {
        Coordinates nextCoords = calculateCoordinates((ValueNode)entry.getValue(),nextStart);
        tParentNode.addChildOfTypeValueNode(entry.getKey(),nextCoords);
        nextStart += nextCoords.getSize();
        return nextStart;
    }


    private Coordinates calculateCoordinates(ValueNode value, int nextStart) {
        return Coordinates.from(nextStart, nextStart + value.getCoordinates().getSize() - 1);
    }


    @Override
    public ValueNode<T> addChildOfTypeValueNode(String nodeName, Coordinates coords) {
        return this.childArray[0].addChildOfTypeValueNode(nodeName, coords);
    }

    @Override
    public ValueNode<T> addChildOfTypeValueNode(String nodeName, Coordinates coords, ValueNode.ValueType type) {
        return this.childArray[0].addChildOfTypeValueNode(nodeName, coords, type);
    }

    @Override
    public ParentNode<T> addChildOfTypeParentNode(String nodeName, int lvlNumber) {
        ParentNode newParent = this.childArray[0].addChildOfTypeParentNode(nodeName, lvlNumber);
        return newParent;
    }

    @Override
    public ParentArrayNode<T> addChildOfTypeParentArrayNode(String nodeName, int lvlNumber, int occursNumber) {
        ParentArrayNode newParent = this.childArray[0].addChildOfTypeParentArrayNode(nodeName, lvlNumber,occursNumber);
        return newParent;
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
