package com.gmail.ak1cec0ld.plugins.racing;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

public class MapManager {
    static Racing plugin;

    
    
    public MapManager(Racing racing){
        plugin = racing;
    }

    public static boolean createLeaderBoard(String raceName, Location board) {
        Block current;
        Sign sign;
        board.getBlock().setType(Material.AIR);
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < plugin.getCategories().size(); col++){
                current = board.getBlock().getRelative(-col, row, 0); //facing North is default; subtract X to go left->right
                
                if(!current.getType().equals(Material.AIR)){
                    plugin.warn("Tried to use an area for the leaderboard that wasn't all air!");
                    return false;
                }
                current.setType(Material.WALL_SIGN);
                sign = (Sign)(current.getState());
                switch(row){
                    case 3:
                        sign.setLine(0, capitalize(raceName));
                        sign.setLine(2, capitalize(plugin.getCategories().get(col))+" Race");
                        break;
                    case 2:
                        sign.setLine(0, "First Place:");
                        sign.setLine(1, "Player");
                        sign.setLine(2, displayFromMillis(600000000));
                        break;
                    case 1:
                        sign.setLine(0, "Second Place:");
                        sign.setLine(1, "Player");
                        sign.setLine(2, displayFromMillis(600000000));
                        break;
                    case 0:
                        sign.setLine(0, "Third Place:");
                        sign.setLine(1, "Player");
                        sign.setLine(2, displayFromMillis(600000000));
                        break;
                }
                sign.update();
            }
        }
        return true;
    }
    
    public static boolean createCheckpointSign(String raceName, Location loc, int index){
        Block current = loc.getBlock();
        if(!(current.getType().equals(Material.SIGN) || current.getType().equals(Material.WALL_SIGN))){
            plugin.warn("Tried to use a non-sign block for a checkpoint!");
            return false;
        }
        Sign sign = (Sign)current.getState();
        if(sign.getLine(0).equalsIgnoreCase(raceName) || sign.getLine(1).equals("checkpoint")){
            plugin.warn("Tried to overwrite an existing race text! Clear/Break it first!");
            return false;
        }
        sign.setLine(0, capitalize(raceName));
        sign.setLine(1, ChatColor.COLOR_CHAR+"5checkpoint");
        sign.setLine(2, String.valueOf(index));
        sign.update();
        return true;
    }

    public static void updateBoard(Location loc, String category, TreeMap<Integer,String> scores) {
        int col = 0;
        Block b = loc.getBlock();
        Block target;
        Sign sign;
        col = plugin.getCategories().indexOf(category);
        int row = 2;
        plugin.debug("Size of scores in MapManager.updateBoard: " + scores.size());
        for(Entry<Integer, String> winner : scores.entrySet()){
            target = b.getRelative(-col,row,0);
            sign = (Sign)target.getState();
            sign.setLine(1, winner.getValue());
            sign.setLine(2, displayFromMillis(winner.getKey()));
            sign.update();
            row--;
        }
    }
    
    private static String capitalize(String input){
        return input.substring(0, 1).toUpperCase()+input.substring(1);
    }

    private static String displayFromMillis(long duration){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));
        long millis  = TimeUnit.MILLISECONDS.toMillis(duration)-TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration));

        return String.format("%dm %ds %dms", minutes, seconds, millis);
    }
}
