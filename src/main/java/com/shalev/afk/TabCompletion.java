package com.shalev.afk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        //Autocomplete for the 'afkTitle' command
        if(label.equalsIgnoreCase("afkTitle")){
            if(args.length == 1 && sender.hasPermission("afk.change")) return Arrays.asList("title","subtitle");
            else return Collections.emptyList();
        }
        //Autocomplete for the 'afk' command
        if(label.equalsIgnoreCase("afk"))
            return Collections.emptyList();
        //Autocomplete for the 'afkTime' command
        if(label.equalsIgnoreCase("afkTime"))
            return Collections.emptyList();

        return null;
    }
}
