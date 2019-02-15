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
    
    public static boolean getDebugging(){
        return config.getBoolean("debugging",false);
    }
    
    public static Set<String> getRaceNames(){
        return config.getKeys(false);
    }
    public static Location getRaceLeaderboardLoc(String raceName){
        String lRaceName = raceName.toLowerCase();
        World raceWorld = Bukkit.getWorld(config.getString(lRaceName+".world"));
        return new Location(raceWorld,
                            config.getInt(lRaceName+".leaderboard.x"),
                            config.getInt(lRaceName+".leaderboard.y"),
                            config.getInt(lRaceName+".leaderboard.z"));
    }
    
    public static int getCheckpointCount(String raceName){
        String lRaceName = raceName.toLowerCase();
        return config.getInt(lRaceName+".checkpoints",0);
    }
    
    public static void setWorld(String raceName, World world){
        String lRaceName = raceName.toLowerCase();
        config.set(lRaceName+ ".world", world.getName());
        yml.save();
    }
    
    public static boolean setCheckpoint(String raceName, Location loc){
        String lRaceName = raceName.toLowerCase();
        if(!loc.getWorld().getName().equals(config.getString(lRaceName+".world"))){
            plugin.warn("Tried to put a checkpoint in another world, that's not supported yet!");
            return false;
        }
        config.set(lRaceName+".checkpoints", config.getInt(lRaceName+".checkpoints",0)+1);
        yml.save();
        return true;
    }
    
    public static void removeCheckpoint(String raceName){
        String lRaceName = raceName.toLowerCase();
        config.set(lRaceName+".checkpoints", config.getInt(lRaceName+".checkpoints",1)-1);
        yml.save();
    }
    
    public static boolean setLeaderBoardLoc(String raceName, Location loc){
        String lRaceName = raceName.toLowerCase();
        if(!loc.getWorld().getName().equals(config.getString(lRaceName+".world")))return false;
        config.set(lRaceName+".leaderboard.x", loc.getBlockX());
        config.set(lRaceName+".leaderboard.y", loc.getBlockY());
        config.set(lRaceName+".leaderboard.z", loc.getBlockZ());
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