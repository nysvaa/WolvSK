package fr.nashoba24.wolvsk.conditions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class CondCanUsePowerBlock extends Condition {


    private Expression<Location> loc;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        loc = (Expression<Location>) expr[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "If a player can use a power that moves block";
    }

    @Override
    public boolean check(Event e) {
        if(WolvMC.canUsePowerBlock(loc.getSingle(e))) {
        	return true;
        }
        else {
        	return false;
        }
    }

}