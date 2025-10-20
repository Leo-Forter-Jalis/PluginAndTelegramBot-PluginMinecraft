package com.lfj.plugin.patb;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import com.lfj.plugin.patb.botmanager.BotBootManager;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

public class UnloadCommand {
    public static LiteralCommandNode<CommandSourceStack> unloadCommand(BotBootManager manager){
        return Commands.literal("tgplyai")
                .requires(sender ->{
                    if(sender.getSender() instanceof Player player)
                        return player.getName().equals("Leo_Forter_Jalis");
                    return false;
                })
                .then(Commands.literal("unload").executes(ctx ->{
                    manager.unload();
                    return Command.SINGLE_SUCCESS;
                })).build();
    }
}
