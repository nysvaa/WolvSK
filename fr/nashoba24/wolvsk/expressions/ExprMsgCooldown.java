package fr.nashoba24.wolvsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class ExprMsgCooldown extends SimpleExpression<String>{
	private Expression<Integer> sec;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		sec = (Expression<Integer>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "cooldown message";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{ WolvMC.msgCooldown(sec.getSingle(e)) };
	}
}

