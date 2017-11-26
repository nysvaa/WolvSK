package fr.nashoba24.wolvsk.askyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprASkyBlockHomeLocation extends SimpleExpression<Location>{
	private Expression<Player> player;
	
	@Override
	public boolean isSingle() {
		return true;
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
		return "asb home of player";
	}
	
	@Override
	@Nullable
	protected Location[] get(Event e) {
		if(player.getSingle(e)!=null) {
			return new Location[]{ ASkyBlockAPI.getInstance().getHomeLocation(player.getSingle(e).getUniqueId()) };
		}
		else {
			return null;
		}
	}
}

