package com.lfj.plugin.patb.botmanager;

import java.io.IOException;

public class Unload {
    private DataHolder dataHolder;
    public Unload(){
        this.dataHolder = DataHolder.getInstance();
    }
    public void unload(){
        for(String key : this.dataHolder.keySet()){
            Data data = this.dataHolder.get(key);
            data.getPlugin().onDisabled();
            data.getThread().interrupt();
            try {
                data.getThread().join(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                data.getClassLoader().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            this.dataHolder.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
