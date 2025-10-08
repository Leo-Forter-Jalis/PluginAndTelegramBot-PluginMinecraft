package com.lfj.plugin.patb;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.Command;

import org.bukkit.command.ConsoleCommandSender;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginCommands {
    public static LiteralCommandNode<CommandSourceStack> reloadCommand(final String firstLiteralName, JavaPlugin plugin){
        return Commands.literal(firstLiteralName)
                .requires(sender ->{
                    if(sender.getSender() instanceof Player player)
                        return player.getName().equals("Leo_Forter_Jalis");
                    if(sender.getSender() instanceof ConsoleCommandSender console){
                        return true;
                    }
                    return false;
                })
                .then(Commands.literal("reload").executes(ctx ->{
                       return Command.SINGLE_SUCCESS;
                })).build();
    }
}
