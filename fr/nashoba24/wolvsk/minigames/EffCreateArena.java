package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffCreateArena extends Effect {
	
	private Expression<Minigame> mg;
	private Expression<String> name;
	private Expression<Integer> min;
	private Expression<Integer> max;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		name = (Expression<String>) expr[0];
		min = (Expression<Integer>) expr[1];
		max = (Expression<Integer>) expr[2];
		mg = (Expression<Minigame>) expr[3];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "create arena";
	}
	
	@Override
	protected void execute(Event e) {
		if(mg.getSingle(e)!=null) 
		mg.getSingle(e).addArena(new Arena(mg.getSingle(e), name.getSingle(e), min.getSingle(e), max.getSingle(e)), true);
	}
}
