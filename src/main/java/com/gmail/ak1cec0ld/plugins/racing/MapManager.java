package com.gmail.ak1cec0ld.plugins.racing;

import java.util.Map.Entry;
import java.util.TreeMap;

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
            for(int col = 0; col < 3; col++){
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
                        switch(col){
                            case 0:
                                sign.setLine(2, "Foot Race");
                                break;
                            case 1:
                                sign.setLine(2, "Mounted Race");
                                break;
                            case 2:
                                sign.setLine(2, "Elytra Race");
                                break;
                        }
                        break;
                    case 2:
                        sign.setLine(0, "First Place:");
                        sign.setLine(1, "Player");
                        sign.setLine(2, Integer.MAX_VALUE+"");
                        break;
                    case 1:
                        sign.setLine(0, "Second Place:");
                        sign.setLine(1, "Player");
                        sign.setLine(2, Integer.MAX_VALUE+"");
                        break;
                    case 0:
                        sign.setLine(0, "Third Place:");
                        sign.setLine(1, "Player");
                        sign.setLine(2, Integer.MAX_VALUE+"");
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
        sign.setLine(1, "§5checkpoint");
        sign.setLine(2, String.valueOf(index));
        sign.update();
        return true;
    }

    public static void updateBoard(Location loc, String category, TreeMap<Integer,String> scores) {
        plugin.info("updateboard " + scores.size());
        int col = 0;
        Block b = loc.getBlock();
        Block target;
        Sign sign;
        switch(category){
            case "elytra":
                col = 2;
                break;
            case "mounted":
                col = 1;
                break;
            case "foot":
                col = 0;
                break;
        }
        int row = 2;
        for(Entry<Integer, String> winner : scores.entrySet()){
            target = b.getRelative(-col,row,0);
            sign = (Sign)target.getState();
            sign.setLine(1, winner.getValue());
            sign.setLine(2, String.valueOf(winner.getKey()));
            sign.update();
            row--;
        }
    }
    
    private static String capitalize(String input){
        return input.substring(0, 1).toUpperCase()+input.substring(1, input.length());
    }
}
