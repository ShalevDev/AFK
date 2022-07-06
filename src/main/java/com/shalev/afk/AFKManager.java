package com.shalev.afk;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;


public class AFKManager {

    private final AFK main;

    public AFKManager(AFK main){
        this.main = main;
    }

    public void activateAFK(Player p){
        //Add a blindness effect to the player
        PotionEffect blindnessEffect = new PotionEffect(PotionEffectType.BLINDNESS,Integer.MAX_VALUE,0,false,false);
        p.addPotionEffect(blindnessEffect);

        //Display the title to the player
        main.titleManager.sendTitle(p);

        //Set the player as AFK
        main.AFKTracker.put(p.getUniqueId(),true);
    }

    //Returns whether the player is registered as AFK
    public boolean isAFK(UUID uuid){
        return main.AFKTracker.get(uuid);
    }


    public void disableAFK(Player p){
        //Remove blindness from the player
        p.removePotionEffect(PotionEffectType.BLINDNESS);

        //Clear the player's title
        main.titleManager.stopTitle(p);

        //Set the player as not AFK
        main.AFKTracker.put(p.getUniqueId(),false);
    }

    //Clears the player's blindness
    public void clearBlindness(Player p){
        p.removePotionEffect(PotionEffectType.BLINDNESS);
    }

}
