package fr.nashoba24.wolvsk.teamspeak;

import java.util.HashMap;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.github.theholywaffle.teamspeak3.api.ClientProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import fr.nashoba24.wolvsk.WolvSK;

public class ExprTSDescription extends SimpleExpression<String>{
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
		return "ts3 description";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(WolvSK.ts3api==null) { return null; }
		Client c = WolvSK.ts3api.getClientByNameExact(client.getSingle(e), true);
		if(c!=null) {
			return new String[]{ c.get(ClientProperty.CLIENT_DESCRIPTION) };
		}
		else {
			return null;
		}
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if(WolvSK.ts3api==null) { return; }
			Client c = WolvSK.ts3api.getClientByNameExact(client.getSingle(e), true);
			if(c!=null) {
				HashMap<ClientProperty, String> map = new HashMap<ClientProperty, String>();
				map.put(ClientProperty.CLIENT_DESCRIPTION, (String) delta[0]);
				WolvSK.ts3api.editClient(c.getId(), map);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}

