package fr.nashoba24.wolvsk.maths;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprMathsPi extends SimpleExpression<Double> {
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "pi";
	}
	
	@Override
	@Nullable
	protected Double[] get(Event e) {
        return new Double[]{ Math.PI };
	}
}

