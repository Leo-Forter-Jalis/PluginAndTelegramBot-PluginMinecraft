package com.lfj.plugin.api.config;

import com.lfj.plugin.api.MetaData;

import java.io.File;

public class Config {
    private File botPluginFolder;
    public Config(File directory, MetaData data){
        if(directory == null) throw new IllegalArgumentException("Argument 'directory' is null!");
        this.botPluginFolder = new File(directory, data.getName());
        if(!botPluginFolder.exists()) createDirectory();
    }
    private void createDirectory(){
        this.botPluginFolder.mkdirs();
    }

}
