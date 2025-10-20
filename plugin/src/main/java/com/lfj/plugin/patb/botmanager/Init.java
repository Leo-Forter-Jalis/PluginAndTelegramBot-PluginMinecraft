package com.lfj.plugin.patb.botmanager;

import java.io.File;

public class Init {
    public static boolean init(File directory){
        if(directory.exists()) return true;
        directory.mkdirs();
        return false;
    }
}
