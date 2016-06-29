package fr.nashoba24.wolvsk.teamspeak;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class ExprTSClientID extends SimpleExpression<Integer>{
	private Expression<String> client;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		client = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "ts3 client id";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		if(WolvSK.ts3api==null) { return null; }
		Client c = WolvSK.ts3api.getClientByNameExact(client.getSingle(e), true);
		if(c!=null) {
			return new Integer[]{ c.getId() };
		}
		else {
			return null;
		}
	}
}

