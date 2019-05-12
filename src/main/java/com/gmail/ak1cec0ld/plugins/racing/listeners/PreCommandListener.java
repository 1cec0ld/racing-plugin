package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class PreCommandListener implements Listener{
    
    Racing plugin;
    
    public PreCommandListener(Racing racing){
        racing.getServer().getPluginManager().registerEvents(this, racing);
        this.plugin = racing;
    }
    
    @EventHandler
    public void onPreCommandEvent(PlayerCommandPreprocessEvent event){
        plugin.debug(event.getMessage());
        PlayerManager.setCommandSent(event.getPlayer());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> PlayerManager.removeCommandSent(event.getPlayer()),5L);
    }
    
}
