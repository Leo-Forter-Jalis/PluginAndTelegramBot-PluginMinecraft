package com.lfj.plugin.telegrambot;

import com.lfj.plugin.api.telegram.TelegramBotPlugin;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.lfj.plugin.telegrambot.token.Read.readToken;

import static com.lfj.plugin.telegrambot.handler.Handle.handle;
import static com.lfj.plugin.telegrambot.handler.Handle.handleStartOrHelp;
import static com.lfj.plugin.telegrambot.handler.InlineHandle.inlineHandle;

public class TelegramBot extends TelegramBotPlugin {
    private TelegramBotsLongPollingApplication application;
    private BotSession session;
    private TelegramClient client;
    private String token;
    @Override
    public void onEnabled(){
        getPlugin().getLogger().info("Starting bots...");
        this.application = new TelegramBotsLongPollingApplication();
        this.token = readToken(getPlugin().getDataFolder());
        this.client = new OkHttpTelegramClient(token);
        startBot();
    }

    private void startBot(){
        if(token == null || token.equals("TOKEN")){
            getPlugin().getLogger().info("Token is null");
            onDisabled();
        }
        try{
            this.session = this.application.registerBot(token, this);
            getPlugin().getLogger().info("Bot Started!");
        }catch (TelegramApiException e){ e.printStackTrace(); }
    }

    @Override
    public void onDisabled(){
        getPlugin().getLogger().info("Stoping bot...");
        try{
            if(this.session != null || this.session.isRunning()){
                getPlugin().getLogger().info("Stoping Telegram session...");
                session.stop();
                session.close();
                session = null;
            }
            getPlugin().getLogger().info("Stopping Telegram Bot application...");
            application.unregisterBot(token);
            application.stop();
            application.close();
            client = null;
            token = null;
            getPlugin().getLogger().info("Bot stopped!");
        }catch (TelegramApiException e){
            getPlugin().getLogger().severe(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            getPlugin().getLogger().severe(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void consume(Update update) {
        if(update.hasMessage()){
            if(update.getMessage().hasText()){
                String text = update.getMessage().getText();
                if(text.startsWith("/start") || text.startsWith("/help")){
                    handleStartOrHelp(client, update, getPlugin());
                } else if (text.startsWith("/add_me")) {
                    String command = text.substring(0, text.indexOf(' '));
                    String a = text.substring(command.length()+1);
                    String[] args = a.split(" ");
                    handle(command, args, client, update, getPlugin());
                }
            }
        } else if (update.hasInlineQuery()) {
            inlineHandle(update, client, getPlugin());
        }
    }
}
