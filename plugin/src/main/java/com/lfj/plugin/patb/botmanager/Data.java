package com.lfj.plugin.patb.botmanager;

import java.io.File;

import java.net.URLClassLoader;
import com.lfj.plugin.api.MetaData;
import com.lfj.plugin.api.BotPlugin;
import java.lang.Thread;

public class Data {
    private URLClassLoader classLoader;
    private BotPlugin plugin;
    private MetaData data;
    private Thread thread;
    private File jarPlugin;
    public Data(){
    }
    public Data(URLClassLoader classLoader, BotPlugin plugin, MetaData data, Thread thread, File jarPlugin){
        this.classLoader = classLoader;
        this.plugin = plugin;
        this.data = data;
        this.thread = thread;
        this.jarPlugin = jarPlugin;
    }
    // Setters
    public void setClassLoader(URLClassLoader classLoader){this.classLoader = classLoader;}
    public void setPlugin(BotPlugin plugin){this.plugin = plugin;}
    public void setData(MetaData data){this.data = data;}
    public void setThread(Thread thread){this.thread = thread;}
    public void setJarPlugin(File jarPlugin){this.jarPlugin = jarPlugin;}
    // Getters
    public URLClassLoader getClassLoader(){return this.classLoader;}
    public BotPlugin getPlugin(){return this.plugin;}
    public MetaData getData(){return this.data;}
    public Thread getThread(){return this.thread;}
    public File getJarPlugin(){return this.jarPlugin;}
}
