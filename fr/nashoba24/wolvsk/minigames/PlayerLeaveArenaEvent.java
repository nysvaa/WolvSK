package fr.nashoba24.wolvsk.minigames;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeaveArenaEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
    private Arena a;
    private Minigame game;
    private Player pl;
 
    public PlayerLeaveArenaEvent(Minigame mg, Arena arena, Player p) {
        this.a = arena;
        this.game = mg;
        this.pl = p;
    }
 
    public Arena getArena() {
        return this.a;
    }
    
    public Minigame getMinigame() {
    	return this.game;
    }
    
    public Player getPlayer() {
    	return this.pl;
    }

	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
