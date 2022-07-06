package com.shalev.afk.Commands;

import com.shalev.afk.AFK;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageManager implements CommandExecutor {

    //This pattern is used to check for hex colors
    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    private final AFK main;

    public MessageManager(AFK main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("afkTitle")){

            if(args.length<2) return false;

            if(!(args[0].equalsIgnoreCase("title") || args[0].equalsIgnoreCase("subtitle"))) return false;

            //Get the given title by the sender
            String title = arrToStr(args);

            //Save the title to the config
            insertTitle(title,args[0]);

            //Update to the new title
            main.titleManager.updateTitles();

            sender.sendMessage(ChatColor.GREEN+"Successfully set "+args[0].toLowerCase());
            return true;
        }
        return true;
    }

    //Converts an array to a string
    private String arrToStr(String[] args){
        StringBuilder title = new StringBuilder();
        for(int i=1;i<args.length-1;i++)
            title.append(args[i]).append(" ");
        title.append(args[args.length-1]);
        return title.toString();
    }

    //Gets a string and converts the hex colors to Minecraft chat colors
    public String translateTitle(String title){

        Matcher match = pattern.matcher(title);
        while(match.find()){
            String color = title.substring(match.start(),match.end());
            title = title.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(title);
        }
        return title;
    }

    //Inserts the given title/subtitle to the config
    private void insertTitle(String str,String type) {
        if (type.equalsIgnoreCase("title"))
            main.getConfig().set("title", str);
        else if (type.equalsIgnoreCase("subtitle"))
            main.getConfig().set("subtitle", str);
        main.saveConfig();
    }
}
