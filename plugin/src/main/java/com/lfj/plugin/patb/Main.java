package com.lfj.plugin.patb;

import com.lfj.plugin.patb.listener.JoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.lfj.plugin.telegrambot.TelegramBot;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();
        TelegramBot.init(this);
        TelegramBot.getInstance().run();
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
    }

    @Override
    public void onDisable() {
        TelegramBot.getInstance().close();
    }
}
