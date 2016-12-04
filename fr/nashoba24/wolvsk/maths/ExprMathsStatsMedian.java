package fr.nashoba24.wolvsk.maths;

import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprMathsStatsMedian extends SimpleExpression<Double> {
	
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
		return "median";
	}
	
	@Override
	@Nullable
	protected Double[] get(Event e) {
		Number[] list = nbs.getAll(e);
		ArrayList<Double> d = new ArrayList<Double>();
		for(Number n : list) {
			d.add(n.doubleValue());
		}
		Collections.sort(d);
		if((d.size() & 1) == 0) {
	        return new Double[]{ (d.get(d.size()/2) + d.get(d.size()/2 - 1))/2 };
		}
		else {
			return new Double[]{ d.get(d.size()/2) };
		}
	}
}

