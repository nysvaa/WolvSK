package fr.nashoba24.wolvsk.maths;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprMathsExp extends SimpleExpression<Double> {
	
	private Expression<Number> nb;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		nb = (Expression<Number>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "exp";
	}
	
	@Override
	@Nullable
	protected Double[] get(Event e) {
        return new Double[]{ Math.exp(nb.getSingle(e).doubleValue()) };
	}
}

