package fr.nashoba24.wolvsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprASkyBlockIslandLocation extends SimpleExpression<Location>{
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
		return "asb island location of player";
	}
	
	@Override
	@Nullable
	protected Location[] get(Event e) {
		return new Location[]{ ASkyBlockAPI.getInstance().getIslandLocation(player.getSingle(e).getUniqueId()) };
	}
}

