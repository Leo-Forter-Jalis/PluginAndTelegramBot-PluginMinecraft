package com.lfj.plugin.patb.botmanager;

public class Run {
    private DataHolder holder;
    public Run(){
        this.holder = DataHolder.getInstance();
    }
    public void run(){
        for(String key : this.holder.keySet()){
            this.holder.get(key).getThread().start();
        }
    }
}
