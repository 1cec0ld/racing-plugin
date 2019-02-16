package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.entity.Boat;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class MountListener implements Listener{
    
    
    public MountListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
    }

    @EventHandler
    public void onMountEntity(EntityMountEvent event){
        if(!(event.getEntity() instanceof Player))return;
        if(!PlayerManager.isRacing((Player)event.getEntity()))return;
        if(!(event.getMount() instanceof Vehicle)){
            event.getEntity().sendMessage("You can only ride vanilla-rideable animals for these races! Disqualified!");
            PlayerManager.endRace((Player)event.getEntity());
            return;
        }
        Vehicle entity = (Vehicle) event.getMount();
        String newCategory;
        if(entity instanceof Boat){
            newCategory = "boat";
        } else if(entity instanceof Pig){
            newCategory = "pig";
        } else {
            newCategory = "mounted";
        }
        PlayerManager.setCategory((Player)event.getEntity(), newCategory);
    }
}