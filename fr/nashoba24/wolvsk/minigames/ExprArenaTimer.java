package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprArenaTimer extends SimpleExpression<Integer>{
	private Expression<Arena> arena;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		arena = (Expression<Arena>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "arena default timer";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if(arena.getSingle(e)!=null) 
		return new Integer[]{ arena.getSingle(e).getDefaultTimer() };
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if((Integer) delta[0]>0) {
				arena.getSingle(e).setDefaultTimer((Integer) delta[0], true);
			}
		}
		else if (mode == ChangeMode.ADD) {
			if((Integer) delta[0] + arena.getSingle(e).getDefaultTimer()>0) {
				arena.getSingle(e).setDefaultTimer((Integer) delta[0] + arena.getSingle(e).getDefaultTimer(), true);
			}
		}
		else if (mode == ChangeMode.REMOVE) {
			if((Integer) delta[0] - arena.getSingle(e).getDefaultTimer()>0) {
				arena.getSingle(e).setDefaultTimer((Integer) delta[0] - arena.getSingle(e).getDefaultTimer(), true);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(int.class);
		return null;
	}
}

