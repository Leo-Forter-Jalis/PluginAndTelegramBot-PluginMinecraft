package com.lfj.plugin.api;

import java.lang.Thread;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org. bukkit.plugin.java.JavaPlugin;

public abstract class BotPlugin extends Thread implements LongPollingSingleThreadUpdateConsumer {
    protected JavaPlugin plugin;
    private void setPlugin(JavaPlugin plugin){
        this.plugin = plugin;
    }
    public JavaPlugin getPlugin(){return this.plugin;}
    @Override
    public void start(){
        onEnabled();
    }
    public abstract void onEnabled();
    public abstract void onDisabled();
}
