package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffStartArena extends Effect {
	
	private Expression<Arena> arena;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		arena = (Expression<Arena>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "start arena";
	}
	
	@Override
	protected void execute(Event e) {
		if(arena.getSingle(e)!=null) 
		Minigames.start(arena.getSingle(e).getMinigame(), arena.getSingle(e), true);
	}
}
