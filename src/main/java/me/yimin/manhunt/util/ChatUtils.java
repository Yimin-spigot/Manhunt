package me.yimin.manhunt.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ChatUtils {

    /**
     * Sends an action bar message to target player.
     * @param player target player
     * @param message message to be sent to player
     */
    public static void sendActionBarMessage(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

}
