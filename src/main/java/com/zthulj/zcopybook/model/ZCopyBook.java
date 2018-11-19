package com.zthulj.zcopybook.model;

public final class ZCopyBook<T> {
    private RootNode<T> rootNode;


    private ZCopyBook(RootNode<T> rootNode) {
        this.rootNode = rootNode;
    }

    public static ZCopyBook readCopyBook(String string){
        return null;
    }


}
