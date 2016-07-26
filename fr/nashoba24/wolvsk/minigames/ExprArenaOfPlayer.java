package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprArenaOfPlayer extends SimpleExpression<Arena>{
	
	private Expression<Player> p;
	
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
		p = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "arena of player";
	}
	
	@Override
	@Nullable
	protected Arena[] get(Event e) {
		if(p.getSingle(e)!=null) {
			if(Minigames.getMinigame(p.getSingle(e))==null) {
				return null;
			}
		}
		return new Arena[]{ Minigames.getMinigame(p.getSingle(e)).getArena(p.getSingle(e)) };
	}
}

