package me.yimin.manhunt;

import me.yimin.manhunt.commands.AutotrackCommand;
import me.yimin.manhunt.commands.ManhuntCommand;
import me.yimin.manhunt.commands.SpeedrunnerCommand;
import me.yimin.manhunt.commands.TrackCommand;
import me.yimin.manhunt.files.Players;
import me.yimin.manhunt.listeners.PlayerListener;
import me.yimin.manhunt.listeners.TrackerListener;
import me.yimin.manhunt.util.ManhuntUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Manhunt extends JavaPlugin {

    private static Manhunt plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new TrackerListener(), this);

        getCommand("autotrack").setExecutor(new AutotrackCommand());
        getCommand("manhunt").setExecutor(new ManhuntCommand());
        getCommand("speedrunner").setExecutor(new SpeedrunnerCommand());
        getCommand("track").setExecutor(new TrackCommand());

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Players.setup();
        Players.get().addDefault("speedrunners", new ArrayList<String>());
        Players.get().options().copyDefaults(true);
        Players.save();

        ManhuntUtils.setup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Manhunt getPlugin() {
        return plugin;
    }
}
