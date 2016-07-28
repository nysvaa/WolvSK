package fr.nashoba24.wolvsk.teamspeak;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TsClientLeaveEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private String p;
 
    public TsClientLeaveEvent(String client) {
    	p = client;
    }
    
    public String getInvoker() {
    	return p;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
