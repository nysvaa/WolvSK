package fr.nashoba24.wolvsk.playerpoints;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprPoints extends SimpleExpression<Integer>{
	
	private Expression<Player> player;
	
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
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "points of player";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{ PlayerPoints.getPlugin(PlayerPoints.class).getAPI().look(player.getSingle(e).getUniqueId()) };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			PlayerPoints.getPlugin(PlayerPoints.class).getAPI().set(player.getSingle(e).getUniqueId(), ((Number) delta[0]).intValue());
		}
		else if (mode == ChangeMode.RESET) {
			PlayerPoints.getPlugin(PlayerPoints.class).getAPI().reset(player.getSingle(e).getUniqueId());
		}
		else if(mode == ChangeMode.ADD) {
			PlayerPoints.getPlugin(PlayerPoints.class).getAPI().give(player.getSingle(e).getUniqueId(), ((Number) delta[0]).intValue());
		}
		else if(mode == ChangeMode.REMOVE) {
			PlayerPoints.getPlugin(PlayerPoints.class).getAPI().take(player.getSingle(e).getUniqueId(), ((Number) delta[0]).intValue());
		}
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}
}

