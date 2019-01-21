package com.gmail.ak1cec0ld.plugins.racing;

import java.io.File;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.ak1cec0ld.plugins.racing.CustomYMLStorage;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

public class ResultManager {
    private CustomYMLStorage yml;
    private YamlConfiguration results;
    
    private int MAX_LEADERS_PER_GAMEMODE = 3;
    
    public ResultManager(Racing plugin){
        yml = new CustomYMLStorage(plugin,"Racing"+File.separator+"results.yml");
        results = yml.getYamlConfiguration();
        yml.save();
    }
    
    public Set<String> getRaceNames(){
        return results.getKeys(false);
    }
    
    
    public TreeMap<Integer,String> getResults(String raceName, String raceType){
        return sortedMapFromConfigSection(results.getConfigurationSection(raceName+"."+raceType));
    }
    
    public void updateResults(String raceName, String raceType, String playerName, int time){
        ConfigurationSection winners = results.getConfigurationSection(raceName+"."+raceType);
        TreeMap<Integer,String> storage = sortedMapFromConfigSection(winners);

        storage = insertPlayerOverwriteHigher(time, playerName, storage);
        
        ConfigurationSection newWinners = results.createSection(raceName+"."+raceType);
        
        int maxLeaders = MAX_LEADERS_PER_GAMEMODE;
        for(Entry<Integer, String> each : storage.entrySet()){
            newWinners.set(each.getValue(), each.getKey());
            yml.save();
            maxLeaders--;
            if(maxLeaders==0)return;
        }
    }
    
    private TreeMap<Integer,String> sortedMapFromConfigSection(ConfigurationSection playersAndScores){
        TreeMap<Integer,String> scoresAndPlayers = new TreeMap<Integer,String>();
        if(playersAndScores==null)return scoresAndPlayers;
        for(String playername : playersAndScores.getKeys(false)){
            scoresAndPlayers.put(playersAndScores.getInt(playername), playername);
        }
        return scoresAndPlayers;
    }
    private TreeMap<Integer,String> insertPlayerOverwriteHigher(Integer time, String playername, TreeMap<Integer,String> startingMap){
        if(startingMap.containsValue(playername)){
            for(Entry<Integer, String> each : startingMap.entrySet()){
                if(each.getValue().equals(playername)){
                    startingMap.remove(Math.max(each.getKey(), time), playername);
                    startingMap.put(Math.min(each.getKey(), time), playername);
                    return startingMap;
                }
            }
        }
        startingMap.put(time, playername);
        return startingMap;
    }
}
/*
raceName:
  elytra:
    playername: int
    playername: int
    playername: int
  horse:
    playername: int
    playername: int
    playername: int
  foot:
    playername: int
    playername: int
    playername: int
*/