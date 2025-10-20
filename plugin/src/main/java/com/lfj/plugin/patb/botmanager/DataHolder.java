package com.lfj.plugin.patb.botmanager;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class DataHolder implements Closeable {
    private static DataHolder instance;
    public static DataHolder getInstance(){
        if(instance != null) return instance;
        return instance = new DataHolder();
    }
    private Map<String, Data> dataMap;
    private DataHolder(){
        this.dataMap = new HashMap<>();
    }
    public void add(String key, Data value){this.dataMap.put(key, value);}
    public void remove(String key){this.dataMap.remove(key);}
    public boolean hasKey(String key){return this.dataMap.containsKey(key);}
    public int length(){return this.dataMap.size();}
    public Data get(String key){return this.dataMap.get(key);}
    public Set<String> keySet(){return this.dataMap.keySet();}

    @Override
    public void close() throws IOException {
        this.dataMap.clear();
        this.dataMap = null;
        instance = null;
    }
}
