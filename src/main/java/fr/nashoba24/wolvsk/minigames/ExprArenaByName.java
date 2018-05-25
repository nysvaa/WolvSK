package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprArenaByName extends SimpleExpression<Arena>{
	
	private Expression<String> name;
	private Expression<Minigame> mg;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Arena> getReturnType() {
		return Arena.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern==0) {
			name = (Expression<String>) expr[0];
			mg = (Expression<Minigame>) expr[1];
		}
		else if(matchedPattern==1) {
			name = (Expression<String>) expr[1];
			mg = (Expression<Minigame>) expr[0];	
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "arena";
	}
	
	@Override
	@Nullable
	protected Arena[] get(Event e) {
		if(mg.getSingle(e)!=null) 
		return new Arena[]{ mg.getSingle(e).getArena(name.getSingle(e), true) };
		return null;
	}
}

