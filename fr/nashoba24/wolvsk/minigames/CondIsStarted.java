package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsStarted extends Condition {

    private Expression<Arena> arena;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        arena = (Expression<Arena>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "arena is started";
    }

    @Override
    public boolean check(Event e) {
    	if(arena.getSingle(e)!=null) {
    		return arena.getSingle(e).isStarted();
    	}
    	return false;
    }

}