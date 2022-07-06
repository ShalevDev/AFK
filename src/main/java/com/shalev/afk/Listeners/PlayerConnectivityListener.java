package com.shalev.afk.Listeners;

import com.shalev.afk.AFK;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectivityListener implements Listener {

    private final AFK main;

    public PlayerConnectivityListener(AFK main){
        this.main = main;
    }


    //Inserts the player to the necessary maps and resets their title in case they were AFK when they left
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.getPlayer().resetTitle();
        main.AFKTracker.put(e.getPlayer().getUniqueId(),false);
        main.lastTimeMoved.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
    }


    //Remove the player from all the maps to prevent saving unnecessary information
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        UUID uuid = e.getPlayer().getUniqueId();
        if(main.afkManager.isAFK(uuid)) main.afkManager.clearBlindness(e.getPlayer());
        main.AFKTracker.remove(uuid);
        main.lastTimeMoved.remove(uuid);
        main.titleManager.stopTitle(e.getPlayer());
    }
}
