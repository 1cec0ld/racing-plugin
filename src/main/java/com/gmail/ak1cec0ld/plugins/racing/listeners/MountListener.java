package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;

public class MountListener implements Listener{
    
    
    @EventHandler
    public void onMountEntity(EntityMountEvent event){
        if(!(event.getEntity() instanceof Player))return;
        if(!PlayerManager.isRacing((Player)event.getEntity()))return;
        if(!(event.getMount() instanceof Vehicle && event.getMount() instanceof Mob)){
            ((Player)event.getEntity()).sendMessage("You can only ride vanilla-rideable animals for these races! Disqualified!");
            PlayerManager.endRace((Player)event.getEntity());
        }
        if(!PlayerManager.getCategory((Player)event.getEntity()).equals("elytra")){ //if it's elytra, keep it that way
            PlayerManager.setCategory((Player)event.getEntity(), "mounted");
        }
    }
    
}
