package com.zthulj.zcopybook.model;

import org.junit.Assert;
import org.junit.Test;

public class CoordinatesTest {

    @Test
    public void from_x1_y10_shouldReturnCorrectCoordinates(){
        Coordinates coords = Coordinates.from(1,10);
        Assert.assertEquals(1,coords.getStart());
        Assert.assertEquals(10,coords.getEnd());
    }

    @Test
    public void from_x1_y1_shouldReturnCorrectCoordinates(){
        Coordinates coords = Coordinates.from(1,1);
        Assert.assertEquals(1,coords.getStart());
        Assert.assertEquals(1,coords.getEnd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void from_x10_y1_shouldThrowIllegalArgExc(){
        Coordinates coords = Coordinates.from(10,1);
        Assert.assertEquals(1,coords.getStart());
        Assert.assertEquals(10,coords.getEnd());
    }

    @Test
    public void getSize_x2y9_shouldReturn8(){
        Coordinates coords = Coordinates.from(2,9);
        Assert.assertEquals(8,coords.getSize());
    }

    @Test
    public void getSize_x0_y0_shouldReturn1(){
        Coordinates coords = Coordinates.from(0,0);
        Assert.assertEquals(1,coords.getSize());
    }
}