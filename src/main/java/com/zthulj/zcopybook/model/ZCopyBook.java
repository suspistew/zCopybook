package com.zthulj.zcopybook.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ZCopyBook<T> {

    private final RootNode<T> rootNode;
    private List<ValueNode<T>> valueNodes;

    public static <T> ZCopyBook<T> from(RootNode<T> rootNodeCopybook){
        List<ValueNode<T>> allValueNodes = new ArrayList<>();
        rootNodeCopybook.getChilds().forEach(
                (k,v) -> allValueNodes.addAll(v.getAllValueNodes())
        );
        return new ZCopyBook<>(rootNodeCopybook,allValueNodes);
    }
}
