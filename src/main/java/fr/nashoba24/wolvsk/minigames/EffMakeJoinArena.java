package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffMakeJoinArena extends Effect {
	
	private Expression<Arena> arena;
	private Expression<Player> p;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		p = (Expression<Player>) expr[0];
		arena = (Expression<Arena>) expr[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "make player join arena";
	}
	
	@Override
	protected void execute(Event e) {
		if(arena.getSingle(e)!=null) 
		Minigames.join(p.getSingle(e), arena.getSingle(e).getMinigame(), arena.getSingle(e), false);
	}
}
