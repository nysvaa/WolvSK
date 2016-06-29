package fr.nashoba24.wolvsk.askyblock;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprASkyBlockIslandWorld extends SimpleExpression<World>{
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends World> getReturnType() {
		return World.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "asb island name of player";
	}
	
	@Override
	@Nullable
	protected World[] get(Event e) {
		return new World[]{ ASkyBlockAPI.getInstance().getIslandWorld() };
	}
}

