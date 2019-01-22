package com.gmail.ak1cec0ld.plugins.racing.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    
    public static List<Location> getCheckpointLocations(String raceName){
        List<Location> points = new ArrayList<Location>();
        World raceWorld = Bukkit.getWorld(config.getString(raceName+".world"));
        if(!config.contains(raceName+".checkpoints"))return points;
        for(String each : config.getConfigurationSection(raceName+".checkpoints").getKeys(false)){
            points.add(new Location(raceWorld,
                                    config.getInt(raceName+".checkpoints."+each+".x"),
                                    config.getInt(raceName+".checkpoints."+each+".y"),
                                    config.getInt(raceName+".checkpoints."+each+".z"))
            );
        }
        return points;
    }
    
    public static void setWorld(String raceName, World world){
        config.set(raceName+ ".world", world.getName());
        yml.save();
    }
    
    public static boolean setCheckpoint(String raceName, int number, Location loc){
        if(!loc.getWorld().getName().equals(config.getString(raceName+".world"))){
            plugin.warn("Tried to put a checkpoint in another world, that's not supported yet!");
            return false;
        }
        config.set(raceName+".checkpoints."+number+".x", loc.getBlockX());
        config.set(raceName+".checkpoints."+number+".y", loc.getBlockY());
        config.set(raceName+".checkpoints."+number+".z", loc.getBlockZ());
        yml.save();
        return true;
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