package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.*;

public class ExprListSort extends SimpleExpression<Object> {
	
	private Expression<Object> var;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		var = (Expression<Object>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "list sorted";
	}
	
	@Override
	@Nullable
	protected Object[] get(Event e) {
		Object[] list = var.getAll(e);
		ArrayList<Object> arr = new ArrayList<Object>(Arrays.asList(list));
		boolean i = false;
		boolean s = false;
		List<String> strings = new ArrayList<String>();
		List<Number> Numbers = new ArrayList<Number>();
		for(Object o : arr) {
			if(i) {
				if(!isNumber(o)) {
					return new Object[]{ var.getAll(e) };
				}
				else {
					Numbers.add((Number) o);
				}
			}
			else if(s) {
				if(!o.getClass().equals(String.class) ) {
					return new Object[]{ var.getAll(e) };
				}
				else {
					strings.add((String) o);
				}
			}
			else {
				if(isNumber(o)) {
					i = true;
					Numbers.add((Number) o);
				}
				else if(o.getClass().equals(String.class) ) {
					s = true;
					strings.add((String) o);
				}
				else {
					return new Object[]{ var.getAll(e) };
				}
			}
		}
		if(i) {
			Collections.sort(Numbers ,new Comparator<Number>() {
			    @Override
			    public int compare(Number o1, Number o2) {
			        Double d1 = (o1 == null) ? Double.POSITIVE_INFINITY : o1.doubleValue();
			        Double d2 = (o2 == null) ? Double.POSITIVE_INFINITY : o2.doubleValue();
			        return  d1.compareTo(d2);
			    }
			});
	        return new Object[]{ Numbers.toArray(new Number[Numbers.size()]) };
		}
		if(s) {
			Collections.sort(strings);
			return new Object[]{ strings.toArray(new String[strings.size()]) };
		}
		return null;
	}
	
	public static boolean isNumber(Object o) {
		if(o instanceof Long || o instanceof Integer || o instanceof Double || o instanceof Float || o instanceof Number) {
			return true;
		}
		return false;
	}
}

