package fr.nashoba24.wolvsk.misc;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffOpenSignGui extends Effect {
	
	private Expression<Player> player;
	private Expression<String> name;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[1];
		name = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "open sign gui";
	}
	
	@Override
	protected void execute(Event e) {
		SignMenu.open(name.getSingle(e), player.getSingle(e), null);
	}
}
