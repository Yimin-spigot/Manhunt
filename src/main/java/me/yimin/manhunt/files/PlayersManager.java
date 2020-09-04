package me.yimin.manhunt.files;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class PlayersManager {


    private static FileConfiguration config(){
        return Players.get();
    }
    private static void saveConfig(){
        Players.save();
    }
    private static List<String> getSpeedrunnersList(){
        return config().getStringList("speedrunners");
    }

    public static void addSpeedrunner(String playerName){
        List<String> list = getSpeedrunnersList();
        list.add(playerName);
        config().set("speedrunners", list);
        saveConfig();
    }

    public static String removeSpeedrunner(String playerName){
        for(String str : getSpeedrunnersList()){
            if(str.equalsIgnoreCase(playerName)){
                List<String> list = getSpeedrunnersList();
                list.remove(str);
                config().set("speedrunners", list);
                saveConfig();
                return str;
            }
        }
        return null;
    }

    public static void removeAllSpeedrunners(){
        List<String> list = getSpeedrunnersList();
        list.clear();
        config().set("speedrunners", list);
    }

    public static List<String> getSpeedrunners(){
        return getSpeedrunnersList();
    }

    /**
     * Corrects the letter casing of username stored inside file.
     * Ex. if player name is "Bob" and username stored inside file is "bob", method will replace "bob" to "Bob"
     * @param playerName target player username
     */
    public static void correctLetterCaseSpeedrunner(String playerName){
        for(String str : getSpeedrunnersList()){
            if(str.equalsIgnoreCase(playerName)){
                List<String> list = getSpeedrunnersList();
                list.set(list.indexOf(str), playerName);
                config().set("speedrunners", list);
                saveConfig();
            }
        }
    }

}
