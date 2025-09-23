package com.lfj.plugin.telegrambot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.plugin.java.JavaPlugin;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.lfj.plugin.telegrambot.token.Read.readToken;

public class TelegramBot {
    private static TelegramBot instance;
    private JavaPlugin plugin;
    private ExecutorService service;

    public synchronized static void init(JavaPlugin plugin){
        if(instance != null) throw new IllegalStateException("Telegram bot already initialized. Call close() first if you want to reinitialize.");
        if(plugin == null) throw new IllegalArgumentException("Argument plugin cannot be null.");
        instance = new TelegramBot(plugin);
    }

    public static TelegramBot getInstance(){
        if(instance == null) throw new IllegalStateException("Telegram not initialized. Call init() first.");
        return instance;
    }

    private TelegramBotsLongPollingApplication application;
    private final String token;

    private TelegramBot(JavaPlugin plugin){
        this.plugin = plugin;
        this.token = readToken(this.plugin.getDataFolder());
        this.service = Executors.newSingleThreadExecutor();
    }
    private void start(){
        if(this.token.equals("TOKEN")){
            this.plugin.getLogger().warning("Token is not worked.");
            return;
        }
        application = new TelegramBotsLongPollingApplication();
        service.submit(() -> {
            try{
                Bot.init(token, this.plugin);
                application.registerBot(token, Bot.getInstance());
                this.plugin.getLogger().info("Telegram bot is started!");
            }catch (TelegramApiException e){
                this.plugin.getLogger().severe("Telegram bot stoping >> " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    public void run(){
        this.plugin.getServer().getLogger().info("Starting Telegram bot...");
        start();
    }

    public synchronized void close(){
        this.plugin.getLogger().info("Stoping Telegram bot...");
        if(application != null) {
            try {
                application.unregisterBot(token);
                application.stop();
                application.close();
                application = null;
                this.plugin.getLogger().info("Telegram bot stop.");
            } catch (Exception e) {
                this.plugin.getLogger().severe("Error stoping Telegram bot > " + e.getMessage());
                e.printStackTrace();
            }
        }
        if(service != null){
            service.shutdown();
            service = null;
            this.plugin.getLogger().info("Thread closed.");
        }
        instance = null;
        this.plugin = null;
    }

}
