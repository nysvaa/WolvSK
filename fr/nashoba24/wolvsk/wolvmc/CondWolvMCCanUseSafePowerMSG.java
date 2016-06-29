package fr.nashoba24.wolvsk.wolvmc;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class CondWolvMCCanUseSafePowerMSG extends Condition {

	private Expression<Player> player;
    private Expression<Location> loc;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
    	player = (Expression<Player>) expr[0];
        loc = (Expression<Location>) expr[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "If a player can use a power that don't moves block and send message if not";
    }

    @Override
    public boolean check(Event e) {
        if(WolvMC.canUsePowerSafe(loc.getSingle(e), player.getSingle(e))) {
        	return true;
        }
        else {
        	return false;
        }
    }

}