package me.yimin.manhunt.items;

import me.yimin.manhunt.Manhunt;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemManager {

    private static String getTrackerDisplayName(){
        return Manhunt.getPlugin().getConfig().getString("tracker.display_name");
    }

    private static List<String> getTrackerLore(){
        return Manhunt.getPlugin().getConfig().getStringList("tracker.lore");
    }

    public static final NamespacedKey TRACKER_ITEM_KEY = new NamespacedKey(Manhunt.getPlugin(), "tracker");

    public static ItemStack getTracker(Location location){
        location.setY(0);
        ItemStack tracker = new ItemStack(Material.COMPASS, 1);
        ItemMeta trackerMeta = tracker.getItemMeta();
        trackerMeta.setDisplayName(getTrackerDisplayName());
        trackerMeta.setLore(getTrackerLore());
        CompassMeta trackerCompassMeta = (CompassMeta) trackerMeta;
        trackerCompassMeta.setLodestoneTracked(true);
        trackerCompassMeta.setLodestone(location);
        trackerCompassMeta.getPersistentDataContainer().set(TRACKER_ITEM_KEY, PersistentDataType.INTEGER, 1);
        tracker.setItemMeta(trackerMeta);

        return tracker;
    }

}
