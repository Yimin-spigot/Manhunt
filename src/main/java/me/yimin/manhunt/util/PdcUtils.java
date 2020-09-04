package me.yimin.manhunt.util;

import me.yimin.manhunt.Manhunt;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PdcUtils {

    private static final NamespacedKey IS_AUTO_TRACKING_KEY = new NamespacedKey(Manhunt.getPlugin(),"isAutoTracking");
    private static final NamespacedKey PLAYER_TRACKED_KEY = new NamespacedKey(Manhunt.getPlugin(),"playerTracked");

    public static void setPlayerTracked(Player player, String value){
        player.getPersistentDataContainer().set(PLAYER_TRACKED_KEY, PersistentDataType.STRING, value);
    }

    public static void removePlayerTracked(Player player){
        player.getPersistentDataContainer().remove(PLAYER_TRACKED_KEY);
    }

    public static String getPlayerTracked(Player player){
        return player.getPersistentDataContainer().get(PLAYER_TRACKED_KEY, PersistentDataType.STRING);
    }

    public static void setIsAutoTracking(Player player, Boolean value){
        if(value) player.getPersistentDataContainer().set(IS_AUTO_TRACKING_KEY, PersistentDataType.INTEGER, 1);
        else player.getPersistentDataContainer().remove(IS_AUTO_TRACKING_KEY);
    }

    public static boolean isAutoTracking(Player player){
        return player.getPersistentDataContainer().has(IS_AUTO_TRACKING_KEY, PersistentDataType.INTEGER);
    }

}
