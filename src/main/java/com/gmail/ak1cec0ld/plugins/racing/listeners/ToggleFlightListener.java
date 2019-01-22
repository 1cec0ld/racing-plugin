package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class ToggleFlightListener implements Listener{

    public ToggleFlightListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
    }

    @EventHandler
    public void onToggleFly(PlayerToggleFlightEvent event){
        if(!PlayerManager.isRacing(event.getPlayer()))return;
        PlayerManager.endRace(event.getPlayer());
        event.getPlayer().sendMessage("Race Progress removed due to flight detection! Disqualified!");
    }
    
}
