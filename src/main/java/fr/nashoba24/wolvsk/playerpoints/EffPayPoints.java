package fr.nashoba24.wolvsk.playerpoints;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffPayPoints extends Effect {
	
	private Expression<Integer> points;
	private Expression<Player> player;
	private Expression<Player> player2;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		points = (Expression<Integer>) expr[1];
		player = (Expression<Player>) expr[0];
		player2 = (Expression<Player>) expr[2];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "pay player";
	}
	
	@Override
	protected void execute(Event e) {
		PlayerPoints.getPlugin(PlayerPoints.class).getAPI().pay(player.getSingle(e).getUniqueId(), player2.getSingle(e).getUniqueId(), points.getSingle(e));
	}
}
