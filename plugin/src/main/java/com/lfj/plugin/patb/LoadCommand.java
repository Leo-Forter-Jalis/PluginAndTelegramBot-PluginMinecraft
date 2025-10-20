package com.lfj.plugin.patb;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import com.lfj.plugin.patb.botmanager.BotBootManager;

public class LoadCommand {
    public static LiteralCommandNode<CommandSourceStack> loadCommand(BotBootManager manager){
        return Commands.literal("tgplyai")
                .requires(sender ->{
                    if(sender.getSender() instanceof Player player)
                        return player.getName().equals("Leo_Forter_Jalis");
                    return false;
                })
                .then(Commands.literal("load").executes(ctx ->{
                    manager.load();
                    return Command.SINGLE_SUCCESS;
                })).build();
    }
}
