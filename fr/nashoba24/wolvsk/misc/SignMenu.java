package fr.nashoba24.wolvsk.misc;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;

import fr.nashoba24.wolvsk.WolvSK;

public class SignMenu {
	
	static int currentID = 256;
	static HashMap<Integer, String> signs = new HashMap<Integer, String>();

	public static void open(String name, Player p, String[] lines) {
	    PacketContainer packet = new PacketContainer(PacketType.Play.Server.OPEN_SIGN_EDITOR);
	    packet.getBlockPositionModifier().write(0, new BlockPosition(666, currentID, 42));
	    //PacketContainer update = new PacketContainer(PacketType.Play.Server.UPDATE_SIGN);
	    //update.getStringArrays().write(0, lines);
	    //update.getBlockPositionModifier().write(0, new BlockPosition(666, currentID, 42));
	    try {
	        ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
	        signs.put(currentID, name);
	        ++currentID;
	        //ProtocolLibrary.getProtocolManager().sendServerPacket(p, update);
	    } catch (InvocationTargetException e) {
	        throw new IllegalStateException("Unable to send packet " + packet, e);
	    }
	}
	
	public static void init() {
		Classes.registerClass(new ClassInfo<SignGui>(SignGui.class, "signgui").user("signgui").name("signgui").parser(new Parser<SignGui>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public SignGui parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(SignGui arg0, int arg1) {
				return arg0.getName();
			}

			@Override
			public String toVariableNameString(SignGui arg0) {
				return arg0.getName();
			}
		   
		}));
		Skript.registerEffect(EffOpenSignGui.class, "open [a ]sign[ ]gui named %string% to %player%");
		Skript.registerExpression(ExprLineSignGui.class, String.class, ExpressionType.PROPERTY, "line %integer% of %signgui%");
		Skript.registerExpression(ExprNameSignGui.class, String.class, ExpressionType.PROPERTY, "name of %signgui%");
		Skript.registerEvent("Sign Gui Event", SimpleEvent.class, UpdateSignEvent.class, "sign[ ]gui[ close| done]");
		EventValues.registerEventValue(UpdateSignEvent.class, SignGui.class, new Getter<SignGui, UpdateSignEvent>() {
			public SignGui get(UpdateSignEvent e) {
				return e.getSignGui();
			}
		}, 0);
		EventValues.registerEventValue(UpdateSignEvent.class, Player.class, new Getter<Player, UpdateSignEvent>() {
			public Player get(UpdateSignEvent e) {
				return e.getPlayer();
			}
		}, 0);
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener(new PacketAdapter(WolvSK.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.UPDATE_SIGN) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				final Player player = event.getPlayer();
				if(event.getPacketType() == PacketType.Play.Client.UPDATE_SIGN) {
                    final PacketContainer packet = event.getPacket();
                    final int x = packet.getBlockPositionModifier().readSafely(0).getX();
                    final int id = packet.getBlockPositionModifier().readSafely(0).getY();
                    final int z = packet.getBlockPositionModifier().readSafely(0).getZ();
                    if(x==666 && z==42 && id>255) {
                    	String[] lines = packet.getStringArrays().readSafely(0);
                    	String line1 = lines[0];
                    	String line2 = lines[1];
                    	String line3 = lines[2];
                    	String line4 = lines[3];
                    	String name = signs.get(id);
                    	WolvSK.getInstance().getServer().getPluginManager().callEvent(new UpdateSignEvent(player, new SignGui(new String[] { line1, line2, line3, line4}, id, name)));
                    }
				}
			}
		});
	}
	
}
