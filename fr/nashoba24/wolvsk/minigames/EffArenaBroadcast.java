package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffArenaBroadcast extends Effect {
	
	private Expression<Arena> arena;
	private Expression<String> msg;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		msg = (Expression<String>) expr[0];
		arena = (Expression<Arena>) expr[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "broadcast in arena";
	}
	
	@Override
	protected void execute(Event e) {
		if(arena.getSingle(e)!=null) 
		arena.getSingle(e).broadcast(msg.getSingle(e));
	}
}
