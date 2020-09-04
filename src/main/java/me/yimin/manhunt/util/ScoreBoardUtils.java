package me.yimin.manhunt.util;

import me.yimin.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import static org.bukkit.ChatColor.*;

public class ScoreBoardUtils {

    private static void addText(Objective objective, int score, Score textName, String text){
        textName = objective.getScore(text);
        textName.setScore(score);
    }

    /**
     * sets the custom scoreboard for the target player
     * @param player target player
     */
    public static void setScoreBoard(Player player){
        FileConfiguration config = Manhunt.getPlugin().getConfig();
        String title = config.getString("scoreboard.title");
        String autoTrackingTitle = config.getString("scoreboard.autotracking_title");
        String trackingTitle = config.getString("scoreboard.tracking_title");
        assert title != null;
        assert autoTrackingTitle != null;
        assert trackingTitle != null;


        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("Manhunt", "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score autoTracking = objective.getScore(autoTrackingTitle);
        autoTracking.setScore(5);

        Team autoTrackStatus = board.registerNewTeam("autoTrackStatus");
        autoTrackStatus.addEntry(BLUE + "");
        objective.getScore(BLUE + "").setScore(4);
        if(PdcUtils.isAutoTracking(player)){
            autoTrackStatus.setPrefix(GREEN + "On");
        }
        else autoTrackStatus.setPrefix(RED + "Off");

        Score emptySpace = objective.getScore("");
        emptySpace.setScore(3);

        Score tracking = objective.getScore(trackingTitle);
        tracking.setScore(2);

        Team playerTracked = board.registerNewTeam("playerTracked");
        playerTracked.addEntry(GREEN + "");
        objective.getScore(GREEN + "").setScore(1);
        if(PdcUtils.getPlayerTracked(player) != null){
            playerTracked.setPrefix(WHITE + PdcUtils.getPlayerTracked(player));
        }
        else playerTracked.setPrefix(RED + "Off");

        player.setScoreboard(board);

    }

    /**
     * Updates the autotrack status portion of the player's scoreboard.
     * If the player is autotracking, display "On".
     * If the player isn't autotracking, display "Off"
     * @param player target player
     */
    public static void updateAutoTrackStatus(Player player){

        Scoreboard board = player.getScoreboard();

        if(PdcUtils.isAutoTracking(player)) board.getTeam("autoTrackStatus").setPrefix(GREEN + "On");
        else board.getTeam("autoTrackStatus").setPrefix(RED + "Off");
    }

    /**
     * Updates the player tracked portion of the scoreboard.
     * Displays the player that target player is currently tracking(stored inside the target player's PDC).
     * Displays "off" if player isn't tracking anyone.
     * @param player
     */
    public static void updatePlayerTracked(Player player){
        Scoreboard board = player.getScoreboard();
        String playerTrackedName = PdcUtils.getPlayerTracked(player);

        if(playerTrackedName == null) board.getTeam("playerTracked").setPrefix(RED + "Off");
        else if(Bukkit.getPlayer(playerTrackedName) != null) board.getTeam("playerTracked").setPrefix(GREEN + playerTrackedName);
        else board.getTeam("playerTracked").setPrefix(RED + playerTrackedName + " (Offline)");
    }

}
