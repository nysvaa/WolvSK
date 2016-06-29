package fr.nashoba24.wolvsk.teamspeak;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class ExprTSIP extends SimpleExpression<String>{
	private Expression<String> client;
	
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
		client = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "ts3 ip";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(WolvSK.ts3api==null) { return null; }
		Client c = WolvSK.ts3api.getClientByNameExact(client.getSingle(e), true);
		if(c!=null) {
			return new String[]{ c.getIp() };
		}
		else {
			return null;
		}
	}
}

