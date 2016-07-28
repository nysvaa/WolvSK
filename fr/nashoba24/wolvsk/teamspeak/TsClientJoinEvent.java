package fr.nashoba24.wolvsk.teamspeak;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class TsClientJoinEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Client p;
 
    public TsClientJoinEvent(Client client) {
    	p = client;
    }
    
    public Client getClient() {
    	return p;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
