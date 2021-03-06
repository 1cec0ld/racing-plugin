package com.gmail.ak1cec0ld.plugins.racing.listeners;

import com.gmail.ak1cec0ld.plugins.racing.RaceManager;
import com.gmail.ak1cec0ld.plugins.racing.Racing;
import com.gmail.ak1cec0ld.plugins.racing.files.ConfigManager;
import com.gmail.ak1cec0ld.plugins.racing.files.ResultManager;
import io.github.jorelali.commandapi.api.CommandAPI;
import io.github.jorelali.commandapi.api.CommandPermission;
import io.github.jorelali.commandapi.api.arguments.*;
import io.github.jorelali.commandapi.api.arguments.LocationArgument.LocationType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.Map.Entry;

public class CommandListener {
    private Racing plugin;
    
    private String COMMAND_ALIAS = "race";
    private String[] ALIASES = {"racing"};
    private LinkedHashMap<String, Argument> arguments;
    private List<String> arg1s;

    public CommandListener(Racing racing){
        plugin = racing;
        arg1s = new ArrayList<>(ConfigManager.getRaceNames());
        initializeArguments();
    }

    private void initializeArguments(){
        DynamicSuggestedStringArgument allRaceNames = new DynamicSuggestedStringArgument(() -> arg1s.toArray(new String[0]));
        Set<String> arg2s = new HashSet<>(Arrays.asList("elytra","horse","foot"));
        for(String category : arg2s){
            arguments = new LinkedHashMap<>();
            arguments.put("action", new LiteralArgument("addwin"));
            arguments.put("Name", allRaceNames);
            arguments.put("category", new LiteralArgument(category));
            arguments.put("winner", new PlayerArgument());
            arguments.put("time", new IntegerArgument(0));
            registerAddwin(category);
        }
        for(String category : arg2s){
            arguments = new LinkedHashMap<>();
            arguments.put("action", new LiteralArgument("results"));
            arguments.put("Name", allRaceNames);
            arguments.put("category", new LiteralArgument(category));
            registerResults(category);
        }
        arguments = new LinkedHashMap<>();
        arguments.put("action", new LiteralArgument("reload"));
        registerReload();
        arguments = new LinkedHashMap<>();
        arguments.put("action", new LiteralArgument("create"));
        arguments.put("Name", new StringArgument());
        arguments.put("LeaderBoardLoc", new LocationArgument(LocationType.BLOCK_POSITION));
        registerCreate();
        arguments = new LinkedHashMap<>();
        arguments.put("action", new LiteralArgument("checkpoint"));
        arguments.put("Name", allRaceNames);
        arguments.put("SignLocation", new LocationArgument(LocationType.BLOCK_POSITION));
        registerCheckpoint();
    }
    
    private void registerAddwin(String category){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.OP, ALIASES, arguments, (sender,args)->{
            sender.sendMessage("Added player " + ((Player)args[1]).getName() + " to race named " + args[0].toString() + " for category " + category + " with time " + args[2]);
            RaceManager.newScore(args[0].toString(), category, ((Player)args[1]), (int)args[2]);
        });
    }
    private void registerResults(String category){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.NONE, ALIASES, arguments, (sender,args)->{
            TreeMap<Integer, String> results = ResultManager.getResults(args[0].toString(), category);
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
            if(!RaceManager.createRace(args[0].toString(), (Location)args[1])){
                sender.sendMessage("Error! See console!");
                return;
            }
            sender.sendMessage("Created a new race! Name: " + args[0].toString());
            arg1s.add(args[0].toString());

        });
    }
    private void registerCheckpoint(){
        CommandAPI.getInstance().register(COMMAND_ALIAS, CommandPermission.OP, ALIASES, arguments, (sender,args)->{
            if(!RaceManager.addCheckpoint(args[0].toString(), (Location)args[1])){
                sender.sendMessage("Error! See console!");
                return;
            }
            sender.sendMessage("Checkpoint set!");
        });
    }
}
