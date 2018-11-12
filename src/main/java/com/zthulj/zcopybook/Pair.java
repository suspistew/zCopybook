package com.zthulj.zcopybook;

import java.util.Objects;


public final class Pair<T,U> {
    public Pair(T first, U second) {
        this.value = second;
        this.key = first;
    }

    private final T key;
    private final U value;

    public T getKey() {
        return key;
    }

    public U getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) &&
                Objects.equals(value, pair.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
