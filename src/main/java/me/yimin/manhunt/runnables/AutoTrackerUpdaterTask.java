package me.yimin.manhunt.runnables;

import me.yimin.manhunt.Manhunt;
import me.yimin.manhunt.events.StopAutoTrackingEvent;
import me.yimin.manhunt.util.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;

public class AutoTrackerUpdaterTask extends BukkitRunnable {

    private final Player PLAYER;
    public AutoTrackerUpdaterTask(Player p){
        PLAYER = p;
    }

    @Override
    public void run() {
        if(!Bukkit.getOnlinePlayers().contains(PLAYER) || !PdcUtils.isAutoTracking(PLAYER)){
            this.cancel();
        }
        else if(!PLAYER.hasPermission("manhunt.autotrack") && Manhunt.getPlugin().getConfig().getBoolean("permissions")){
            PLAYER.sendMessage(RED + "You no longer have permission to autotrack");
            Bukkit.getServer().getPluginManager().callEvent(new StopAutoTrackingEvent(PLAYER));
        }
        else{
            Player nearestSpeedrunner = PlayerUtils.getNearestSpeedrunner(PLAYER);

            if(nearestSpeedrunner != null){
                PdcUtils.setPlayerTracked(PLAYER, nearestSpeedrunner.getDisplayName());
                TrackerUtils.updateTracker(PLAYER);
            }
            else{
                PdcUtils.removePlayerTracked(PLAYER);
            }

            ScoreBoardUtils.updatePlayerTracked(PLAYER);

            if(TrackerUtils.isHoldingTracker(PLAYER)){
                if(nearestSpeedrunner != null) ChatUtils.sendActionBarMessage(PLAYER, GREEN + "§lTracking nearest speedrunner: " + DARK_GREEN + nearestSpeedrunner.getDisplayName());
                else ChatUtils.sendActionBarMessage(PLAYER, RED + "§lThere are no speedrunners in this world");
            }

        }
    }
}
