package com.gmail.ak1cec0ld.plugins.racing;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
        new InteractListener(this);
        new TeleportListener(this);
        new CommandListener(this);
    }



    public void disqualifyPlayer(Player player) {
        
    }
    
    public ConfigManager getConfigManager(){
        return this.configManager;
    }
    
    public ResultManager getResultManager(){
        return this.resultManager;
    }



    public boolean isRacing(Player entity) {
        // TODO Auto-generated method stub
        return false;
    }
}
