package com.gmail.ak1cec0ld.plugins.racing;

import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RaceManager {
    private static Racing plugin;
    
    public RaceManager(Racing racing){
        plugin = racing;
    }
    
    
    public static boolean createRace(String name, Location leaderboard){
        //create config entry
        plugin.getConfigManager().setWorld(name, leaderboard.getWorld());
        plugin.getConfigManager().setLeaderBoardLoc(name, leaderboard);
        //create signs
        if(!MapManager.createLeaderBoard(name, leaderboard)){
            plugin.warn("Tried to create an invalid leaderboard!");
            return false;
        }
        return true;
    }
    
    public static boolean addCheckpoint(String raceName, Location loc){
        int count = plugin.getConfigManager().getCheckpointLocations(raceName).size();
        plugin.getConfigManager().setCheckpoint(raceName, count+1, loc);
        
        if(!MapManager.createCheckpointSign(raceName, loc, count)){
            plugin.warn("Tried to create an invalid checkpoint!");
            return false;
        }
        return true;
    }
    
    public static boolean newScore(String raceName, String category, Player player, int score){
        TreeMap<Integer, String> old = plugin.getResultManager().getResults(raceName, category);
        for(Entry<Integer,String> scorePlayer : old.entrySet()){
            if(score < scorePlayer.getKey()){
                old = plugin.getResultManager().updateResults(raceName, category, player.getName(), score);
                Location loc = plugin.getConfigManager().getRaceLeaderboardLoc(raceName);
                MapManager.updateBoard(loc, category, old);
                return true;
            }
        }
        return false;
    }
}
