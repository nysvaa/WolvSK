package fr.nashoba24.wolvsk.pvparena;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.slipcor.pvparena.api.PVPArenaAPI;
import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.managers.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprPVPArenaPlayerArena extends SimpleExpression<Arena>{
	
	private Expression<Player> player;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Arena> getReturnType() {
		return Arena.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "arena of player";
	}
	
	@Override
	@Nullable
	protected Arena[] get(Event e) {
		String name = PVPArenaAPI.getArenaName(player.getSingle(e));
		if(name == null) {
			return null;
		}
		return new Arena[]{ ArenaManager.getArenaByName(name) };
	}
}

