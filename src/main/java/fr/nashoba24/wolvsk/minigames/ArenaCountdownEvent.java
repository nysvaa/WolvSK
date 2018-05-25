package fr.nashoba24.wolvsk.minigames;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaCountdownEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
    private Arena a;
    private Minigame game;
    private Integer countdown;
 
    public ArenaCountdownEvent(Minigame mg, Arena arena, Integer c) {
        this.a = arena;
        this.game = mg;
        this.countdown = c;
    }
 
    public Arena getArena() {
        return this.a;
    }
    
    public Minigame getMinigame() {
    	return this.game;
    }
    
    public Integer getCountdown() {
    	return this.countdown;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
 
}
