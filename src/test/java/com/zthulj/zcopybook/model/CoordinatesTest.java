package com.zthulj.zcopybook.model;

import org.junit.Assert;
import org.junit.Test;

public class CoordinatesTest {

    @Test
    public void from_x1_y10_shouldReturnCorrectCoordinates(){
        Coordinates coords = Coordinates.create(1,10);
        Assert.assertEquals(1,coords.getStart());
        Assert.assertEquals(10,coords.getEnd());
    }

    @Test
    public void from_x1_y1_shouldReturnCorrectCoordinates(){
        Coordinates coords = Coordinates.create(1,1);
        Assert.assertEquals(1,coords.getStart());
        Assert.assertEquals(1,coords.getEnd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void from_x10_y1_shouldThrowIllegalArgExc(){
        Coordinates coords = Coordinates.create(10,1);
        Assert.assertEquals(1,coords.getStart());
        Assert.assertEquals(10,coords.getEnd());
    }

    @Test
    public void getSize_x2y9_shouldReturn7(){
        Coordinates coords = Coordinates.create(2,9);
        Assert.assertEquals(7,coords.getSize());
    }

    @Test
    public void getSize_x0_y0_shouldReturn0(){
        Coordinates coords = Coordinates.create(0,0);
        Assert.assertEquals(0,coords.getSize());
    }
}
