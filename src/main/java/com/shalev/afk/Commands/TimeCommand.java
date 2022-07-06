package com.shalev.afk.Commands;

import com.shalev.afk.AFK;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimeCommand implements CommandExecutor {

    private AFK main;

    public TimeCommand(AFK main){
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("afkTime")){
            //The sender queries the current AFK time
            if(args.length<1){
                int time = main.getConfig().getInt("afkTime");
                sender.sendMessage(ChatColor.GOLD+"The current AFK time is "+ChatColor.WHITE+time+ChatColor.GOLD+" seconds");
                return true;
            }
            //If the time given isn't a number, return false
            if(!isNumeric(args[0])) return false;

            //Insert the given time to the config
            main.getConfig().set("afkTime",Integer.valueOf(args[0]));
            main.saveConfig();

            sender.sendMessage(ChatColor.GREEN+"Successfully set afk time");
            return true;
        }

        return true;
    }

    //Checks whether the given string is a number
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
