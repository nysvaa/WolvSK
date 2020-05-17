package fr.nashoba24.wolvsk.supertrails;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.kvq.plugin.trails.API.SuperTrailsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffSuperTrailsOpenMenu extends Effect {
	
	private Expression<Player> player;
	private Expression<String> menu;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[1];
		menu = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "open menu to player";
	}
	
	@Override
	protected void execute(Event e) {
		SuperTrailsAPI.OpenInventory(player.getSingle(e), menu.getSingle(e));
		//Can be: main, particle, block, rain, builder, wings, modes
	}
}
