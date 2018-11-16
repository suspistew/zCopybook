package com.zthulj.zcopybook.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                    Coordinates nextCoords = calculateCoordinates((ValueNode)entry.getValue(),nextStart);
                    getArray()[i].addValueNode(entry.getKey(),nextCoords);
                    nextStart += nextCoords.getSize();
                }else if(entry.getValue() instanceof ParentArrayNode){

                }

            }
        }
        return nextStart;
    }



    private Coordinates calculateCoordinates(ValueNode value, int nextStart) {
        logger.debug(nextStart + " " +  value.getCoordinates().getSize());
        return Coordinates.from(nextStart, nextStart + value.getCoordinates().getSize() - 1);
    }


    @Override
    public ValueNode<T> addValueNode(String nodeName, Coordinates coords) {
        return getArray()[0].addValueNode(nodeName, coords);
    }


    public ParentNode<T>[] getArray() {
        return array;
    }
}
