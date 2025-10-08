package com.lfj.plugin.patb;

import com.lfj.plugin.patb.listener.JoinEvent;
import com.lfj.plugin.patb.listener.ConnectionsPlayers;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import static com.lfj.plugin.patb.CommandExample.register;
import static com.lfj.plugin.patb.PluginCommands.reloadCommand;

import com.lfj.plugin.patb.botmanager.BotLoadAndUnload;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {
    private BotLoadAndUnload loadAndUnload;
    @Override
    public void onEnable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();
        File directory = new File(getDataFolder(), "bots");
        if(!directory.exists())
            directory.mkdirs();
        loadAndUnload = new BotLoadAndUnload(this);
        try {
            loadAndUnload.load(directory);
        } catch (Exception e) {
            getServer().getLogger().severe("[TelegramBot-PL-YAI]" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            loadAndUnload.unload();
        } catch (IOException e) {
            getServer().getLogger().severe(e.getMessage());
        }
    }
}
