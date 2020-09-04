package me.yimin.manhunt.util;

import me.yimin.manhunt.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class TrackerUtils{



    /**
     * Gets which slot/index the player carries a nether/lodestone tracker. If the player doesn't have one, method returns null
     * @param player target player
     * @return the slot which the player is carrying the tracker
     */
    private static Integer getSlotTracker(Player player){ ;
        for(int i = 0; i < player.getInventory().getSize(); i++){
            ItemStack item = player.getInventory().getItem(i);
            if(item != null && isTracker(item)){
                return i;
            }
        }
        return null;
    }

    /**
     * Returns if the target item is a tracker
     * @param item target Item
     * @return true if target item is a tracker
     */
    public static boolean isTracker(ItemStack item){
        if(item.hasItemMeta()){
            return item.getItemMeta().getPersistentDataContainer().has(ItemManager.TRACKER_ITEM_KEY, PersistentDataType.INTEGER);
        }
        return false;
    }

    /**
     * Gives target player a tracker
     * @param player target player
     * @param targetLocation location that tracker is going to track
     */
    public static void giveTracker(Player player, Location targetLocation){
        player.getInventory().addItem(ItemManager.getTracker(targetLocation));
    }

    /**
     * Determines if a player has a tracker in their inventory
     * @param player target player
     * @return true if target player has a tracker in their inventory
     */
    public static boolean hasTracker(Player player){
        return getSlotTracker(player) != null;
    }

    /**
     * removes tracker from player's inventory
     * @param player target player
     */
    public static void removeTracker(Player player) {
        player.getInventory().setItem(getSlotTracker(player), new ItemStack(Material.AIR));
    }

    /**
     * Determines if player is holding a tracker in their main/off hand
     * @param player target player
     * @return true if player is holding a tracker in their main/off hand
     */
    public static boolean isHoldingTracker(Player player){
        return isTracker(player.getEquipment().getItemInMainHand()) ||
                isTracker(player.getEquipment().getItemInOffHand());
    }

    /**
     * Gives target player a tracker that tracks the location of the
     * player name stored inside the target player's persistent data container "playerTracked" key
     * @param player target player
     */
    public static void updateTracker(Player player){

        if(isTracker(player.getItemOnCursor())){
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(PdcUtils.getPlayerTracked(player));
        Location targetPlayerLocation = targetPlayer.getLocation();

        if(!hasTracker(player)) giveTracker(player, targetPlayerLocation);
        else player.getInventory().setItem(getSlotTracker(player), ItemManager.getTracker(targetPlayerLocation));

        Location bedrockLocation = targetPlayer.getLocation();
        bedrockLocation.setY(0);

        if(!bedrockLocation.getBlock().getType().equals(Material.LODESTONE)) BlockUtils.placeLodestoneForTracking(targetPlayer);

    }
}
