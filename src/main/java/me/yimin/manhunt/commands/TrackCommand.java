package me.yimin.manhunt.commands;

import me.yimin.manhunt.Manhunt;
import me.yimin.manhunt.events.StartTrackingEvent;
import me.yimin.manhunt.events.StopAutoTrackingEvent;
import me.yimin.manhunt.events.StopTrackingEvent;
import me.yimin.manhunt.files.PlayersManager;
import me.yimin.manhunt.util.PdcUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class TrackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("manhunt.track") && Manhunt.getPlugin().getConfig().getBoolean("permissions")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return false;
        }

        if(!(sender instanceof Player)) sender.sendMessage(RED + "You must be a player to do this command!");
        else{
            Player player = (Player) sender;

            if (args.length == 1) {
                Player targetPlayer = Bukkit.getPlayer(args[0]);

                if(!PdcUtils.isAutoTracking(player) && PdcUtils.getPlayerTracked(player) != null && PdcUtils.getPlayerTracked(player).equalsIgnoreCase(args[0])){
                    Bukkit.getPluginManager().callEvent(new StopTrackingEvent(player));
                    player.sendMessage(RED + "You are no longer tracking " + DARK_RED + args[0]);
                }
                else if (targetPlayer == null) player.sendMessage(RED + args[0] + " is not online!");
                else if (!PlayersManager.getSpeedrunners().contains(targetPlayer.getDisplayName()))
                    player.sendMessage(RED + args[0] + " is not a speedrunner!");
                else {
                    if (PdcUtils.isAutoTracking(player)){
                        Bukkit.getPluginManager().callEvent(new StopAutoTrackingEvent(player));
                        player.sendMessage(RED + "Auto tracking disabled.");
                    }
                    Bukkit.getPluginManager().callEvent(new StartTrackingEvent(player, targetPlayer.getDisplayName()));
                }
            }
            else{
                player.sendMessage(GOLD + "/track <speedrunner> " + WHITE + "to track/untrack a speedrunner");
            }
        }

        return false;
    }
}
