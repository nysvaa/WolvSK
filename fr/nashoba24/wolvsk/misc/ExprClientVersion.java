package fr.nashoba24.wolvsk.misc;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Protocol;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class ExprClientVersion extends SimpleExpression<Integer> implements Listener {

	public static final Map<InetSocketAddress, Integer> playerVersions = new ConcurrentHashMap<InetSocketAddress, Integer>();
	
	private Expression<Player> player;
	
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
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "client version";
	}
	
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		return new Integer[]{ playerVersions.get(player.getSingle(e).getAddress()) };
	}
	
	public static void registerClientVersion() {
		Bukkit.getPluginManager().registerEvents(new ExprClientVersion(), WolvSK.getInstance());
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(WolvSK.getInstance(),
				ListenerPriority.NORMAL,
				PacketType.Handshake.Client.SET_PROTOCOL) {

			@Override
			public void onPacketReceiving(final PacketEvent event) {
				final PacketContainer packet = event.getPacket();
	            if (event.getPacketType() == PacketType.Handshake.Client.SET_PROTOCOL) {
	            	if (packet.getProtocols().read(0) == Protocol.LOGIN) {
	            		playerVersions.put(event.getPlayer().getAddress(), packet.getIntegers().read(0));
	            	}
	            } else {
	            	playerVersions.remove(event.getPlayer().getAddress());
	            }
			}
		});
	}
	
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        playerVersions.remove(event.getPlayer().getAddress());
    }
}

