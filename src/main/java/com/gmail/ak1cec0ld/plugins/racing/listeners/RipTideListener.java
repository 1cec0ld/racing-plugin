package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRiptideEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;

public class RipTideListener implements Listener{

    @EventHandler
    public void onRipTide(PlayerRiptideEvent event){
        if(!PlayerManager.isRacing(event.getPlayer()))return;
        
    }
    
}
