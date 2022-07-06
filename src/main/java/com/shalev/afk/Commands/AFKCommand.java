package com.shalev.afk.Commands;

import com.shalev.afk.AFK;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    private final AFK main;

    public AFKCommand(AFK main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("afk")){
            //If the sender isn't a players, stop
            if(!main.isPlayer(sender)) return true;

            //If the player is already registered as AFK, notify them and stop
            Player p = (Player) sender;
            if(main.afkManager.isAFK(p.getUniqueId())) {
                p.sendMessage(ChatColor.RED+"You are already AFK!");
                return true;
            }

            //Register the player as AFK
            main.afkManager.activateAFK(p);
            return true;
        }
        return true;
    }
}
