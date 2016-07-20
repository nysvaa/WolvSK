package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffCreateMinigame extends Effect {
	
	private Expression<String> name;
	private Expression<String> cmd;
	private Expression<String> prefix;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		name = (Expression<String>) expr[0];
		cmd = (Expression<String>) expr[1];
		prefix = (Expression<String>) expr[2];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "create minigame";
	}
	
	@Override
	protected void execute(Event e) {
		Minigames.createMinigame(name.getSingle(e), cmd.getSingle(e), prefix.getSingle(e));
	}
}
