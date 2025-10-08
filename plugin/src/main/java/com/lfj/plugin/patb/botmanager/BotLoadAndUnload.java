package com.lfj.plugin.patb.botmanager;

import java.io.File;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.List;
import java.util.ArrayList;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lfj.plugin.api.BotPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BotLoadAndUnload {
    private List<URLClassLoader> loaders;
    private List<BotPlugin> plugins;
    private ExecutorService service;
    private JavaPlugin plugin;
    public BotLoadAndUnload(JavaPlugin plugin){
        this.loaders = new ArrayList<>();
        this.plugins = new ArrayList<>();
        this.service = Executors.newSingleThreadExecutor();
        this.plugin = plugin;
    }
    public void load(File directory) throws Exception {
        plugin.getLogger().info("Bot loader starting...");
        File[] files = directory.listFiles((dir, name) -> name.endsWith("jar"));
        for(File jar : files){
            plugin.getLogger().info("Load bots...");
            plugin.getLogger().info("File name >> " + jar.getName().replace(".jar", ""));
            URL url = jar.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{url}, this.getClass().getClassLoader());
            plugin.getLogger().info("Reading metadata...");
            ObjectMapper mapper = new ObjectMapper(new JsonFactory());
            MetaData data = mapper.readValue(loader.getResource("bot.json"), MetaData.class);
            plugin.getLogger().info("Metadata:\nName >> " + data.getName() + ", Author >> " + data.getAuthor() + ", Version >> " + data.getVersion() + ", Main-Class >> " + data.getMainClass());
            Class<?> clazz = loader.loadClass(data.getMainClass());
            if(BotPlugin.class.isAssignableFrom(clazz)) {
                plugin.getLogger().info("Bot '" + jar.getName().replace(".jar", "") + "' starting...");
                BotPlugin pluginBot = (BotPlugin) clazz.getDeclaredConstructor().newInstance();
                Method method = pluginBot.getClass().getSuperclass().getDeclaredMethod("setPlugin", JavaPlugin.class);
                method.setAccessible(true);
                method.invoke(pluginBot, this.plugin);
                startThreadTask(pluginBot, jar.getName().toLowerCase().substring(0, jar.getName().indexOf(".jar")));
                pluginBot.onEnabled();
                plugin.getLogger().info("Bot '" + jar.getName().replace(".jar", "") + "' started!");
                plugins.add(pluginBot);
                loaders.add(loader);
            }else{
                loader.close();
            }
        }
    }
    public void unload() throws IOException {
        plugin.getLogger().info("Unloaded bots...");
        for(BotPlugin plugin : plugins){
            plugin.onDisabled();
            plugin.interrupt();
        }
        for(URLClassLoader loader : loaders){
            loader.close();
        }
        plugins.clear();
        loaders.clear();
        plugin.getLogger().info("Unload bots!");
    }
    private void startThreadTask(BotPlugin botPlugin, String name){
        botPlugin.setName(name);
        botPlugin.start();
    }
}
