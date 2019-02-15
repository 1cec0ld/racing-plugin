package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class ConsumeListener implements Listener{

    public ConsumeListener(Racing racing){
        racing.getServer().getPluginManager().registerEvents(this, racing);
    }
    
    
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        ItemStack items = event.getItem();
        if(!items.hasItemMeta())return;
        if(!(items.getItemMeta() instanceof PotionMeta))return;
        if(!PlayerManager.isRacing(event.getPlayer()))return;
        PlayerManager.setCategory(event.getPlayer(), "potions");
    }
    
}
