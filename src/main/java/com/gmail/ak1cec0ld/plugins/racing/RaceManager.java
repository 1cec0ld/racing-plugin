package com.gmail.ak1cec0ld.plugins.racing;

import org.bukkit.Location;

public class RaceManager {
    private static Racing plugin;
    
    public RaceManager(Racing racing){
        plugin = racing;
    }
    
    
    public static void createRace(String name, Location leaderboard){
        //create config entry
        plugin.getConfigManager().setWorld(name, leaderboard.getWorld());
        plugin.getConfigManager().setLeaderBoardLoc(name, leaderboard);
        //create signs
        
    }
    
    public static void setCheckpoint(String raceName, int number){
        
    }
}
