package com.gmail.ak1cec0ld.plugins.racing.files;

import java.io.File;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.ak1cec0ld.plugins.racing.Racing;



public class ResultManager {
    
    private static CustomYMLStorage yml;
    private static YamlConfiguration results;
    
    private final static int MAX_LEADERS_PER_GAMEMODE = 3;
    
    public ResultManager(Racing plugin){
        yml = new CustomYMLStorage(plugin,"Racing"+File.separator+"results.yml");
        results = yml.getYamlConfiguration();
        yml.save();
    }
    
    public static TreeMap<Integer,String> getResults(String raceName, String raceType){
        return sortedMapFromConfigSection(results.getConfigurationSection(raceName+"."+raceType));
    }
    
    public static TreeMap<Integer,String> updateResults(String raceName, String raceType, String playerName, int time){
        //Bukkit.getLogger().info(time + " time sent");
        ConfigurationSection winners = results.getConfigurationSection(raceName+"."+raceType);
        TreeMap<Integer,String> storage = sortedMapFromConfigSection(winners);
        storage = insertPlayerOverwriteHigher(time, playerName, storage);
        ConfigurationSection newWinners = results.createSection(raceName+"."+raceType);
        
        int maxLeaders = MAX_LEADERS_PER_GAMEMODE;
        for(Entry<Integer, String> each : storage.entrySet()){
            newWinners.set(each.getValue(), each.getKey());
            yml.save();
            maxLeaders--;
            if(maxLeaders==0){
                return subset(storage, 0, MAX_LEADERS_PER_GAMEMODE);
            }
        }
        return storage;
    }
    
    private static TreeMap<Integer,String> sortedMapFromConfigSection(ConfigurationSection playersAndScores){
        TreeMap<Integer,String> scoresAndPlayers = new TreeMap<Integer,String>();
        if(playersAndScores==null)return scoresAndPlayers;
        for(String playername : playersAndScores.getKeys(false)){
            scoresAndPlayers.put(playersAndScores.getInt(playername), playername);
        }
        return scoresAndPlayers;
    }
    private static TreeMap<Integer,String> insertPlayerOverwriteHigher(Integer time, String playername, TreeMap<Integer,String> startingMap){
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
    private static <K,V> TreeMap<K,V> subset(TreeMap<K,V> tree, int startIndex, int endIndex){
        if(startIndex>tree.size())return null;
        if(endIndex>tree.size())return null;
        TreeMap<K,V> map = new TreeMap<K,V>();
        int index = 0;
        for(Entry<K, V> entry : tree.entrySet()){
            if(index>=startIndex && endIndex>index){
                map.put(entry.getKey(), entry.getValue());
                if(index==endIndex)return map;
            }
        }
        return map;
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