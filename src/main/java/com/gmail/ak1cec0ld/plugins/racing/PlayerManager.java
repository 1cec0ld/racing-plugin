package com.gmail.ak1cec0ld.plugins.racing;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerManager {
    private static Racing plugin;
    
    public PlayerManager(Racing racing){
        plugin = racing;
    }
    
    
    
    public static void startRace(Player player, String raceName){
        endRace(player);
        player.setMetadata("race_racingGame", new FixedMetadataValue(plugin, raceName));
        player.setMetadata("race_raceTime", new FixedMetadataValue(plugin, System.currentTimeMillis()));
        player.setMetadata("race_checkpoint", new FixedMetadataValue(plugin, 0));
        player.setMetadata("race_category", new FixedMetadataValue(plugin, getPlayerStartCategory(player)));
        plugin.debug("Category: " + getCategory(player));
    }
    public static void endRace(Player player) {
        player.removeMetadata("race_racingGame", plugin);
        player.removeMetadata("race_raceTime", plugin);
        player.removeMetadata("race_checkpoint", plugin);
        player.removeMetadata("race_category", plugin);
    }
    public static void setCheckpoint(Player player, int point){
        if(isRacing(player)){
            player.setMetadata("race_checkpoint", new FixedMetadataValue(plugin, point));
        }
    }
    
    public static String getRaceName(Player player){
        return (isRacing(player) ? player.getMetadata("race_racingGame").get(0).asString() : "null");
    }
    public static int getRaceTime(Player player){
        return (isRacing(player) ? (int)player.getMetadata("race_raceTime").get(0).asLong() : Integer.MAX_VALUE);
    }
    public static int getCheckpoint(Player player){
        return (isRacing(player) ? player.getMetadata("race_checkpoint").get(0).asInt() : -1);
    }
    public static String getCategory(Player player){
        return (isRacing(player) ? player.getMetadata("race_category").get(0).asString() : "null");
    }
    public static void setCategory(Player player, String category){
        int oldTier = plugin.getTierOfCategory(getCategory(player));
        int newTier = plugin.getTierOfCategory(category);
        if(newTier <= oldTier)return;
        player.setMetadata("race_category", new FixedMetadataValue(plugin, category));
        player.sendMessage("You've been upgraded to the " + capitalize(category) + " Category!");
        plugin.debug("Set " + player.getDisplayName() + " category to " + category);
    }
    
    
    
    public static boolean isRacing(Player player) {
        return player.hasMetadata("race_racingGame") && 
               player.hasMetadata("race_checkpoint") && 
               player.hasMetadata("race_raceTime") &&
               player.hasMetadata("race_category");
    }
    
    public static String getPlayerStartCategory(Player player){
        if (player.getInventory().getChestplate()!=null && player.getInventory().getChestplate().equals(Material.ELYTRA))return "elytra";
        Entity vehicle = player.getVehicle();
        if(vehicle != null){
            if (vehicle instanceof Pig) return "pig";
            if (vehicle instanceof Boat) return "boat";
            return "mounted";
        }
        if (player.getActivePotionEffects().size()>0) return "potions";
        return "foot";
    }
    private static String capitalize(String input){
        return input.substring(0, 1).toUpperCase()+input.substring(1, input.length());
    }
}
