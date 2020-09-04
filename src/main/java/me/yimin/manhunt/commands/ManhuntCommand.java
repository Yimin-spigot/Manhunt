package me.yimin.manhunt.commands;

import me.yimin.manhunt.Manhunt;
import me.yimin.manhunt.files.Players;
import me.yimin.manhunt.util.ScoreBoardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManhuntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



       if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
           if(!sender.hasPermission("manhunt.reload") && Manhunt.getPlugin().getConfig().getBoolean("permissions")) {
               sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
               return false;
           }

           Manhunt.getPlugin().reloadConfig();
           Players.reload();
           for(Player pl : Bukkit.getOnlinePlayers()) ScoreBoardUtils.setScoreBoard(pl);
           sender.sendMessage(ChatColor.GREEN + "Successfully reloaded Manhunt.");
       }

        return false;
    }
}
