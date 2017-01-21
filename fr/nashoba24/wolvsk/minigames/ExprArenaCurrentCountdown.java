package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprArenaCurrentCountdown extends SimpleExpression<Integer>{
	private Expression<Arena> arena;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		arena = (Expression<Arena>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "arena countdown";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if(arena.getSingle(e)!=null) 
		return new Integer[]{ arena.getSingle(e).getCountdown() };
		return null;
	}
}

