package me.yimin.manhunt.runnables;

import me.yimin.manhunt.Manhunt;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class TrackerUpdaterTaskManager {

    private static HashMap<UUID, BukkitTask> taskMap = new HashMap<>();

    public static void startTask(Player player){
        taskMap.put(player.getUniqueId(), new TrackerUpdaterTask(player).runTaskTimer(Manhunt.getPlugin(), 0, Manhunt.getPlugin().getConfig().getInt("update_tracker_every")));
    }

    public static void cancelTask(Player player){
        taskMap.get(player.getUniqueId()).cancel();
        taskMap.remove(player.getUniqueId());
    }

    public static boolean hasTask(Player player){
        return taskMap.containsKey(player.getUniqueId());
    }

}
