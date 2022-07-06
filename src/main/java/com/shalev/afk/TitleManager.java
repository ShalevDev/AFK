package com.shalev.afk;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;

public class TitleManager implements Listener {
    //Keeps track of which player triggered each task
    private final HashMap<UUID,Integer> trackTasks;

    private final AFK main;
    private String title;
    private String subtitle;

    public TitleManager(AFK main){
        this.main = main;
        trackTasks = new HashMap<>();
    }

    //Fetches the titles from the config to update them
    public void updateTitles(){
        title = main.messageManager.translateTitle(main.getConfig().getString("title"));
        subtitle = main.messageManager.translateTitle(main.getConfig().getString("subtitle"));
    }

    //Schedules a repeating task to the player that displays a title
    public void sendTitle(Player p){
        int ticks = 72000;
        p.sendTitle(title,subtitle,20,ticks,0);
        final Runnable runnable = () -> p.sendTitle(title,subtitle,0,ticks+20,0);

        int taskID = main.getServer().getScheduler().scheduleSyncRepeatingTask(main,runnable,ticks,ticks);
        trackTasks.put(p.getUniqueId(),taskID);
    }

    //Resets the title displayed to the player and stops their title task
    public void stopTitle(Player p){
        p.resetTitle();
        if(!trackTasks.containsKey(p.getUniqueId())) return;
        int taskID = trackTasks.get(p.getUniqueId());
        main.getServer().getScheduler().cancelTask(taskID);
        trackTasks.remove(p.getUniqueId());
    }



}
