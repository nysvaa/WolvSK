package fr.nashoba24.wolvsk.supertrails;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.kvq.plugin.trails.API.SuperTrailsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffSuperTrailsSetWings extends Effect {
	
	private Expression<Player> player;
	private Expression<String> color1;
	private Expression<String> color2;
	private Expression<String> color3;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		color1 = (Expression<String>) expr[1];
		color2 = (Expression<String>) expr[2];
		color3 = (Expression<String>) expr[3];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "set wings of player";
	}
	
	@Override
	protected void execute(Event e) {
		String c1 = color1.getSingle(e);
		String c2 = color2.getSingle(e);
		String c3 = color3.getSingle(e);
		SuperTrailsAPI.SetWings(player.getSingle(e), c1, c2, c3);
	}
}
