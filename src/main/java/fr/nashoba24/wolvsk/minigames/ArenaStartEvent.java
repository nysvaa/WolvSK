package fr.nashoba24.wolvsk.minigames;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaStartEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
    private Arena a;
    private Minigame game;
 
    public ArenaStartEvent(Minigame mg, Arena arena) {
        this.a = arena;
        this.game = mg;
    }
 
    public Arena getArena() {
        return this.a;
    }
    
    public Minigame getMinigame() {
    	return this.game;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}
