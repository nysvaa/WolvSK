package fr.nashoba24.wolvsk.askyblock;

import java.util.Set;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprASkyBlockCoopIslands extends SimpleExpression<Location>{
	private Expression<Player> player;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "asb coop islands";
	}
	
	@Override
	@Nullable
	protected Location[] get(Event e) {
		Set<Location> list = ASkyBlockAPI.getInstance().getCoopIslands(player.getSingle(e));
		Location[] locs = new Location[list.size()];
		Integer i = 0;
		for(Location l : list) {
			locs[i] = l;
			++i;
		}
		return locs;
	}
}

