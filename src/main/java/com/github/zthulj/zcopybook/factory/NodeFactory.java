package com.github.zthulj.zcopybook.factory;

import com.github.zthulj.zcopybook.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NodeFactory {

    public static <T extends Serializable> RootNode<T> createRootNode() {
        return new RootNode<>(new LinkedHashMap());
    }

    public static <T extends Serializable> ParentNode<T> createParentNode(ParentNode<T> parent, int lvlNumber) {
        return new ParentNode<>(parent, new LinkedHashMap<>(), lvlNumber);
    }

    public static <T extends Serializable> ValueNode<T> createValueNode(ParentNode<T> parent, Coordinates coords) {
        return createValueNode(parent, coords, ValueNode.ValueType.STRING);
    }

    public static <T extends Serializable>  ValueNode<T> createValueNode(ParentNode<T> parent, Coordinates coords, ValueNode.ValueType type) {
        return new ValueNode(parent, coords, type);
    }

    public static <T extends Serializable> ParentArrayNode<T> createParentNodeArray(ParentNode<T> parent, int lvlNumber, int occursNumber) {
        return new ParentArrayNode(parent, lvlNumber, occursNumber);
    }
}
