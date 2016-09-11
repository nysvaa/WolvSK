package fr.nashoba24.wolvsk.misc;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprLineSignGui extends SimpleExpression<String> implements Listener {
	
	private Expression<SignGui> gui;
	private Expression<Integer> line;
	
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
		gui = (Expression<SignGui>) expr[1];
		line = (Expression<Integer>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "line of sign-gui";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(gui.getSingle(e)==null) {
			return null;
		}
		return new String[]{ gui.getSingle(e).getLine(line.getSingle(e)) };
	}
}

