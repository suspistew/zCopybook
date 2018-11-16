package com.zthulj.zcopybook.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ParentArrayNode<T> extends ParentNode<T> {
    private static Logger logger = LoggerFactory.getLogger(ParentArrayNode.class);

    public ParentArrayNode(ParentNode parent, int levelNumber, int occursNumber) {
        super(parent, null, levelNumber);
        if (occursNumber < 1)
            throw new IllegalArgumentException("OccursNumber can't be less than 1");

        array = new ParentNode[occursNumber];
        for (int i = 0; i < occursNumber; i++) {
            array[i] = Node.createParentNode(parent, levelNumber);
        }

    }

    private ParentNode<T>[] array;

    /**
     * Will populate all the data in registered in the first occurs (0) in the following.
     *
     * @param nextStart the current cursor position when creating the substrings
     * @return the modified nextStart after all the occurs traitment
     */
    public int populateOccurs(int nextStart) {
        ParentNode model = getArray()[0];
        for (int i = 1; i < getArray().length ; i++) {
            for(Map.Entry<String,Node> entry : (Set<Map.Entry<String,Node>>)model.getChilds().entrySet()){
                if(entry.getValue() instanceof ValueNode){
                    nextStart = populateValueNode(nextStart, getArray()[i], entry);
                }else if(entry.getValue() instanceof ParentArrayNode){
                    nextStart = populateParentArrayNode(nextStart,getArray()[i],entry);
                }else if(entry.getValue() instanceof ParentNode){
                   nextStart = populateParentNode(nextStart,getArray()[i],entry);
                }

            }
        }
        return nextStart;
    }

    private int populateParentArrayNode(int nextStart, ParentNode<T> toPopulate, Map.Entry<String, Node> entry) {

        ParentArrayNode parentArrayNode = (ParentArrayNode)entry.getValue();
        ParentArrayNode parentArrayToPopulate = toPopulate.addParentArrayNode(entry.getKey(),parentArrayNode.levelNumber,parentArrayNode.array.length);

        for(int i = 0; i < parentArrayNode.getArray().length; i++){
            ParentNode source = parentArrayNode.getArray()[i];
            ParentNode destination = parentArrayToPopulate.getArray()[i];
            nextStart = populateParentNodeChilds(nextStart, source, destination);
        }

        return nextStart;
    }

    private int populateParentNode(int nextStart, ParentNode<T> toPopulate, Map.Entry<String, Node> entry) {
        ParentNode parentNode = (ParentNode)entry.getValue();
        ParentNode current = toPopulate.addParentNode(entry.getKey(),parentNode.levelNumber);
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
        tParentNode.addValueNode(entry.getKey(),nextCoords);
        nextStart += nextCoords.getSize();
        return nextStart;
    }


    private Coordinates calculateCoordinates(ValueNode value, int nextStart) {
        return Coordinates.from(nextStart, nextStart + value.getCoordinates().getSize() - 1);
    }


    @Override
    public ValueNode<T> addValueNode(String nodeName, Coordinates coords) {
        return getArray()[0].addValueNode(nodeName, coords);
    }

    @Override
    public ParentNode<T> addParentNode(String nodeName, int lvlNumber) {
        ParentNode newParent = getArray()[0].addParentNode(nodeName, lvlNumber);
        return newParent;
    }

    @Override
    public ParentArrayNode<T> addParentArrayNode(String nodeName, int lvlNumber, int occursNumber) {
        ParentArrayNode newParent = getArray()[0].addParentArrayNode(nodeName, lvlNumber,occursNumber);
        return newParent;
    }


    public ParentNode<T>[] getArray() {
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentArrayNode)) return false;
        if (!super.equals(o)) return false;
        ParentArrayNode<?> that = (ParentArrayNode<?>) o;
        return Arrays.equals(getArray(), that.getArray());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(getArray());
        return result;
    }
}
