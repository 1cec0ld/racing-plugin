package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRiptideEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class RipTideListener implements Listener{
    
    public RipTideListener(Racing racing){
        racing.getServer().getPluginManager().registerEvents(this, racing);
    }

    @EventHandler
    public void onRipTide(PlayerRiptideEvent event){
        if(!PlayerManager.isRacing(event.getPlayer()))return;
        PlayerManager.setCategory(event.getPlayer(), "riptide");
    }
}
