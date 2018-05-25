package fr.nashoba24.wolvsk.pvparena;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.managers.ArenaManager;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprPVPArenaArena extends SimpleExpression<Arena>{
	
	private Expression<String> name;
	
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
		name = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "get arena by name";
	}
	
	@Override
	@Nullable
	protected Arena[] get(Event e) {
		return new Arena[]{ ArenaManager.getArenaByName(name.getSingle(e)) };
	}
}

