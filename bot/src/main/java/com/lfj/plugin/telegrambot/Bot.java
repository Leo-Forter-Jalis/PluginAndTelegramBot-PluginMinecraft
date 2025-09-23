package com.lfj.plugin.telegrambot;

import org.bukkit.plugin.java.JavaPlugin;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;


import static com.lfj.plugin.telegrambot.handler.Handle.handle;
import static com.lfj.plugin.telegrambot.handler.SendToUser.send;
import static com.lfj.plugin.telegrambot.handler.InlineHandle.inlineHandle;

public class Bot implements LongPollingSingleThreadUpdateConsumer {

    private static Bot instance;
    public static void init(String token, JavaPlugin plugin){
        if(instance != null) throw new IllegalStateException("Bot not is null.");
        if(token == null || token.equals("TOKEN")) throw new IllegalArgumentException("client is null");
        if(plugin == null) throw new IllegalArgumentException("plugin is null");
        instance = new Bot(token, plugin);
    }
    public static Bot getInstance(){
        if(instance == null) throw new IllegalStateException("Bot is null. Call method init");
        return instance;
    }

    private TelegramClient client;
    private JavaPlugin plugin;

    private Bot(String token, JavaPlugin plugin){
        this.client = new OkHttpTelegramClient(token);
        this.plugin = plugin;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            if(update.getMessage().hasText()) {
                this.plugin.getLogger().info("Bot received message.");
                String[] parts = update.getMessage().getText().split(" ");
                String command = parts[0];
                handle(command, parts, this.client, update, this.plugin);
            }
        } else if(update.hasInlineQuery()){
            inlineHandle(update, this.client, this.plugin);
        }
    }

    public void sendToUserMessage(String text, long userID){
        send(userID, text, this.client);
    }

}
