package me.yimin.manhunt.runnables;

import me.yimin.manhunt.Manhunt;
import me.yimin.manhunt.events.StopTrackingEvent;
import me.yimin.manhunt.files.PlayersManager;
import me.yimin.manhunt.util.ChatUtils;
import me.yimin.manhunt.util.PdcUtils;
import me.yimin.manhunt.util.TrackerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.ChatColor.*;

public class TrackerUpdaterTask extends BukkitRunnable {

    private final Player PLAYER;
    public TrackerUpdaterTask(Player p){
        PLAYER = p;
    }

    @Override
    public void run() {
        //Cancelling this runnable

        if(PLAYER == null) {
            System.out.println("player left");
            this.cancel();
        }
        else if(!PLAYER.hasPermission("manhunt.track") && Manhunt.getPlugin().getConfig().getBoolean("permissions")){
            PLAYER.sendMessage(RED + "You no longer have permission to track");
            Bukkit.getServer().getPluginManager().callEvent(new StopTrackingEvent(PLAYER));
        }
        else if(!PlayersManager.getSpeedrunners().contains(PdcUtils.getPlayerTracked(PLAYER))){
            PLAYER.sendMessage(RED + "Tracking stopped. Reason: " + PdcUtils.getPlayerTracked(PLAYER) + " is no longer a speedrunner.");
            Bukkit.getPluginManager().callEvent(new StopTrackingEvent(PLAYER));
        }
        else {
            String targetPlayerName = PdcUtils.getPlayerTracked(PLAYER);
            Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

            if(targetPlayer != null && PLAYER.getWorld() == targetPlayer.getWorld()) TrackerUtils.updateTracker(PLAYER);

            //Action bar text display for when player is holding tracker
            if(TrackerUtils.isHoldingTracker(PLAYER)){
                if(targetPlayer == null) ChatUtils.sendActionBarMessage(PLAYER, RED + targetPlayerName + "§l is offline");
                else if(PLAYER.getWorld() != targetPlayer.getWorld()) ChatUtils.sendActionBarMessage(PLAYER, RED + targetPlayerName + "§l is in another dimension");
                else ChatUtils.sendActionBarMessage(PLAYER, GREEN + "§lTracking: " + DARK_GREEN + targetPlayerName);
            }
        }

    }
}
