package com.zthulj.zcopybook.model;

public final class ZCopyBook<T> {
    private Node<T> rootNode;

    private ZCopyBook(Node<T> rootNode) {
        this.rootNode = rootNode;
    }

    public static ZCopyBook readCopyBook(String string){
        return null;
    }
}
