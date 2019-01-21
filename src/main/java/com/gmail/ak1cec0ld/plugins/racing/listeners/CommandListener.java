package com.gmail.ak1cec0ld.plugins.racing.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.racing.RaceManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;

import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.Argument;
import io.github.jorelali.commandapi.api.arguments.DynamicSuggestedStringArgument;
import io.github.jorelali.commandapi.api.arguments.IntegerArgument;
import io.github.jorelali.commandapi.api.arguments.LiteralArgument;
import io.github.jorelali.commandapi.api.arguments.LocationArgument;
import io.github.jorelali.commandapi.api.arguments.PlayerArgument;
import io.github.jorelali.commandapi.api.arguments.StringArgument;
import io.github.jorelali.commandapi.api.arguments.LocationArgument.LocationType;

public class CommandListener {
    private Racing plugin;
    
    private String COMMAND_ALIAS = "race";
    private String[] ALIASES = {"racing"};
    private LinkedHashMap<String, Argument> arguments;
    
    public CommandListener(Racing racing){
        plugin = racing;
        initializeArguments();
    }

    public void initializeArguments(){
        List<String> arg1s = new ArrayList<String>(plugin.getResultManager().getRaceNames());
        DynamicSuggestedStringArgument dynSSArg = new DynamicSuggestedStringArgument(() -> {
            return arg1s.toArray(new String[arg1s.size()]);
        });
        Set<String> arg2s = new HashSet<String>(Arrays.asList("elytra","horse","foot"));
        for(String category : arg2s){
            arguments = new LinkedHashMap<String, Argument>();
            arguments.put("action", new LiteralArgument("addwin"));
            arguments.put("suggested", dynSSArg);
            arguments.put("category", new LiteralArgument(category));
            arguments.put("winner", new PlayerArgument());
            arguments.put("time", new IntegerArgument(0));
            registerAddwin(category);
        }
        for(String category : arg2s){
            arguments = new LinkedHashMap<String, Argument>();
            arguments.put("action", new LiteralArgument("results"));
            arguments.put("suggested", dynSSArg);
            arguments.put("category", new LiteralArgument(category));
            registerResults(category);
        }
        arguments = new LinkedHashMap<String, Argument>();
        arguments.put("action", new LiteralArgument("reload"));
        registerReload();
        arguments = new LinkedHashMap<String, Argument>();
        arguments.put("action", new LiteralArgument("create"));
        arguments.put("Name", new StringArgument());
        arguments.put("LeaderBoardLoc", new LocationArgument(LocationType.BLOCK_POSITION));
        registerCreate();
        arguments = new LinkedHashMap<String, Argument>();
        arguments.put("action", new LiteralArgument("checkpoint"));
        arguments.put("Name", new StringArgument());
        arguments.put("SignLocation", new LocationArgument());
        registerCheckpoint();
    }
    
    private void registerAddwin(String category){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.OP, ALIASES, arguments, (sender,args)->{
            plugin.getResultManager().updateResults(args[0].toString(), category, ((Player)args[1]).getName(), (int)args[2]);
            sender.sendMessage("Added player " + ((Player)args[1]).getName() + " to race named " + args[0].toString() + " for category " + category + " with time " + (int)args[2]);
        });
    }
    private void registerResults(String category){
        CommandAPI.getInstance().register(COMMAND_ALIAS, ALIASES, arguments, (sender,args)->{
            TreeMap<Integer, String> results = plugin.getResultManager().getResults(args[0].toString(), category);
            sender.sendMessage("Results:");
            for(Entry<Integer, String> each : results.entrySet()){
                sender.sendMessage(each.getValue() + " : " + each.getKey());
            }
        });
    }
    private void registerReload(){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.OP, ALIASES, arguments, (sender,args)->{
            sender.sendMessage("Reloaded Racing plugin, config, and results files! Ongoing races will continue without interruption.");
            plugin.reloadPlugin();
        });
    }
    private void registerCreate(){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.OP, ALIASES, arguments, (sender,args)->{
            sender.sendMessage("Created a new race! Name: " + args[0].toString());
            Location board = (Location)args[1];
            RaceManager.createRace(args[0].toString(), board);
        });
    }
    private void registerCheckpoint(){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.OP, ALIASES, arguments, (sender,args)->{
            int count = plugin.getConfigManager().getCheckpointLocations(args[0].toString()).size();
            sender.sendMessage("Checkpoint " + (count+1) + " set");
            plugin.getConfigManager().setCheckpoint(args[0].toString(), count+1, (Location)args[1]);
        });
    }
}
