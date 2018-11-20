package com.zthulj.zcopybook.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@ToString
public final class Coordinates implements Serializable {
    
	private static final long serialVersionUID = 6100935963637812753L;
	private final int start;
    private final int end;

    public static Coordinates from(int start, int end) {
        if(end < start)
            throw new IllegalArgumentException("end can't be less than start. It should be at least equal");
        return new Coordinates(start, end);
    }

    public int getSize(){
        return end - start + 1;
    }
}
