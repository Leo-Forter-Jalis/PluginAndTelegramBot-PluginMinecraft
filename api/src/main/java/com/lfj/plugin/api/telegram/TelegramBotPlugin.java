package com.lfj.plugin.api.telegram;

import com.lfj.plugin.api.BotPlugin;

import com.lfj.plugin.api.MetaData;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org. bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class TelegramBotPlugin implements LongPollingSingleThreadUpdateConsumer, BotPlugin {
    private JavaPlugin plugin;
    private MetaData data;
    private File directory;

    private void setPlugin(JavaPlugin plugin){this.plugin = plugin;}
    private void setDataFolder(File directory){this.directory = directory;}
    private void setMetaData(MetaData data){this.data = data;}

    @Override
    public void onLoad(){
        plugin.getLogger().info("Bot info:");
        plugin.getLogger().info("\n{\n" + "\t\"Name\": \"" + data.getName() + "\",\n\t\"Author\": \"" + data.getAuthor() + "\",\n\t\"Type\": \"Telegram Bot" + "\",\n\t\"Version\": \"" + data.getMajorVersion() + "." + data.getMinorVersion() + "\",\n\t\"Main-Class\": \"" + data.getMainClass() + "\"\n}");
        onEnabled();
    }

    @Override
    public MetaData getMetaData(){return data;}
    @Override
    public JavaPlugin getPlugin(){return this.plugin;}
    @Override
    public File getDataFolder(){return directory;}

    public abstract void onEnabled();
    public abstract void onDisabled();
}
