package com.zthulj.zcopybook.model;

import com.zthulj.zcopybook.Utils;
import com.zthulj.zcopybook.engine.ZLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ZCopyBookTest {

    @Test
    public void factory_copybook_shouldHaveAllRegisteredValueNodes() throws IOException {
        ZLoader zloader = new ZLoader();
        ZCopyBook zCopybook = zloader.load(Utils.fileFromResource("copybook/simplecopybook.cbl"));
        Assert.assertEquals(9,zCopybook.getValueNodes().size());
    }

    @Test
    public void factory_copybookWithArrays_shouldHaveAllRegisteredValueNodes() throws IOException {
        ZLoader zloader = new ZLoader();
        ZCopyBook zCopybook = zloader.load(Utils.fileFromResource("copybook/aParentOccursAndSomeChildsAtSameLevel.cbl"));
        Assert.assertEquals(6,zCopybook.getValueNodes().size());
    }

    @Test
    public void totalWaitedLength_copybook_shouldGetCorrectSize() throws IOException {
        ZLoader zloader = new ZLoader();
        ZCopyBook zCopybook = zloader.load(Utils.fileFromResource("copybook/simplecopybook.cbl"));

        Assert.assertEquals(80,zCopybook.getWaitedLength());
    }

}
