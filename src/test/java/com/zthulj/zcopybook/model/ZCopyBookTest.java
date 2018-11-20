package com.zthulj.zcopybook.model;

import com.zthulj.zcopybook.engine.ZLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.io.File;
import java.io.IOException;

public class ZCopyBookTest {

    @Test
    public void factory_copybook_shouldHaveAllRegisteredValueNodes() throws IOException {
        ZLoader zloader = new ZLoader();
        ZCopyBook zCopybook = zloader.load(fileFromResource("copybook/simplecopybook.cbl"));
        Assert.assertEquals(9,zCopybook.getValueNodes().size());
    }

    @Test
    public void factory_copybookWithArrays_shouldHaveAllRegisteredValueNodes() throws IOException {
        ZLoader zloader = new ZLoader();
        ZCopyBook zCopybook = zloader.load(fileFromResource("copybook/aParentOccursAndSomeChildsAtSameLevel.cbl"));
        Assert.assertEquals(6,zCopybook.getValueNodes().size());
    }


    private File fileFromResource(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }
}
