package com.zthulj.zcopybook.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zthulj.zcopybook.factory.NodeFactory;
import com.zthulj.zcopybook.serializer.ValueNodeSerializer;

import java.util.Objects;

@JsonSerialize(using = ValueNodeSerializer.class)
public final class ValueNode<T> extends Node<T> {
    private T value;
    private Coordinates coordinates;
    private ValueType valueType;

    public enum ValueType{
        STRING, SIGNED_INT, SIGNED_FLOAT
    }

    public ValueNode(ParentNode<T> parent,Coordinates coordinates) {
        super(parent);
        this.coordinates = coordinates;
        this.valueType = ValueType.STRING;
    }

    public ValueNode(ParentNode<T> parent,Coordinates coordinates, ValueType valueType){
        super(parent);
        this.coordinates = coordinates;
        this.valueType = valueType;
    }

    @Override
    public int copyInto(ParentNode destination, int cursorPosition, String name) {
        Coordinates nextCoords = calculateCoordinates(this, cursorPosition);
        ValueNode valueNode = NodeFactory.createValueNode(destination,nextCoords,this.valueType);
        destination.addChild(valueNode,name);
        cursorPosition += nextCoords.getSize();
        return cursorPosition;
    }

    private Coordinates calculateCoordinates(ValueNode value, int nextStart) {
        return Coordinates.from(nextStart, nextStart + value.getCoordinates().getSize() - 1);
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

    public ValueType getValueType() {
        return valueType;
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
