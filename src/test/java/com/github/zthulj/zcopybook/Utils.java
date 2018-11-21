package com.github.zthulj.zcopybook;

import java.io.File;

public class Utils {
    public static File fileFromResource(String path) {
        return new File(Utils.class.getClassLoader().getResource(path).getFile());
    }
}
