package fr.nashoba24.wolvsk.teamspeak;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class ExprTSClients extends SimpleExpression<Client>{
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends Client> getReturnType() {
		return Client.class;
	}
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "ts3 clients";
	}
	
	@Override
	@Nullable
	protected Client[] get(Event e) {
		if(WolvSK.ts3api==null) { return null; }
		List<Client> c = WolvSK.ts3api.getClients();
		Client[] list = new Client[c.size()];
		Integer i = 0;
		for(Client cl : c) {
			list[i] = cl;
			++i;
		}
		return list;
	}
}

