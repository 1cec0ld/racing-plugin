package com.gmail.ak1cec0ld.plugins.racing.files;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.ak1cec0ld.plugins.racing.Racing;



public class ConfigManager {

    private static CustomYMLStorage yml;
    private static YamlConfiguration config;
    private static Racing plugin;
    
    public ConfigManager(Racing racing) {
        yml = new CustomYMLStorage(racing,"Racing"+File.separator+"config.yml");
        config = yml.getYamlConfiguration();
        plugin = racing;
        yml.save();
    }
    public static Set<String> getRaceNames(){
        return config.getKeys(false);
    }
    public static Location getRaceLeaderboardLoc(String raceName){
        World raceWorld = Bukkit.getWorld(config.getString(raceName+".world"));
        return new Location(raceWorld,
                            config.getInt(raceName+".leaderboard.x"),
                            config.getInt(raceName+".leaderboard.y"),
                            config.getInt(raceName+".leaderboard.z"));
    }
    
    public static int getCheckpointCount(String raceName){
        return config.getInt(raceName+".checkpoints",0);
    }
    
    public static void setWorld(String raceName, World world){
        config.set(raceName+ ".world", world.getName());
        yml.save();
    }
    
    public static boolean setCheckpoint(String raceName, Location loc){
        if(!loc.getWorld().getName().equals(config.getString(raceName+".world"))){
            plugin.warn("Tried to put a checkpoint in another world, that's not supported yet!");
            return false;
        }
        config.set(raceName+".checkpoints", config.getInt(raceName+".checkpoints",0)+1);
        yml.save();
        return true;
    }
    
    public static void removeCheckpoint(String raceName){
        config.set(raceName+".checkpoints", config.getInt(raceName+".checkpoints",1)-1);
        yml.save();
    }
    
    public static boolean setLeaderBoardLoc(String raceName, Location loc){
        if(!loc.getWorld().getName().equals(config.getString(raceName+".world")))return false;
        config.set(raceName+".leaderboard.x", loc.getBlockX());
        config.set(raceName+".leaderboard.y", loc.getBlockY());
        config.set(raceName+".leaderboard.z", loc.getBlockZ());
        yml.save();
        return true;
    }
}


/*
raceName:
  world: string
  leaderboard:
    x: int
    y: int
    z: int
  checkpoints:
    1:
      x: int
      y: int
      z: int
*/