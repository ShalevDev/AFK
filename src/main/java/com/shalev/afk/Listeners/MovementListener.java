package com.shalev.afk.Listeners;


import com.shalev.afk.AFK;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {

    private final AFK main;

    public MovementListener(AFK main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player p = e.getPlayer();

        //Update time since last moved
        main.lastTimeMoved.put(p.getUniqueId(),System.currentTimeMillis());

        //If the player is AFK, disable AFK
        if(main.afkManager.isAFK(p.getUniqueId())) main.afkManager.disableAFK(p);
    }
}
