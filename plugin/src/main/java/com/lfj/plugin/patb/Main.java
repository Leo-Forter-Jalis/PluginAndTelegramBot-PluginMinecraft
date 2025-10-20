package com.lfj.plugin.patb;

import com.lfj.plugin.patb.botmanager.BotBootManager;
import com.lfj.plugin.patb.listener.JoinEvent;
import com.lfj.plugin.patb.listener.ConnectionsPlayers;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.plugin.java.JavaPlugin;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

public final class Main extends JavaPlugin {
    private BotBootManager botBootManager;
    @Override
    public void onEnable() {
        if(!getDataFolder().exists())
            getDataFolder().mkdirs();
        botBootManager = new BotBootManager(getDataFolder(), this);
        botBootManager.load();
        LiteralArgumentBuilder argument = Commands.literal("tgplyai");
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, command ->{
            command.registrar().register(LoadCommand.loadCommand(botBootManager));
            command.registrar().register(UnloadCommand.unloadCommand(botBootManager));
        });
    }

    @Override
    public void onDisable() {
        botBootManager.unload();
    }
}
