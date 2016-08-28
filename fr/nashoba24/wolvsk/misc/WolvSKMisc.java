package fr.nashoba24.wolvsk.misc;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import fr.nashoba24.wolvsk.WolvSK;

public class WolvSKMisc {

	public static void registerAll() {
		   Skript.registerExpression(ExprNameOfBlock.class, String.class, ExpressionType.PROPERTY, "name of %block%", "%block%['s] name");
		   Skript.registerExpression(ExprBlockPower.class, Integer.class, ExpressionType.PROPERTY, "power of %block%", "%block%['s] power");
		   Skript.registerCondition(CondOdd.class, "%number% is odd");
		   Skript.registerCondition(CondEven.class, "%number% is even");
		   Skript.registerCondition(CondCooldownFinish.class, "cooldown %string% is finish", "cooldown %string% of %player% is finish");
		   Skript.registerEffect(EffCreateCooldown.class, "create cooldown %string% for %timespan%", "create cooldown %string% (for|of) %player% for %timespan%");
		   Skript.registerExpression(ExprCooldownLeftTime.class, Timespan.class, ExpressionType.PROPERTY, "cooldown[ left][ time] %string%", "cooldown[ left][ time] %string% of %player%");
		   Skript.registerExpression(ExprCountry.class, String.class, ExpressionType.PROPERTY, "country of ip %string%", "country of %player%", "country code of ip %string%", "country code of %player%", "ip %string%['s] country", "%player%['s] country", "ip %string%['s] country code", "%player%['s] country code");
		   Skript.registerExpression(ExprRandomUUID.class, UUID.class, ExpressionType.PROPERTY, "[a ]random uuid");
		   Skript.registerExpression(ExprRandomLicenceCode.class, String.class, ExpressionType.PROPERTY, "[a ]random license code");
		   Skript.registerExpression(ExprRandomAlphaNumericString.class, String.class, ExpressionType.PROPERTY, "[a ]random alpha[ ]numeric[al] (text|string) of length %integer%");
		   Skript.registerExpression(ExprListSort.class, Object.class, ExpressionType.PROPERTY, "%objects% (sorted|in order)");
		   Skript.registerExpression(ExprReturnOfMethodWithParams.class, Object.class, ExpressionType.PROPERTY, "return of (function|method) %string% with param[meter][s] %objects% in class[ named] %string%");
		   Skript.registerExpression(ExprReturnOfMethodWithoutParams.class, Object.class, ExpressionType.PROPERTY, "return of (function|method) %string% [without param[meter][s]] in class[ named] %string%");
		   Skript.registerEffect(EffCallMethodWithParams.class, "call (function|method) %string% with param[meter][s] %objects% in class[ named] %string%");
		   Skript.registerEffect(EffCallMethodWithoutParams.class, "call (function|method) %string% [without param[meter][s]] in class[ named] %string%");
		   if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			   registerSteer();
			   Skript.registerEvent("Vehicle Steer Left", SimpleEvent.class, SteerLeftEvent.class, "vehicle steer left");
			   EventValues.registerEventValue(SteerLeftEvent.class, Player.class, new Getter<Player, SteerLeftEvent>() {
				   public Player get(SteerLeftEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   Skript.registerEvent("Vehicle Steer Right", SimpleEvent.class, SteerRightEvent.class, "vehicle steer right");
			   EventValues.registerEventValue(SteerRightEvent.class, Player.class, new Getter<Player, SteerRightEvent>() {
				   public Player get(SteerRightEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   Skript.registerEvent("Vehicle Steer Forward", SimpleEvent.class, SteerForwardEvent.class, "vehicle steer forward");
			   EventValues.registerEventValue(SteerForwardEvent.class, Player.class, new Getter<Player, SteerForwardEvent>() {
				   public Player get(SteerForwardEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   Skript.registerEvent("Vehicle Steer Backward", SimpleEvent.class, SteerBackwardEvent.class, "vehicle steer backward");
			   EventValues.registerEventValue(SteerBackwardEvent.class, Player.class, new Getter<Player, SteerBackwardEvent>() {
				   public Player get(SteerBackwardEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   Skript.registerEvent("Vehicle Steer Jump", SimpleEvent.class, SteerJumpEvent.class, "vehicle steer jump");
			   EventValues.registerEventValue(SteerJumpEvent.class, Player.class, new Getter<Player, SteerJumpEvent>() {
				   public Player get(SteerJumpEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
		   }
	}
	
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
                    	WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerLeftEvent(player));
                    }
                    else if(sideways<0) {
                    	WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerRightEvent(player));
                    }
                    if(forward>0) {
                    	WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerForwardEvent(player));
                    }
                    else if(forward<0) {
                    	WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerBackwardEvent(player));
                    }
                    if(jump) {
                    	WolvSK.getInstance().getServer().getPluginManager().callEvent(new SteerJumpEvent(player));
                    }
                }
            }
        });
	}
}
