package com.lfj.plugin.patb.botmanager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.bukkit.plugin.java.JavaPlugin;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lfj.plugin.api.MetaData;
import com.lfj.plugin.api.telegram.TelegramBotPlugin;

public class Load {
    private File directory;
    private DataHolder holder;
    private JavaPlugin plugin;

    public Load(File directory, JavaPlugin plugin){
        this.directory = directory;
        this.holder = DataHolder.getInstance();
        this.plugin = plugin;
    }
    private Class<?> readFile(URLClassLoader loader, MetaData data){
        if(data == null) return null;
        try {
            return loader.loadClass(data.getMainClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Thread createThread(TelegramBotPlugin plugin){
        return new Thread(()->{
            try {
                Method onLoad = plugin.getClass().getSuperclass().getDeclaredMethod("onLoad");
                onLoad.setAccessible(true);
                onLoad.invoke(plugin);
            }catch (NoSuchMethodException e){
                e.printStackTrace();
            }catch (InvocationTargetException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }, plugin.getMetaData().getName());
    }
    private TelegramBotPlugin configuration(Class<?> clazz, MetaData data, boolean isTgBot){
        try {
            if(isTgBot){
                TelegramBotPlugin plugin = (TelegramBotPlugin) clazz.getDeclaredConstructor().newInstance();
                Method setPlugin = plugin.getClass().getSuperclass().getDeclaredMethod("setPlugin", JavaPlugin.class);
                Method setMetaData = plugin.getClass().getSuperclass().getDeclaredMethod("setMetaData", MetaData.class);
                setPlugin.setAccessible(true); setMetaData.setAccessible(true);
                setPlugin.invoke(plugin, this.plugin);
                setMetaData.invoke(plugin, data);
                return plugin;
            }else{
                return null;
            }
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }
        return null;
    }
    private MetaData getMetaData(URL botMetaData){
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            return mapper.readValue(botMetaData, MetaData.class);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public void load(){
        File[] jars = this.directory.listFiles((dir, name) -> name.endsWith("jar"));
        if(jars == null) return;
        for(File jar : jars){
            URLClassLoader classLoader = null;
            try{
                classLoader = new URLClassLoader(new URL[]{jar.toURI().toURL()}, this.getClass().getClassLoader());
                MetaData data = getMetaData(classLoader.findResource("bot.json"));
                if(data == null) continue;
                Class<?> clazz = readFile(classLoader, data);
                if(clazz == null) continue;
                if(TelegramBotPlugin.class.isAssignableFrom(clazz)){
                    TelegramBotPlugin plugin = configuration(clazz, data, true);
                    Thread thread = createThread(plugin);
                    this.holder.add(data.getName(), new Data(classLoader, plugin, data, thread, jar));
                }else{
                    continue;
                }
                classLoader = null;
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
