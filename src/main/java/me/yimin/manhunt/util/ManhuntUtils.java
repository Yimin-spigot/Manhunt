package me.yimin.manhunt.util;

import me.yimin.manhunt.events.StartAutoTrackingEvent;
import me.yimin.manhunt.events.StartTrackingEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ManhuntUtils {

    /**
     * This method is mainly useful for when the server reloads.
     * Sets up scoreboard for all the players currently online,
     * starts up tracking tasks for players that are currently tracking,
     * and starts up auto tracking tasks for players that are currently autotracking.
     */
    public static void setup(){
        for(Player player : Bukkit.getOnlinePlayers()){
            ScoreBoardUtils.setScoreBoard(player);

            if(PdcUtils.isAutoTracking(player)){
                Bukkit.getPluginManager().callEvent(new StartAutoTrackingEvent(player));
            }
            else if(PdcUtils.getPlayerTracked(player) != null) {
                Bukkit.getPluginManager().callEvent(new StartTrackingEvent(player, PdcUtils.getPlayerTracked(player)));
            }
        }
    }

}
