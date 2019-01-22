package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;

public class ToggleFlightListener implements Listener{

    @EventHandler
    public void onToggleFly(PlayerToggleFlightEvent event){
        if(!PlayerManager.isRacing(event.getPlayer()))return;
        PlayerManager.endRace(event.getPlayer());
        event.getPlayer().sendMessage("Race Progress removed due to flight detection! Disqualified!");
    }
    
}
