package fr.nashoba24.wolvsk.misc;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitScheduler;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import ch.njol.skript.util.Timespan;
import fr.nashoba24.wolvsk.WolvSK;
import fr.nashoba24.wolvsk.misc.anvilgui.WolvSKAnvilGUI;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_10_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_11_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_12_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_8_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_8_R2;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_8_R3;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_9_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffSpectate1_9_R2;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_10_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_11_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_12_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_8_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_8_R2;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_8_R3;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_9_R1;
import fr.nashoba24.wolvsk.misc.spectate.EffUnspectate1_9_R2;

public class WolvSKMisc implements Listener {
	
	public static HashMap<String, Integer> lastDeath = new HashMap<String, Integer>();
	public static String[] insults = new String[]{"srx", "ptn", "fdp", "ntm", "wtf", "pd"};

	public static void registerAll() {
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
		   Skript.registerEvent("Rage Event", SimpleEvent.class, PlayerRageEvent.class, "[player ]rage");
		   WolvSKAnvilGUI.registerAll();
		   String version = WolvSK.getInstance().getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		   switch(version) {
		   		case "v1_8_R1":
		   			Skript.registerEffect(EffSpectate1_8_R1.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_8_R1.class, "wolvsk make %player% stop spectating");
		   			break;
		   		case "v1_8_R2":
		   			Skript.registerEffect(EffSpectate1_8_R2.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_8_R2.class, "wolvsk make %player% stop spectating");
		   			break;
		   		case "v1_8_R3":
		   			Skript.registerEffect(EffSpectate1_8_R3.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_8_R3.class, "wolvsk make %player% stop spectating");
		   			break;
		   		case "v1_9_R1":
		   			Skript.registerEffect(EffSpectate1_9_R1.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_9_R1.class, "wolvsk make %player% stop spectating");
		   			break;
		   		case "v1_9_R2":
		   			Skript.registerEffect(EffSpectate1_9_R2.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_9_R2.class, "wolvsk make %player% stop spectating");
		   			break;
		   		case "v1_10_R1":
		   			Skript.registerEffect(EffSpectate1_10_R1.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_10_R1.class, "wolvsk make %player% stop spectating");
		   			break;
		   		case "v1_11_R1":
		   			Skript.registerEffect(EffSpectate1_11_R1.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_11_R1.class, "wolvsk make %player% stop spectating");
		   			break;
		   		case "v1_12_R1":
		   			Skript.registerEffect(EffSpectate1_12_R1.class, "wolvsk make %player% spectate %entity%");
		   			Skript.registerEffect(EffUnspectate1_12_R1.class, "wolvsk make %player% stop spectating");
		   			break;
		   }
		   /////////////////////
		   //Skript.registerEffect(EffMakeEntityGlow.class, "make %entity% glow for %players%");//TODO
		   /////////////////////
		   EventValues.registerEventValue(PlayerRageEvent.class, Player.class, new Getter<Player, PlayerRageEvent>() {
			   public Player get(PlayerRageEvent e) {
				   return e.getPlayer();
			   }
		   }, 0);
		   if(Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			   WolvSKSteer.registerSteer();
			   ExprClientVersion.registerClientVersion();
			   Skript.registerExpression(ExprClientVersion.class, Integer.class, ExpressionType.PROPERTY, "(minecraft|mc) version of %player%");
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
	
	@EventHandler
	public void onRage(AsyncPlayerChatEvent e) {
		if(lastDeath.containsKey(e.getPlayer().getName())) {
			for(String s : insults) {
				if(e.getMessage().contains(s)) {
					WolvSK.getInstance().getServer().getPluginManager().callEvent(new PlayerRageEvent(e.getPlayer()));
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(final PlayerDeathEvent e) {
		if(lastDeath.containsKey(e.getEntity().getName())) {
			Bukkit.getServer().getScheduler().cancelTask(lastDeath.get(e.getEntity().getName()));
		}
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		int t = scheduler.scheduleSyncDelayedTask(WolvSK.getPlugin(WolvSK.class), new Runnable() {
			@Override
			public void run() {
				WolvSKMisc.lastDeath.remove(e.getEntity().getName());
			}
		}, 20 * 15L);
		WolvSKMisc.lastDeath.put(e.getEntity().getName(), t);
	}
}
