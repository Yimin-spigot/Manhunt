package me.yimin.manhunt.listeners;

import me.yimin.manhunt.events.StartAutoTrackingEvent;
import me.yimin.manhunt.events.StartTrackingEvent;
import me.yimin.manhunt.files.PlayersManager;
import me.yimin.manhunt.util.PdcUtils;
import me.yimin.manhunt.util.ScoreBoardUtils;
import me.yimin.manhunt.util.TrackerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        ScoreBoardUtils.setScoreBoard(player);
        PlayersManager.correctLetterCaseSpeedrunner(player.getDisplayName());

        if(PdcUtils.isAutoTracking(player)){
            Bukkit.getPluginManager().callEvent(new StartAutoTrackingEvent(player));
        }
        else if(PdcUtils.getPlayerTracked(player) != null) {
            Bukkit.getPluginManager().callEvent(new StartTrackingEvent(player, PdcUtils.getPlayerTracked(player)));
        }

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        if(TrackerUtils.isTracker(event.getItemDrop().getItemStack())) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        event.getDrops().removeIf(TrackerUtils::isTracker);
    }

}
