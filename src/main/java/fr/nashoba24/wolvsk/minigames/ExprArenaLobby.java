package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprArenaLobby extends SimpleExpression<Location>{
	
	private Expression<Arena> arena;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		arena = (Expression<Arena>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "lobby of arena";
	}
	
	@Override
	@Nullable
	protected Location[] get(Event e) {
		if(arena.getSingle(e)!=null) 
		return new Location[]{ arena.getSingle(e).getLobby() };
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			arena.getSingle(e).setLobby((Location) delta[0], true);
		}
		else if(mode == ChangeMode.REMOVE) {
			arena.getSingle(e).setLobby(null, true);
		}
		else if(mode == ChangeMode.RESET) {
			arena.getSingle(e).setLobby(null, true);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET)
			return CollectionUtils.array(Location.class);
		return null;
	}
}

