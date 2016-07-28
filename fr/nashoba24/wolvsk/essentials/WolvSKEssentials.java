package fr.nashoba24.wolvsk.essentials;

import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.UserBalanceUpdateEvent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

public class WolvSKEssentials {

	public static void registerAll() {
		   if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null) {
			   Skript.registerEvent("User Balance Update", SimpleEvent.class, UserBalanceUpdateEvent.class, "[user ](balance|money) (update|change)");
			   EventValues.registerEventValue(UserBalanceUpdateEvent.class, Player.class, new Getter<Player, UserBalanceUpdateEvent>() {
				   public Player get(UserBalanceUpdateEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(UserBalanceUpdateEvent.class, Double.class, new Getter<Double, UserBalanceUpdateEvent>() {
				   public Double get(UserBalanceUpdateEvent e) {
					   return e.getOldBalance().doubleValue();
				   }
			   }, 0);
			   Skript.registerEvent("AFK Toggle", SimpleEvent.class, AfkStatusChangeEvent.class, "afk[ status] (change|toggle)");
			   EventValues.registerEventValue(AfkStatusChangeEvent.class, Player.class, new Getter<Player, AfkStatusChangeEvent>() {
				   public Player get(AfkStatusChangeEvent e) {
					   return e.getAffected().getBase();
				   }
			   }, 0);
			   Skript.registerCondition(CondEssentialsAFK.class, "%player% is afk");
			   Skript.registerCondition(CondEssentialsGodMode.class, "%player% is[ in] god[ mode]");
			   Skript.registerCondition(CondEssentialsVanish.class, "%player% is vanish[ed]"); //Ajouter support SuperVanish
			   Skript.registerExpression(ExprEssentialsHomes.class, String.class, ExpressionType.PROPERTY, "homes of %player%", "%player%['s] homes");
			   Skript.registerExpression(ExprEssentialsHome.class, Location.class, ExpressionType.PROPERTY, "home %string% of %player%", "%player%['s] home %string%");
			   Skript.registerExpression(ExprEssentialsHome.class, Location.class, ExpressionType.PROPERTY, "home of %player%", "%player%['s] home");
			   Skript.registerExpression(ExprEssentialsLogoutLocation.class, Location.class, ExpressionType.PROPERTY, "log[ ]out[ location] of %player%", "%player%['s] log[ ]out[ location]");
		   }
	}
	
}
