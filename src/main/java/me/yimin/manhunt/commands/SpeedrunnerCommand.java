package me.yimin.manhunt.commands;

import me.yimin.manhunt.Manhunt;
import me.yimin.manhunt.files.PlayersManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class SpeedrunnerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String helpMessage =
                GOLD + "§l" + "Speedrunner commands:\n" +
                        GOLD + "/speedrunner " + BLUE + "add " + GREEN + "<username>\n" +
                        GOLD + "/speedrunner " + BLUE + "remove " + GREEN + "<username>\n" +
                        GOLD + "/speedrunner " + BLUE + "reset\n" +
                        GOLD + "/speedrunner " + BLUE + "list"
                ;

        if(args.length > 0 && Manhunt.getPlugin().getConfig().getBoolean("permissions")) {
            switch(args[0].toLowerCase()){
                case "reset":
                    if(!sender.hasPermission("manhunt.speedrunner.reset")) {
                        sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                        return false;
                    }
                    break;
                case "list":
                    if(!sender.hasPermission("manhunt.speedrunner.list")) {
                        sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                        return false;
                    }
                    break;
                case "add":
                    if(!sender.hasPermission("manhunt.speedrunner.add")) {
                        sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                        return false;
                    }
                    break;
                case "remove":
                    if(!sender.hasPermission("manhunt.speedrunner.remove")) {
                        sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
                        return false;
                    }
                    break;
                default: sender.sendMessage(helpMessage);
            }
        }

        //If player has permissions
        switch (args.length) {
            case 1:
                switch (args[0].toLowerCase()) {
                    case "reset":
                        PlayersManager.removeAllSpeedrunners();
                        sender.sendMessage(GREEN + "All speedrunners removed.");
                        break;

                    case "list":
                        sender.sendMessage(
                                GOLD + "Speedrunners: " +
                                WHITE + PlayersManager.getSpeedrunners().toString().replace("[", "").replace("]", "")
                        );
                        break;

                    case "add":
                        sender.sendMessage(RED + "/speedrunner add <username>");
                        break;

                    case "remove":
                        sender.sendMessage(RED + "/speedrunner remove <username>");
                        break;

                    default: sender.sendMessage(helpMessage);

                }
                break;

            case 2:
                switch (args[0].toLowerCase()) {
                    case "add":
                        if (StringUtils.containsIgnoreCase(PlayersManager.getSpeedrunners().toString(), args[1])) {
                            sender.sendMessage(RED + args[1] + " is already a speedrunner!");
                        } else {
                            Player targetPlayer = Bukkit.getPlayer(args[1]);

                            //Using Player.getDisplayName() instead of args[1] to correct the letter casing
                            if(targetPlayer != null) {
                                PlayersManager.addSpeedrunner(targetPlayer.getDisplayName());
                                sender.sendMessage(GREEN + targetPlayer.getDisplayName() + " is now a speedrunner!");
                            }
                            else {
                                PlayersManager.addSpeedrunner(args[1]);
                                sender.sendMessage(GREEN + args[1] + " is now a speedrunner!");
                            }

                        }
                        break;

                    case "remove":
                        if (StringUtils.containsIgnoreCase(PlayersManager.getSpeedrunners().toString(), args[1])) {
                            String correctedLetterCaseName = PlayersManager.removeSpeedrunner(args[1]);
                            sender.sendMessage(GREEN + correctedLetterCaseName + " is no longer a speedrunner!");
                        }
                        else sender.sendMessage(RED + args[1] + " isn't a speedrunner!");
                        break;

                    default: sender.sendMessage(helpMessage);
                }
                break;

            default: sender.sendMessage(helpMessage);

        }
        return false;
    }

}
