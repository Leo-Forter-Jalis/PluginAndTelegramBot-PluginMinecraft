package com.lfj.plugin.api;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public interface BotPlugin {
    void onLoad();
    void onEnabled();
    void onDisabled();
    JavaPlugin getPlugin();
    MetaData getMetaData();
    File getDataFolder();
}
