package me.yimin.manhunt.listeners;

import me.yimin.manhunt.Manhunt;
import me.yimin.manhunt.events.StartAutoTrackingEvent;
import me.yimin.manhunt.events.StartTrackingEvent;
import me.yimin.manhunt.events.StopAutoTrackingEvent;
import me.yimin.manhunt.events.StopTrackingEvent;
import me.yimin.manhunt.runnables.AutoTrackerUpdaterTask;
import me.yimin.manhunt.runnables.TrackerUpdaterTaskManager;
import me.yimin.manhunt.util.PdcUtils;
import me.yimin.manhunt.util.ScoreBoardUtils;
import me.yimin.manhunt.util.TrackerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.ChatColor.DARK_GREEN;
import static org.bukkit.ChatColor.GREEN;

public class TrackerListener implements Listener {

    private final int TRACKER_TASK_DELAY = Manhunt.getPlugin().getConfig().getInt("update_tracker_every");

    @EventHandler
    public void onStartTrack(StartTrackingEvent event){
        Player player = event.getPlayer();
        String targetPlayerName = event.getTargetPlayerName();

        PdcUtils.setPlayerTracked(player, targetPlayerName);

        if(!TrackerUpdaterTaskManager.hasTask(player)) TrackerUpdaterTaskManager.startTask(player);

        ScoreBoardUtils.updatePlayerTracked(player);

        player.sendMessage(GREEN + "You are now tracking " + DARK_GREEN + targetPlayerName);
    }

    @EventHandler
    public void onStopTrack(StopTrackingEvent event){
        Player player = event.getPlayer();

        PdcUtils.removePlayerTracked(player);
        ScoreBoardUtils.updatePlayerTracked(player);

        TrackerUtils.removeTracker(player);
        TrackerUpdaterTaskManager.cancelTask(player);

    }

    @EventHandler
    public void onStartAutoTrack(StartAutoTrackingEvent event){
        Player player = event.getPlayer();

        PdcUtils.setIsAutoTracking(player, true);
        new AutoTrackerUpdaterTask(player).runTaskTimer(Manhunt.getPlugin(), 0, TRACKER_TASK_DELAY);

        ScoreBoardUtils.updateAutoTrackStatus(player);

        player.sendMessage(GREEN + "Auto tracking enabled.");
    }

    @EventHandler
    public void onStopAutoTrack(StopAutoTrackingEvent event){
        Player player = event.getPlayer();

        PdcUtils.setIsAutoTracking(player, false);
        PdcUtils.removePlayerTracked(player);

        ScoreBoardUtils.updateAutoTrackStatus(player);
        ScoreBoardUtils.updatePlayerTracked(player);

        TrackerUtils.removeTracker(player);
    }

}
