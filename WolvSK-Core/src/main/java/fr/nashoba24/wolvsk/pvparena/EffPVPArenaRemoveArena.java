package fr.nashoba24.wolvsk.pvparena;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.managers.ArenaManager;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffPVPArenaRemoveArena extends Effect {
	
	private Expression<Arena> arena;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		arena = (Expression<Arena>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "remove pvparena";
	}
	
	@Override
	protected void execute(Event e) {
		ArenaManager.removeArena(arena.getSingle(e), false);
	}
}
