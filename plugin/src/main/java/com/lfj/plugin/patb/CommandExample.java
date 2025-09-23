package com.lfj.plugin.patb;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

public class CommandExample {
    public static LiteralArgumentBuilder<CommandSourceStack> register(final String commandName){
        return Commands.literal(commandName)
                .requires(sender ->{
                  if(sender.getSender() instanceof Player player){
                      return player.getName().equals("Leo_Forter_Jallis");
                  }
                  return false;
                })
                .then(Commands.literal("lol").executes(ctx -> {
                    ctx.getSource().getSender().sendMessage("LOL");
                    return Command.SINGLE_SUCCESS;
                }))
                .executes(ctx -> {
                    ctx.getSource().getSender().sendMessage("Base Command.");
                    return Command.SINGLE_SUCCESS;
                });
    }
}
