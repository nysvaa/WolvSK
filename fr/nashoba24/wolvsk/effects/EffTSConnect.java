package fr.nashoba24.wolvsk.effects;

import java.util.logging.Level;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class EffTSConnect extends Effect {
	
	private Expression<String> host;
	private Expression<String> user;
	private Expression<String> login;
	private Expression<String> password;
	private Expression<Integer> port;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		host = (Expression<String>) expr[0];
		user = (Expression<String>) expr[1];
		login = (Expression<String>) expr[2];
		password = (Expression<String>) expr[3];
		if(expr.length==5) {
			port = (Expression<Integer>) expr[4];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "ts3 connect";
	}
	
	@Override
	protected void execute(Event e) {
		try {
			final TS3Config config = new TS3Config();
			config.setHost(host.getSingle(e));
			config.setDebugLevel(Level.OFF);
			if(port.getSingle(e)==1) {
				config.setQueryPort(10011);
			}
			else {
				config.setQueryPort(port.getSingle(e));
			}
			final TS3Query query = new TS3Query(config).connect();
			fr.nashoba24.wolvsk.WolvSK.ts3query = query;
			final TS3Api api = query.getApi();
			api.login(login.getSingle(e), password.getSingle(e));
			api.selectVirtualServerById(1);
			api.setNickname(user.getSingle(e));
			fr.nashoba24.wolvsk.WolvSK.ts3api = api;
		}
		catch (TS3ConnectionFailedException e1) {
			WolvSK.getInstance().getLogger().warning("Tried to connect to " + host + " but don't succeed");
			return;
		}
	}
}
