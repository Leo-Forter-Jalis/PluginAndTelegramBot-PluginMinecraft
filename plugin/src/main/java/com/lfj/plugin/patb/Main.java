package com.lfj.plugin.patb;

import com.lfj.plugin.patb.listener.JoinEvent;
import com.lfj.plugin.patb.listener.ConnectionsPlayers;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import static com.lfj.plugin.patb.CommandExample.register;
import static com.lfj.plugin.patb.PluginCommands.reloadCommand;

import com.lfj.plugin.telegrambot.TelegramBot;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();
        TelegramBot.init(this);
        TelegramBot.getInstance().run();
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new ConnectionsPlayers(), this);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, command -> {
            command.registrar().register(reloadCommand("tgbotyai", this));
            //command.registrar().register(register(), " ");
        });
        this.reloadConfig();
    }

    @Override
    public void onDisable() {
        TelegramBot.getInstance().close();
    }
}
