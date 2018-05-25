package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprMinigameByName extends SimpleExpression<Minigame>{
	
	private Expression<String> name;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Minigame> getReturnType() {
		return Minigame.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		name = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "minigame";
	}
	
	@Override
	@Nullable
	protected Minigame[] get(Event e) {
		return new Minigame[]{ Minigames.getByName(name.getSingle(e)) };
	}
}

