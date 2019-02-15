package com.gmail.ak1cec0ld.plugins.racing.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.gmail.ak1cec0ld.plugins.racing.PlayerManager;
import com.gmail.ak1cec0ld.plugins.racing.RaceManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;
import com.gmail.ak1cec0ld.plugins.racing.files.ConfigManager;

public class InteractListener implements Listener{

    private Racing plugin;
    private int MAX_TIME_ALLOWED = 60*60*1000; //60 minutes, 60 seconds, 1000 milliseconds
    
    public InteractListener(Racing racing) {
        racing.getServer().getPluginManager().registerEvents(this, racing);
        plugin = racing;
    }
    
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!player.getGameMode().equals(GameMode.SURVIVAL))return;
        if(player.isFlying() || player.getAllowFlight())return;
        if(!event.getHand().equals(EquipmentSlot.HAND))return;
        if(!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)))return;
        Block hitblock = event.getClickedBlock();
        if(!(hitblock.getType().equals(Material.WALL_SIGN) || hitblock.getType().equals(Material.SIGN)))return;
        Sign sign = (Sign)hitblock.getState();
        
        processRaceInteract(player,sign);
    }
    
    private void processRaceInteract(Player player, Sign sign){
        switch(sign.getLine(0)){
            case "§2[Begin Race]":
                if(ConfigManager.getRaceNames().contains(sign.getLine(1).toLowerCase())){
                    PlayerManager.startRace(player, sign.getLine(1));
                    player.sendMessage("You qualify for the " + capitalize(PlayerManager.getCategory(player)) + " category!");
                    player.sendMessage("§lGO!");
                } else {
                    plugin.warn("Unsupported Racing Sign at " + sign.getLocation().toString());
                }
                break;
            case "§2[End Race]":
                if(ConfigManager.getRaceNames().contains(sign.getLine(1).toLowerCase())){
                    if(!PlayerManager.isRacing(player))return;
                    if(!PlayerManager.getRaceName(player).equalsIgnoreCase(sign.getLine(1)))return;
                    if(PlayerManager.getCheckpoint(player)!=ConfigManager.getCheckpointCount(sign.getLine(1))){
                        player.sendMessage("The Race isn't over! Find checkpoint " + (PlayerManager.getCheckpoint(player)+1) + "!");
                        return;
                    }
                    processRaceEnd(player);
                } else {
                    plugin.warn("Unsupported Racing Sign at " + sign.getLocation().toString());
                }
                break;
        }
        switch(sign.getLine(1)){
            case "§5checkpoint":
                if(ConfigManager.getRaceNames().contains(sign.getLine(0).toLowerCase())){
                    int number = Integer.valueOf(sign.getLine(2));
                    if(PlayerManager.getCheckpoint(player) == number-1){
                        PlayerManager.setCheckpoint(player, number);
                        player.sendMessage("Checkpoint hit! Keep going!");
                    } else {
                        player.sendMessage("Wrong Checkpoint! Find number " + (PlayerManager.getCheckpoint(player)+1) + "!");
                    }
                } else {
                    plugin.warn("Why is a checkpoint showing for an invalid race?");
                }
                break;
        }
    }
    
    private void processRaceEnd(Player player){
        int score = (int)(System.currentTimeMillis()-PlayerManager.getRaceTime(player));
        if(score <= 0 ){
            player.sendMessage("Error, your race start time was erased?");
            PlayerManager.endRace(player);
            return;
        }
        if(score > MAX_TIME_ALLOWED){ //if theyve been going over an hour, DQ)
            player.sendMessage("You spent too long, it's been over an hour since you started the race! Disqualified!");
            PlayerManager.endRace(player);
            return;
        }
        player.sendMessage("Congratulations! Your time is " + ChatColor.BLUE + score + "!");
        player.sendMessage("It looks like you qualified for the " + ChatColor.YELLOW + PlayerManager.getCategory(player) + " category!");
        player.sendMessage("Check the leaderboard to find out if you got a rank!");
        RaceManager.newScore(PlayerManager.getRaceName(player), PlayerManager.getCategory(player), player, score);
        PlayerManager.endRace(player);
    }
    private static String capitalize(String input){
        return input.substring(0, 1).toUpperCase()+input.substring(1, input.length());
    }
}
