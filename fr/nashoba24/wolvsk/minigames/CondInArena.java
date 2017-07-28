package fr.nashoba24.wolvsk.minigames;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondInArena extends Condition {

    private Expression<Player> player;
    private Expression<Arena> arena;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        player = (Expression<Player>) expr[0];
        arena = (Expression<Arena>) expr[1];
        setNegated(i == 1);
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "player is in arena";
    }

    @Override
    public boolean check(Event e) {
    	if(arena.getSingle(e)!=null && player.getSingle(e)!=null) {
    		return isNegated() ? !arena.getSingle(e).isInArena(player.getSingle(e)) : arena.getSingle(e).isInArena(player.getSingle(e));
    	}
    	return isNegated();
    }

}