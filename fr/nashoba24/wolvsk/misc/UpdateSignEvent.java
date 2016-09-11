package fr.nashoba24.wolvsk.misc;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateSignEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private SignGui gui;
 
    public UpdateSignEvent(Player p, SignGui signgui) {
    	this.player = p;
    	this.gui = signgui;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public SignGui getSignGui() {
    	return this.gui;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}
