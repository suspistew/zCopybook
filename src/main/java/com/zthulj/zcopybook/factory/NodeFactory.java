package com.zthulj.zcopybook.factory;

import com.zthulj.zcopybook.model.*;

import java.util.LinkedHashMap;

public class NodeFactory {

    public static <T> RootNode<T> createRootNode() {
        return new RootNode<T>(new LinkedHashMap());
    }


    public static <T> ParentNode<T> createParentNode(ParentNode<T> parent, int lvlNumber) {
        return new ParentNode<T>(parent, new LinkedHashMap<>(), lvlNumber);
    }

    public static <T> ValueNode<T> createValueNode(ParentNode<T> parent, Coordinates coords) {
        return createValueNode(parent, coords, ValueNode.ValueType.STRING);
    }

    public static <T>  ValueNode<T> createValueNode(ParentNode<T> parent, Coordinates coords, ValueNode.ValueType type) {
        return new ValueNode(parent, coords, type);
    }

    public static <T> ParentArrayNode<T> createParentNodeArray(ParentNode<T> parent, int lvlNumber, int occursNumber) {
        return new ParentArrayNode(parent, lvlNumber, occursNumber);
    }
}
