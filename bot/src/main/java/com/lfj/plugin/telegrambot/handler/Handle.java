package com.lfj.plugin.telegrambot.handler;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.OfflinePlayer;

import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.lfj.plugin.telegrambot.algorithm.Enumeration.enumerationElements;

import com.lfj.datacontroller.DataController;
import com.lfj.datacontroller.PlayerData;

public class Handle {
    // Method add in list Players

    public static void handle(String command, String[] arguments, TelegramClient client, Update update, JavaPlugin plugin){
        if(command.equals("/add_me"))
            handleAddMeCommand(arguments, client, update, plugin);
        else if(command.equals("/start") || command.equals("/help"))
            handleStartOrHelp(client, update, plugin);
    }

    private static void handleAddMeCommand(String[] args, TelegramClient client, Update update, JavaPlugin plugin){
        User user = update.getMessage().getFrom();
        DataController controller = plugin.getServer().getServicesManager().load(DataController.class);
        if(controller.contains(user.getId())){
            sendMessageToUser(client, user, "Вы уже есть в списке!", plugin);
            return;
        }
        PlayerData data = new PlayerData();
        plugin.getServer().getScheduler().runTask(plugin, ()->{
            OfflinePlayer player = plugin.getServer().getOfflinePlayer(args[1]);
            player.setWhitelisted(true);
            if(!player.isWhitelisted()){
                plugin.getLogger().info("Add new player call error.");
                sendMessageToUser(client, user, "В процессе добавления в список, произошла ошибка!", plugin);
                return;
            }
            plugin.getLogger().info("Player " + args[1] + " added to whitelist.");
            sendMessageToUser(client, user, "Вы были успешно добавлены в список!\n\nIp >> e2.aurorix.net:25352\n\nПриятной игры! :)", plugin);
        });
        enumerationElements(args, data, user, plugin);
        controller.add(args[1], data);
    }
    /*
    private static void putData(String[] args, PlayerData data, User user){
        for(int i = 2; i < args.length; i++){
            if(args[i].startsWith("true") || args[i].startsWith("false")){
                if(args[i].startsWith("true")) data.setSendCodeVerification(true);
                data.setSendCodeVerification(false);
            } else if(args[i].startsWith("#")){
                data.setHexCode(args[i]);
            } else if(args[i].startsWith("D") || args[i].startsWith("d")){
                data.setUserName(args[i]);
            }
        }
        data.setUserId(user.getId());
        data.setUuid(null);
    }
    */
    public static void handleStartOrHelp(TelegramClient client, Update update, JavaPlugin plugin){
        String text = update.getMessage().getText();
        if(text.equals("/start"))
            sendMessageToUser(client, update.getMessage().getFrom(), "Доброго времени суток!\nДанный бот предназначен для добавления участников в белый список сервера.\n\nЧтобы добавтить себя на сервер, вам нужно написать команду > /add_me [NickName] (hex=#color_code)\nПример №1 > /add_me Player\nПример №2 > /add_me Player hex=#FFFFFF\n\nГде взять color_code?\nМожно воспользоваться сайтом https://csscolor.ru/ , вам лишь выбрать цвет и скопировать строку под названием 'HEX'(Вставить надо с #-символом).", plugin);
        else if(text.equals("/help"))
            sendMessageToUser(client, update.getMessage().getFrom(), "Доброго времени суток!\nДанный бот предназначен для добавления участников в белый список сервера.\n\nЧтобы добавтить себя на сервер, вам нужно написать команду > /add_me [NickName] (hex=#color_code)\nПример №1 > /add_me Player\nПример №2 > /add_me Player hex=#FFFFFF\n\nГде взять color_code?\nМожно воспользоваться сайтом https://csscolor.ru/ , вам лишь выбрать цвет и скопировать строку под названием 'HEX'(Вставить надо с #-символом).", plugin);
        else{
            sendMessageToUser(client, update.getMessage().getFrom(), "Введите команду /start или /help для подробностей.", plugin);
        }
    }
    private static void sendMessageToUser(TelegramClient client, User user, String text, JavaPlugin plugin){
        SendMessage message = SendMessage
                .builder()
                .chatId(user.getId())
                .text(text)
                .build();
        try{client.execute(message);}catch (TelegramApiException e){e.printStackTrace();}
        StringBuilder sb = new StringBuilder();
        sb.append("[User >> " + user.getFirstName() + "]")
                        .append("[UserID >> " + user.getId() + "]")
                                .append("[Answer >> " + text + "]");
        plugin.getLogger().info(sb.toString());
    }
}
