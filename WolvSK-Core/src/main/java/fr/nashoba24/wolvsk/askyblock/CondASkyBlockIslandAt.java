package fr.nashoba24.wolvsk.askyblock;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class CondASkyBlockIslandAt extends Condition {

    private Expression<Location> loc;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        loc = (Expression<Location>) expr[0];
        setNegated(i == 1);
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "island at";
    }

    @Override
    public boolean check(Event e) {
    	if(loc.getSingle(e)!=null) {
	        return isNegated() != ASkyBlockAPI.getInstance().islandAtLocation(loc.getSingle(e));
    	}
    	return isNegated();
    }

}