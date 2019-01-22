package com.gmail.ak1cec0ld.plugins.racing;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerManager {
    static Racing plugin;
    
    public PlayerManager(Racing racing){
        plugin = racing;
    }
    
    
    
    public static void startRace(Player player, String raceName){
        endRace(player);
        player.setMetadata("race_racingGame", new FixedMetadataValue(plugin, raceName));
        player.setMetadata("race_raceTime", new FixedMetadataValue(plugin, System.currentTimeMillis()));
        player.setMetadata("race_checkpoint", new FixedMetadataValue(plugin, 0));
        player.setMetadata("race_category", new FixedMetadataValue(plugin, "foot"));
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
    public static void setCategory(Player player, String category){
        if(isRacing(player)){
            player.setMetadata("race_category", new FixedMetadataValue(plugin, category));
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
    
    public static boolean isRacing(Player player) {
        return player.hasMetadata("race_racingGame") && player.hasMetadata("race_checkpoint") && player.hasMetadata("race_raceTime");
    }
}
