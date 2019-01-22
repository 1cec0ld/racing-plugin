package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class TeleportListener implements Listener{
    
    Racing plugin;

    public TeleportListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
        plugin = racing;
    }
    
    
    @EventHandler
    public void onTeleport(EntityTeleportEvent event){
        if(!(event.getEntity() instanceof Player))return;
        if(!(PlayerManager.isRacing((Player)event.getEntity())))return;
        ((Player)event.getEntity()).sendMessage("Race Progress removed due to teleportation! Disqualified!");
        PlayerManager.endRace((Player)event.getEntity());
    }
}
