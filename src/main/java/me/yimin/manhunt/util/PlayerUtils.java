package me.yimin.manhunt.util;

import me.yimin.manhunt.files.PlayersManager;
import org.bukkit.entity.Player;

public class PlayerUtils {


    /**
     * Gets nearest speedrunner that's in the same world as target player
     * @param player target player
     * @return nearest speedrunner
     */
    public static Player getNearestSpeedrunner(Player player){
        Player nearestPlayer = null;
        Double nearestDistance = null;

        for(Player p : player.getWorld().getPlayers()){
            if(PlayersManager.getSpeedrunners().contains(p.getDisplayName())){
                double distance = player.getLocation().distance(p.getLocation());
                if(nearestDistance == null || distance < nearestDistance){
                    nearestPlayer = p;
                    nearestDistance = distance;
                }
            }
        }
        return nearestPlayer;
    }

    public static boolean isGettingTracked(Player player){
        for(Player p : player.getWorld().getPlayers()){
            if(PdcUtils.getPlayerTracked(p) != null && PdcUtils.getPlayerTracked(p).equals(player.getDisplayName())) return true;
        }
        return false;
    }

}
