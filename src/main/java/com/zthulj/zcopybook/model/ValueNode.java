package com.zthulj.zcopybook.model;

import java.util.Objects;

public final class ValueNode<T> extends Node<T> {
    private T value;
    private Coordinates coordinates;

    public ValueNode(ParentNode<T> parent,Coordinates coordinates) {
        super(parent);
        this.coordinates = coordinates;
    }

    @Override
    public boolean isParent() {
        return false;
    }

    public T getValue() {
        return value;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueNode)) return false;
        ValueNode<?> valueNode = (ValueNode<?>) o;
        return Objects.equals(getValue(), valueNode.getValue()) &&
                Objects.equals(getCoordinates(), valueNode.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getCoordinates());
    }
}
