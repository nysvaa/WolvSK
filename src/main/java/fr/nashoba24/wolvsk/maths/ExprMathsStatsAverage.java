package fr.nashoba24.wolvsk.maths;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprMathsStatsAverage extends SimpleExpression<Double> {
	
	private Expression<Number> nbs;
	
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
		nbs = (Expression<Number>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "average";
	}
	
	@Override
	@Nullable
	protected Double[] get(Event e) {
		Number[] l = nbs.getAll(e);
		Double tot = 0.0;
		for(Number n : l) {
			tot += n.doubleValue();
		}
        return new Double[]{ tot/l.length };
	}
}

