package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class TeleportListener implements Listener{
    
    Racing plugin;

    public TeleportListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
        plugin = racing;
    }
    
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(!(PlayerManager.isRacing(event.getPlayer())))return;
        (event.getPlayer()).sendMessage("Race Progress removed due to teleportation! Disqualified!");
        PlayerManager.endRace(event.getPlayer());
    }
}
