package com.gmail.ak1cec0ld.plugins.racing;

import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.racing.files.ConfigManager;
import com.gmail.ak1cec0ld.plugins.racing.files.ResultManager;

public class RaceManager {
    private static Racing plugin;
    
    public RaceManager(Racing racing){
        plugin = racing;
    }
    
    
    public static boolean createRace(String name, Location leaderboard){
        //create config entry
        ConfigManager.setWorld(name, leaderboard.getWorld());
        ConfigManager.setLeaderBoardLoc(name, leaderboard);
        //create signs
        if(!MapManager.createLeaderBoard(name, leaderboard)){
            plugin.warn("Tried to create an invalid leaderboard!");
            return false;
        }
        return true;
    }
    
    public static boolean addCheckpoint(String raceName, Location loc){
        int count = ConfigManager.getCheckpointLocations(raceName).size();
        if(!ConfigManager.setCheckpoint(raceName, count+1, loc)){
            plugin.warn("Config file can't create checkpoint!");
            return false;
        }
        
        if(!MapManager.createCheckpointSign(raceName, loc, count)){
            plugin.warn("Tried to create a checkpoint at an invalid spot!");
            return false;
        }
        return true;
    }
    
    public static void newScore(String raceName, String category, Player player, int score){
        TreeMap<Integer, String> update;
        update = ResultManager.updateResults(raceName, category, player.getName(), score);
        Location loc = ConfigManager.getRaceLeaderboardLoc(raceName);
        MapManager.updateBoard(loc, category, update);
    }
}
