package me.yimin.manhunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StartTrackingEvent extends Event {

    private final Player PLAYER;
    private final String TARGET_PLAYER_NAME;

    public StartTrackingEvent(Player p, String targetPlayerName){
        PLAYER = p;
        TARGET_PLAYER_NAME = targetPlayerName;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }

    public Player getPlayer(){
        return PLAYER;
    }

    public String getTargetPlayerName(){
        return TARGET_PLAYER_NAME;
    }

}
