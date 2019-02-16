package com.gmail.ak1cec0ld.plugins.racing.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class TeleportListener implements Listener{

    private Racing plugin;
        
    public TeleportListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
        this.plugin = racing;
    }
    
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(!(PlayerManager.isRacing(event.getPlayer())))return;
        if(PlayerManager.hasTeleportWorkaround(event.getPlayer()) || isNearVehicle(event.getPlayer()))return; //we only ignore tp's during races if via vanilla commandblocks or onto vehicles
        if(event.getCause().equals(TeleportCause.COMMAND)){
            PlayerManager.setTeleportWorkaround(event.getPlayer());
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> PlayerManager.hasTeleportWorkaround(event.getPlayer()),5L);
        }
                PlayerManager.setCategory(event.getPlayer(), "teleport");
    }
    
    private Boolean isNearVehicle(Entity loc){
        List<Entity> nearby = loc.getNearbyEntities(2, 2, 2);
        for(Entity each : nearby){
            if (each instanceof Vehicle){
                return true;
            }
        }
        return false;
    }
}
