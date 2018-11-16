package com.zthulj.zcopybook.model;

import java.io.Serializable;
import java.util.Objects;

public final class Coordinates implements Serializable {
    private final int start;
    private final int end;

    private Coordinates(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static Coordinates from(int start, int end) {
        if(end < start)
            throw new IllegalArgumentException("end can't be less than start. It should be at least equal");
        return new Coordinates(start, end);
    }

    public int getSize(){
        return end - start + 1;
    }

    public int getStart() {
        return start;
    }


    public int getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return getStart() == that.getStart() &&
                getEnd() == that.getEnd();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd());
    }
}
