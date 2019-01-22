package com.gmail.ak1cec0ld.plugins.racing;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.ak1cec0ld.plugins.racing.files.ConfigManager;
import com.gmail.ak1cec0ld.plugins.racing.files.ResultManager;
import com.gmail.ak1cec0ld.plugins.racing.listeners.CommandListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.InteractListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.TeleportListener;

public class Racing extends JavaPlugin{

    
    public void onEnable(){
        reloadPlugin();
        new CommandListener(this);
    }
    
    
    
    public void reloadPlugin(){
        new ResultManager(this);
        new ConfigManager(this);
        new RaceManager(this);
        new MapManager(this);
        new PlayerManager(this);
        new InteractListener(this);
        new TeleportListener(this);
    }

    public void info(String msg){
        Bukkit.getLogger().info("[Racing] "+msg);
    }
    public void warn(String msg){
        Bukkit.getLogger().warning("[Racing] "+msg);
    }
}
