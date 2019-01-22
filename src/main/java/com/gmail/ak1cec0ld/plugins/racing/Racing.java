package com.gmail.ak1cec0ld.plugins.racing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.ak1cec0ld.plugins.racing.files.ConfigManager;
import com.gmail.ak1cec0ld.plugins.racing.files.ResultManager;
import com.gmail.ak1cec0ld.plugins.racing.listeners.CommandListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.InteractListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.TeleportListener;

public class Racing extends JavaPlugin{

    private ResultManager resultManager;
    private ConfigManager configManager;
    
    public void onEnable(){
        reloadPlugin();
    }
    
    
    
    public void reloadPlugin(){
        resultManager = new ResultManager(this);
        configManager = new ConfigManager(this);
        new RaceManager(this);
        new MapManager(this);
        new PlayerManager(this);
        new InteractListener(this);
        new TeleportListener(this);
        new CommandListener(this);
    }



    public void disqualifyPlayer(Player player) {
        player.removeMetadata("racingGame", this);
    }
    public boolean isRacing(Player player) {
        return player.hasMetadata("racingGame");
    }
    
    
    
    public ConfigManager getConfigManager(){
        return this.configManager;
    }
    public ResultManager getResultManager(){
        return this.resultManager;
    }

    public void info(String msg){
        Bukkit.getLogger().info("[Racing] "+msg);
    }
    public void warn(String msg){
        Bukkit.getLogger().warning("[Racing] "+msg);
    }
}
