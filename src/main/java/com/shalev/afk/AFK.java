package com.shalev.afk;

import com.shalev.afk.Commands.AFKCommand;
import com.shalev.afk.Commands.MessageManager;
import com.shalev.afk.Commands.TimeCommand;
import com.shalev.afk.Listeners.MovementListener;
import com.shalev.afk.Listeners.PlayerConnectivityListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class AFK extends JavaPlugin {

    //Keeps track of who is afk and who isn't
    public HashMap<UUID,Boolean> AFKTracker;
    //Keeps record of the last time each player moved
    public HashMap<UUID,Long> lastTimeMoved;
    public TitleManager titleManager = new TitleManager(this);
    public MessageManager messageManager = new MessageManager(this);
    public AFKManager afkManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        AFKTracker = new HashMap<>();
        lastTimeMoved = new HashMap<>();
        afkManager = new AFKManager(this);

        TabCompletion tabCompleter = new TabCompletion();

        //Register all the commands
        getCommand("afkTitle").setExecutor(messageManager);
        getCommand("afk").setExecutor(new AFKCommand(this));
        getCommand("afkTime").setExecutor(new TimeCommand(this));

        //Set the tab completion of the commands
        List<String> commands = Arrays.asList("afkTitle","afk","afkTime");
        for(String command : commands)
            getCommand(command).setTabCompleter(tabCompleter);


        saveDefaultConfig();
        setConfigComments();

        titleManager.updateTitles();

        //Register the listeners
        getServer().getPluginManager().registerEvents(new MovementListener(this),this);
        getServer().getPluginManager().registerEvents(new PlayerConnectivityListener(this),this);

        //Iterates through online players and registers them to the AFKTracker
        registerOnlinePlayer();

        MovementCheck movementCheck = new MovementCheck(this);
        //Start the task responsible for checking if players stop moving
        movementCheck.startCheck();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    //Sets the comments in the config
    private void setConfigComments(){
        getConfig().options().setHeader(Arrays.asList("--------------------------------------------------------------","","","Welcome to AFK!","","","--------------------------------------------------------------"));
        saveConfig();
    }


    //Register the last time each player online moved and disable current AFK players
    private void registerOnlinePlayer(){
        for(Player p : Bukkit.getOnlinePlayers()) {
            afkManager.disableAFK(p);
            lastTimeMoved.put(p.getUniqueId(),System.currentTimeMillis());
        }

    }


    //Checks if a sender is a player, if not sends a corresponding message
    public boolean isPlayer(CommandSender sender){
        if(sender instanceof Player) return true;
        sender.sendMessage(ChatColor.DARK_RED+"This command can only be performed by a player!");
        return false;
    }
}
