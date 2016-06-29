package fr.nashoba24.wolvsk.supertrails;

import javax.annotation.Nullable;

import me.kvq.plugin.trails.API.SuperTrailsAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffSuperTrailsHideTrails extends Effect {
	
	private Expression<Player> player;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "hide trails of player";
	}
	
	@Override
	protected void execute(Event e) {
		SuperTrailsAPI.HideTrailFor(player.getSingle(e), true);
	}
}
