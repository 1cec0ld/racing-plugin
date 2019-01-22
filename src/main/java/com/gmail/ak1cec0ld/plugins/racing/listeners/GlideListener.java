package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class GlideListener implements Listener{
    
    public GlideListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
    }

    @EventHandler
    public void onGlideToggle(EntityToggleGlideEvent event){
        if(!(event.getEntity() instanceof Player))return;
        if(!PlayerManager.isRacing((Player)event.getEntity()))return;
        PlayerManager.setCategory((Player)event.getEntity(), "elytra");
    }
    
}
