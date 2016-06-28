package fr.nashoba24.wolvsk.conditions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondASkyBlockIslandAt extends Condition {

    private Expression<Location> loc;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        loc = (Expression<Location>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "island at";
    }

    @Override
    public boolean check(Event e) {
        if(ASkyBlockAPI.getInstance().islandAtLocation(loc.getSingle(e))) {
        	return true;
        }
        else {
        	return false;
        }
    }

}