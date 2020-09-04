package me.yimin.manhunt.commands;

import me.yimin.manhunt.Manhunt;
import me.yimin.manhunt.events.StartAutoTrackingEvent;
import me.yimin.manhunt.events.StopAutoTrackingEvent;
import me.yimin.manhunt.runnables.TrackerUpdaterTaskManager;
import me.yimin.manhunt.util.PdcUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.RED;

public class AutotrackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("manhunt.autotrack") && Manhunt.getPlugin().getConfig().getBoolean("permissions")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return false;
        }

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(TrackerUpdaterTaskManager.hasTask(player)){
                TrackerUpdaterTaskManager.cancelTask(player);
                player.sendMessage(RED + "Manual tracking disabled.");
            }

            if(PdcUtils.isAutoTracking(player)) {
                Bukkit.getPluginManager().callEvent(new StopAutoTrackingEvent(player));
                player.sendMessage(RED + "Auto tracking disabled.");
            }
            else Bukkit.getPluginManager().callEvent(new StartAutoTrackingEvent(player));
        }

        else sender.sendMessage("You must be a player to do this command!");

        return false;
    }

}
