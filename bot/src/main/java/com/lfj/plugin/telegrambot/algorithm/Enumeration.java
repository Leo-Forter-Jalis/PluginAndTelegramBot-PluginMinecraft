package com.lfj.plugin.telegrambot.algorithm;

import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.api.objects.User;
import com.lfj.datacontroller.PlayerData;

public class Enumeration {
    public static void enumerationElements(String[] args, PlayerData data, User user, JavaPlugin plugin){
        data.setUserId(user.getId());
        for(String s : args){
            if(plugin == null) break;
            plugin.getLogger().info("[" + s + "]");
        }
        boolean userNameSet = false;
        for(int i = 2; i < args.length; i++){
            if(args[i].equals("true") || args[i].equals("false")){
                data.setSendCodeVerification(Boolean.parseBoolean(args[i]));
            } else if (args[i].startsWith("#")) {
                data.setHexCode(args[i]);
            }
            if(args[i].startsWith("d")){
                data.setDisplayName(args[i].substring(1));
                userNameSet = true;
            }
        }
        if(!userNameSet){
            data.setDisplayName(user.getFirstName());
        }
    }
}
