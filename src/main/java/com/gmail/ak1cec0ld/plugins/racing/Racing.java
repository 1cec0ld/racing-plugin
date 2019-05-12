package com.gmail.ak1cec0ld.plugins.racing;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.ak1cec0ld.plugins.racing.files.ConfigManager;
import com.gmail.ak1cec0ld.plugins.racing.files.ResultManager;
import com.gmail.ak1cec0ld.plugins.racing.listeners.CommandListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.ConsumeListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.GlideListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.InteractListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.MountListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.PotionSplashListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.PreCommandListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.RipTideListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.TeleportListener;
import com.gmail.ak1cec0ld.plugins.racing.listeners.ToggleFlightListener;

public class Racing extends JavaPlugin{
    private static boolean DEBUGGING;
    private static ArrayList<String> CATEGORIES = new ArrayList<String>(Arrays.asList("foot","potions","pig","boat","mounted","elytra","riptide","teleport"));
    public void onEnable(){
        reloadPlugin();
        new CommandListener(this);
    }
    
    //todo:
    //  Allow for multiple directions for the Leaderboard to face based on player facing direction
    
    public void reloadPlugin(){
        new ResultManager(this);
        new ConfigManager(this);
        new RaceManager(this);
        new MapManager(this);
        new PlayerManager(this);

        new InteractListener(this);
        
        new ConsumeListener(this);
        new PotionSplashListener(this);
        new MountListener(this);
        new GlideListener(this);
        new RipTideListener(this);
        new TeleportListener(this);
        new PreCommandListener(this);
        new ToggleFlightListener(this);
        
        DEBUGGING = ConfigManager.getDebugging();
    }
    
    public ArrayList<String> getCategories(){
        return CATEGORIES;
    }
    
    public int getTierOfCategory(String category){
        return CATEGORIES.indexOf(category);
    }

    public void info(String msg){
        Bukkit.getLogger().info("[Racing] "+msg);
    }
    public void warn(String msg){
        Bukkit.getLogger().warning("[Racing : @1cec0ld] "+msg);
    }
    public void debug(String msg){
        if(DEBUGGING)Bukkit.getLogger().info("[Racing : Debug] "+msg);
    }
}
