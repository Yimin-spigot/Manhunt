package me.yimin.manhunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StopTrackingEvent extends Event {

    private final Player PLAYER;

    public StopTrackingEvent(Player p){
        PLAYER = p;
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
