package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class TeleportListener implements Listener{

    //private Racing plugin;
        
    public TeleportListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
        //this.plugin = racing;
    }
    
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(!(PlayerManager.isRacing(event.getPlayer())))return;
        if(PlayerManager.hasCommandSent(event.getPlayer()) || 
           event.getCause().equals(TeleportCause.ENDER_PEARL) || 
           event.getCause().equals(TeleportCause.CHORUS_FRUIT)){
            PlayerManager.setCategory(event.getPlayer(), "teleport");
        }
    }
}
