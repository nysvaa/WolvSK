package fr.nashoba24.wolvsk.essentials;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.nashoba24.wolvsk.WolvSK;
import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.UserBalanceUpdateEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WolvSKEssentials {

	public static void register() {
		   if (WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials") != null) {
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
			   Skript.registerCondition(CondEssentialsAFK.class, "%player% is afk", "%player% is(n't| not) afk");
			   Skript.registerCondition(CondEssentialsCanBuild.class, "essentials %player% can build", "essentials %player% cannot build");
			   Skript.registerCondition(CondEssentialsCanInteractVanish.class, "%player% can interact vanish", "%player% cannot interact vanish");
			   Skript.registerCondition(CondEssentialsHasHome.class, "%player% ha(s|ve)[ a[n]] home", "%player% does(n't| not) ha(s|ve)[ a[n]] home", "%player% ha(s|ve)(n't| not)[ a[n]] home");
			   Skript.registerCondition(CondEssentialsHasPowertools.class, "%player% ha(s|ve) powertool[s]", "%player% does(n't| not) ha(s|ve) powertool[s]", "%player% ha(s|ve)(n't| not) powertool[s]");
			   Skript.registerCondition(CondEssentialsIgnore.class, "%player% (ignore|is ignoring) %player%", "%player% does(n't| not) ignore %player%");
			   Skript.registerCondition(CondEssentialsIsJailed.class, "%player% is jail[ed]", "%player% is(n't| not) jail[ed]");
			   Skript.registerCondition(CondEssentialsIsMuted.class, "%player% is mute[d]", "%player% is(n't| not) muted");
			   Skript.registerCondition(CondEssentialsPowerToolsEnabled.class, "%player% ha(s|ve) powertool[s] enable[d]", "%player% does(n't| not) ha(s|ve) powertool[s] enable[d]", "%player% ha(s|ve)(n't| not) powertool[s] enable[d]");
			   Skript.registerCondition(CondEssentialsSocialSpyEnabled.class, "%player% ha(s|ve) social spy[ enable[d]]", "%player% does(n't| not) ha(s|ve) social spy[ enable[d]]", "%player% ha(s|ve)(n't| not) social spy[ enable[d]]");
			   Skript.registerCondition(CondEssentialsGodMode.class, "%player% is[ in] god[ mode]", "%player% is(n't| not)[ in] god[ mode]");
			   Skript.registerCondition(CondEssentialsVanish.class, "%player% is vanish[ed]", "%player% is(n't| not) vanish[ed]");
			   Skript.registerExpression(ExprEssentialsHomes.class, String.class, ExpressionType.PROPERTY, "homes of %player%", "%player%['s] homes");
			   Skript.registerExpression(ExprEssentialsHome.class, Location.class, ExpressionType.PROPERTY, "home %string% of %player%", "%player%['s] home %string%");
			   Skript.registerExpression(ExprEssentialsHome.class, Location.class, ExpressionType.PROPERTY, "home of %player%", "%player%['s] home");
			   Skript.registerEffect(EffEssentialsDelHome.class, "delete home %string% of %player%");
			   Skript.registerEffect(EffEssentialsMakePlayerBecomeAFK.class, "make %player% become afk", "make %player% become (no afk|active)");
			   Skript.registerEffect(EffEssentialsMakePlayerGod.class, "make %player% become god", "disable god[ mode] for %player%");
			   Skript.registerEffect(EffEssentialsMakePlayerIgnore.class, "make %player% ignore %player%", "make %player% (doesn't ignore|unignore) %player%");
			   Skript.registerEffect(EffEssentialsMakePlayerJailed.class, "jail %player%", "unjail %player%");
			   Skript.registerEffect(EffEssentialsMakePlayerMuted.class, "mute %player%", "unmute %player%");
			   Skript.registerEffect(EffEssentialsMakeSocialSpy.class, "enable social spy for %player%", "disable social spy for %player%");
			   Skript.registerEffect(EffEssentialsMakeVanish.class, "vanish %player%", "unvanish %player%");
			   Skript.registerEffect(EffEssentialsSendMail.class, "send mail %string% to %player%");
			   Skript.registerExpression(ExprEssentialsJail.class, String.class, ExpressionType.PROPERTY, "jail of %player%", "%player%['s] jail");
			   Skript.registerExpression(ExprEssentialsJailTimeout.class, Long.class, ExpressionType.PROPERTY, "jail timeout of %player%", "%player%['s] jail timeout");
			   Skript.registerExpression(ExprEssentialsLastAccountName.class, String.class, ExpressionType.PROPERTY, "last account name of %player%", "%player%['s] last account name");
			   Skript.registerExpression(ExprEssentialsLastLoginAddress.class, String.class, ExpressionType.PROPERTY, "last login address of %player%", "%player%['s] last login address");
			   Skript.registerExpression(ExprEssentialsMuteTimeout.class, Long.class, ExpressionType.PROPERTY, "mute timeout of %player%", "%player%['s] mute timeout");
			   Skript.registerExpression(ExprEssentialsNickname.class, String.class, ExpressionType.PROPERTY, "essential[s] nick[ ]name %player%", "%player%['s] essential[s] nick[ ]name");
			   Skript.registerExpression(ExprEssentialsLogoutLocation.class, Location.class, ExpressionType.PROPERTY, "log[ ]out[ location] of %player%", "%player%['s] log[ ]out[ location]");
		   }
	}
	
}
