package fr.nashoba24.wolvsk.teamspeak;

import java.util.logging.Level;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import fr.nashoba24.wolvsk.WolvSK;

public class EffTSConnect extends Effect {
	
	private Expression<String> host;
	private Expression<String> user;
	private Expression<String> login;
	private Expression<String> password;
	private Expression<Integer> port;
	private boolean debug = false;
	
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
		if(matchedPattern==1) {
			debug = true;
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
			if(debug) {
				config.setDebugLevel(Level.ALL);
			}
			else {
				config.setDebugLevel(Level.OFF);
			}
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
			api.registerAllEvents();
			api.addTS3Listeners(new TS3Listener() {

				@Override
				public void onTextMessage(TextMessageEvent e) {
					return;
				}

				@Override
				public void onServerEdit(ServerEditedEvent e) {
					return;
				}

				@Override
				public void onClientMoved(ClientMovedEvent e) {
					return;
				}

				@Override
				public void onClientLeave(ClientLeaveEvent e) {
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new TsClientLeaveEvent(e.getInvokerName()));
				}

				@Override
				public void onClientJoin(ClientJoinEvent e) {
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new TsClientJoinEvent(api.getClientByNameExact(e.getInvokerName(), false)));
				}

				@Override
				public void onChannelEdit(ChannelEditedEvent e) {
					return;
				}

				@Override
				public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
					return;
				}

				@Override
				public void onChannelCreate(ChannelCreateEvent e) {
					return;
				}

				@Override
				public void onChannelDeleted(ChannelDeletedEvent e) {
					return;
				}

				@Override
				public void onChannelMoved(ChannelMovedEvent e) {
					return;
				}

				@Override
				public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
					return;
				}

				@Override
				public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
					return;
				}
			});
			fr.nashoba24.wolvsk.WolvSK.ts3api = api;
		}
		catch (TS3ConnectionFailedException e1) {
			WolvSK.getInstance().getLogger().warning("Tried to connect to " + host + " but don't succeed");
			fr.nashoba24.wolvsk.WolvSK.ts3query = null;
			fr.nashoba24.wolvsk.WolvSK.ts3api = null;
			return;
		}
	}
}
