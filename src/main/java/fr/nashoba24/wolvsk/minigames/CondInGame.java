package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondInGame extends Condition {

    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        player = (Expression<Player>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "player is in an arena";
    }

    @Override
    public boolean check(Event e) {
    	return isNegated() ? !Minigames.inGame(player.getSingle(e)) : Minigames.inGame(player.getSingle(e));
    }

}