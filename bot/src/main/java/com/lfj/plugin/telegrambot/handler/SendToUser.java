package com.lfj.plugin.telegrambot.handler;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class SendToUser {
    public static void send(long chatID, String text, TelegramClient client){
        SendMessage message = SendMessage
                .builder()
                .chatId(chatID)
                .text(text)
                .build();
        try{ client.execute(message); }catch (TelegramApiException e) { e.printStackTrace(); }
    }
}
