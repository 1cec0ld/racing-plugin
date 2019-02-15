package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class PotionSplashListener implements Listener{
    
    public PotionSplashListener(Racing racing){
        racing.getServer().getPluginManager().registerEvents(this, racing);
    }
    
    @EventHandler
    public void onSplash(PotionSplashEvent event){
        for(Entity each : event.getAffectedEntities()){
            if(each instanceof Player){
                if(PlayerManager.isRacing((Player)each)){
                    PlayerManager.setCategory((Player)each, "potions");
                }
            }
        }
    }
    
}
