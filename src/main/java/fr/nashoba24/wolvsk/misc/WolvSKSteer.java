package fr.nashoba24.wolvsk.misc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WolvSKSteer {

	public static void registerSteer() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(WolvSK.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                final Player player = event.getPlayer();
                if(event.getPacketType() == PacketType.Play.Client.STEER_VEHICLE && player.getVehicle() != null) {
                    final PacketContainer packet = event.getPacket();
                    final float sideways = packet.getFloat().readSafely(0);
                    final float forward = packet.getFloat().readSafely(1);
                    final Boolean jump = packet.getBooleans().readSafely(0);
                    if(sideways>0) {
                        Bukkit.getScheduler().runTask(WolvSK.getInstance(), () -> {
                            WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerLeftEvent(player));
                        });
                    	// WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerLeftEvent(player));
                    }
                    else if(sideways<0) {
                        Bukkit.getScheduler().runTask(WolvSK.getInstance(), () -> {
                            WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerRightEvent(player));
                        });
                    	// WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerRightEvent(player));
                    }
                    if(forward>0) {
                        Bukkit.getScheduler().runTask(WolvSK.getInstance(), () -> {
                            WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerForwardEvent(player));
                        });
                    	// WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerForwardEvent(player));
                    }
                    else if(forward<0) {
                        Bukkit.getScheduler().runTask(WolvSK.getInstance(), () -> {
                            WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerBackwardEvent(player));
                        });
                    	// WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerBackwardEvent(player));
                    }
                    if(jump) {
                        Bukkit.getScheduler().runTask(WolvSK.getInstance(), () -> {
                            WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerJumpEvent(player));
                        });
                    	// WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerJumpEvent(player));
                    }
                }
            }
        });
	}
	
}
