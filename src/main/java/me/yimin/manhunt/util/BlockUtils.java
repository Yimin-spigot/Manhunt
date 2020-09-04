package me.yimin.manhunt.util;

import me.yimin.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockUtils {
    public static void placeLodestoneForTracking(Player player){
        final Location LOCATION_BEDROCK = player.getLocation();
        final Location LOCATION_ABOVE_BEDROCK = player.getLocation();
        LOCATION_BEDROCK.setY(0);
        LOCATION_ABOVE_BEDROCK.setY(1);

        final Block BLOCK_BEDROCK = LOCATION_BEDROCK.getBlock();
        final Block BLOCK_ABOVE_BEDROCK = LOCATION_ABOVE_BEDROCK.getBlock();
        final Material BLOCK_ABOVE_BEDROCK_MATERIAL = LOCATION_ABOVE_BEDROCK.getBlock().getType();

        BLOCK_BEDROCK.setType(Material.LODESTONE);

        BLOCK_ABOVE_BEDROCK.setType(Material.BEDROCK);

        //Bukkit runnable for removing lodestone
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!(
                        player.getLocation().getBlockX() == LOCATION_BEDROCK.getBlockX() &&
                        player.getLocation().getBlockZ() == LOCATION_BEDROCK.getBlockZ() &&
                        PlayerUtils.isGettingTracked(player) &&
                        Bukkit.getPlayer(player.getDisplayName()) != null
                ))
                {
                    BLOCK_ABOVE_BEDROCK.setType(BLOCK_ABOVE_BEDROCK_MATERIAL);
                    BLOCK_BEDROCK.setType(Material.BEDROCK);
                    cancel();
                }
            }
        }.runTaskTimer(Manhunt.getPlugin(), 0, 1);

    }

}
