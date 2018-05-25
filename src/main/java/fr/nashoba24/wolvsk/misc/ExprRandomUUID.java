package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.UUID;

public class ExprRandomUUID extends SimpleExpression<UUID> {
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends UUID> getReturnType() {
		return UUID.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "random uuid";
	}
	
	@Override
	@Nullable
	protected UUID[] get(Event e) {
		return new UUID[] { UUID.randomUUID() };
	}
}

