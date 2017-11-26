package fr.nashoba24.wolvsk.misc;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SteerRightEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
 
    public SteerRightEvent(Player p) {
    	this.player = p;
    }
    
    public Player getPlayer() {
    	return this.player;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}
