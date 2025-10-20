package com.lfj.plugin.patb.botmanager;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BotBootManager {
    private boolean loading = false;
    private File directory;
    private Load load;
    private Run run;
    private Unload unload;
    public BotBootManager(File directory, JavaPlugin plugin){
        this.directory = new File(directory, "bots");
        if(!Init.init(this.directory)) {
            return;
        }
        this.load = new Load(this.directory, plugin);
        this.run = new Run();
        this.unload = new Unload();
    }
    public void load(){
        if(this.loading) return;
        this.load.load();
        this.loading = true;
        run();
    }
    private void run(){
        this.run.run();
    }
    public void unload(){
        if(!this.loading) return;
        this.unload.unload();
        this.loading = false;
    }
}
