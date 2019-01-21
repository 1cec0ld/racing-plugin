package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class InteractListener implements Listener{

    public InteractListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
    }
    
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        
    }
    
    
    

    //player.setMetadata("tpd", new FixedMetadataValue(controller.getPlugin(), true));

    //if(!(event.getEntity().hasMetadata("racingGame")))return;
    //player.removeMetadata("tpd", controller.getPlugin());
}
