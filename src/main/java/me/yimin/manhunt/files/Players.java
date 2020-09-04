package me.yimin.manhunt.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Players {

    private static File file;
    private static FileConfiguration players;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Manhunt").getDataFolder(), "players.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            } catch(IOException e){
                System.out.println("Couldn't create new file: players.yml");
                e.printStackTrace();
            }

        }

        players = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return players;
    }

    public static void save(){
        try{
            players.save(file);
        } catch(IOException e){
            System.out.println("Couldn't save players.yml file");
            e.printStackTrace();
        }
    }

    public static void reload(){
        players = YamlConfiguration.loadConfiguration(file);
    }

}
