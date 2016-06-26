package fr.nashoba24.wolvsk.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class ExprMissionsRace extends SimpleExpression<String>{
	private Expression<String> race;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		race = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "missions of race";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		ArrayList<String> s = WolvMC.getMissionsForRace(race.getSingle(e));
		String t = String.join(", ", s);
		return new String[]{ t };
	}
}

