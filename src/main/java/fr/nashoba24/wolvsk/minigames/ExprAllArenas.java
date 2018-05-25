package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprAllArenas extends SimpleExpression<Arena>{
	
	private Expression<Minigame> mg;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends Arena> getReturnType() {
		return Arena.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		mg = (Expression<Minigame>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "all arenas";
	}
	
	@Override
	@Nullable
	protected Arena[] get(Event e) {
		if(mg.getSingle(e)!=null) 
		return mg.getSingle(e).getArenas();
		return null;
	}
}

