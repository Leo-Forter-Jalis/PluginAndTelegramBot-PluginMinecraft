package com.lfj.plugin.patb.listener;

import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

import io.papermc.paper.event.connection.PlayerConnectionValidateLoginEvent;

import com.lfj.datacontroller.DataController;
import com.lfj.datacontroller.PlayerData;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class JoinEvent implements Listener {
    private JavaPlugin plugin;
    public JoinEvent(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        PlayerData data = this.plugin.getServer().getServicesManager().load(DataController.class).get(player.getName());
        if(data == null) return;
        data.setUuid(player.getUniqueId());
        if(data.getHexCode() != null)
            player.displayName(Component.empty().append(Component.text(data.getDisplayName())).color(TextColor.fromHexString(data.getHexCode())));
        else{
            player.displayName(Component.empty().append(Component.text(data.getDisplayName())));
        }
    }

}
