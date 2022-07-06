package com.shalev.afk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MovementCheck {

    private AFK main;

    public MovementCheck(AFK main){
        this.main = main;
    }

    //Schedules the checkPlayers task as a repeating task
    public void startCheck(){
        main.getServer().getScheduler().scheduleSyncRepeatingTask(main,checkPlayers,0,20);
    }

    //Iterates through online players and check if they can be declared as AFK by checking if they move or not
    private final Runnable checkPlayers = () -> {
        for(Player p: Bukkit.getOnlinePlayers()){
            int afkTime = main.getConfig().getInt("afkTime");
            Long lastTimeSinceMoved = main.lastTimeMoved.get(p.getUniqueId());
            //If enough time has passed and the player isn't declared as afk declare the player as afk
            if(System.currentTimeMillis()-lastTimeSinceMoved>=afkTime* 1000L && !main.afkManager.isAFK(p.getUniqueId()))
                main.afkManager.activateAFK(p);
        }
    };
}
