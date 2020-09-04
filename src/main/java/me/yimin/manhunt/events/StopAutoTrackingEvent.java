package me.yimin.manhunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StopAutoTrackingEvent extends Event {

    private final Player PLAYER;

    public StopAutoTrackingEvent(Player player){
        PLAYER = player;
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

}
