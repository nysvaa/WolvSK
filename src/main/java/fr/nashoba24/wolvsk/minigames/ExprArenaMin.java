package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprArenaMin extends SimpleExpression<Integer>{
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
		return "min of arena";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if(arena.getSingle(e)!=null) 
		return new Integer[]{ arena.getSingle(e).getMin() };
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if(arena.getSingle(e).getMax()>=(Integer) delta[0]) {
				arena.getSingle(e).setMin((Integer) delta[0], true);
			}
		}
		else if (mode == ChangeMode.ADD) {
			if(arena.getSingle(e).getMax()>=(Integer) delta[0] + arena.getSingle(e).getMin()) {
				arena.getSingle(e).setMin((Integer) delta[0] + arena.getSingle(e).getMin(), true);
			}
		}
		else if (mode == ChangeMode.REMOVE) {
			if(arena.getSingle(e).getMax()>=(Integer) delta[0] - arena.getSingle(e).getMin()) {
				arena.getSingle(e).setMin((Integer) delta[0] - arena.getSingle(e).getMin(), true);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(int.class);	
		return null;
	}
}

