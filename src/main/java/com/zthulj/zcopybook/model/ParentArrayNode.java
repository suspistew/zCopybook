package com.zthulj.zcopybook.model;

public class ParentArrayNode<T> extends ParentNode<T> {
    public ParentArrayNode() {

        super(null,null,0);
    }

    @Override
    public boolean isParent() {
        return true;
    }
}
