package fr.nashoba24.wolvsk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.nashoba24.wolvmc.WolvMC;
import fr.nashoba24.wolvmc.events.WolvMCInitEffectsEvent;
import fr.nashoba24.wolvmc.events.WolvMCReloadEvent;
import fr.nashoba24.wolvsk.conditions.CondCanUsePowerBlock;
import fr.nashoba24.wolvsk.conditions.CondCanUsePowerBlockMSG;
import fr.nashoba24.wolvsk.conditions.CondCanUseSafePower;
import fr.nashoba24.wolvsk.conditions.CondCanUseSafePowerMSG;
import fr.nashoba24.wolvsk.conditions.CondFinishMission;
import fr.nashoba24.wolvsk.conditions.CondRaceExist;
import fr.nashoba24.wolvsk.effects.EffCreateMission;
import fr.nashoba24.wolvsk.effects.EffCreateRace;
import fr.nashoba24.wolvsk.effects.EffOpenChangeInv;
import fr.nashoba24.wolvsk.effects.EffOpenChooseInv;
import fr.nashoba24.wolvsk.effects.EffSaveData;
import fr.nashoba24.wolvsk.expressions.ExprAllRaces;
import fr.nashoba24.wolvsk.expressions.ExprChanges;
import fr.nashoba24.wolvsk.expressions.ExprCustomPrefix;
import fr.nashoba24.wolvsk.expressions.ExprDescriptionMission;
import fr.nashoba24.wolvsk.expressions.ExprFinishMsgMission;
import fr.nashoba24.wolvsk.expressions.ExprGoalMission;
import fr.nashoba24.wolvsk.expressions.ExprMissionOfPlayer;
import fr.nashoba24.wolvsk.expressions.ExprMissionRace;
import fr.nashoba24.wolvsk.expressions.ExprMissionsRace;
import fr.nashoba24.wolvsk.expressions.ExprMsgCooldown;
import fr.nashoba24.wolvsk.expressions.ExprNameOfBlock;
import fr.nashoba24.wolvsk.expressions.ExprPlayTime;
import fr.nashoba24.wolvsk.expressions.ExprPrefixRace;
import fr.nashoba24.wolvsk.expressions.ExprRaceOfPlayer;

public class Main extends JavaPlugin implements Listener {
	  
	  @Override
	  public void onDisable()
	  {
	    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&bWolvSK Disabled!"));
	  }
	  
	  @Override
	  public void onEnable()
	  {
		   Bukkit.getPluginManager().registerEvents(new ExprNameOfBlock(), this);
		   Skript.registerAddon(this);
		   Skript.registerExpression(ExprNameOfBlock.class, String.class, ExpressionType.PROPERTY, "name of %block%", "%block%['s] name");
		   Plugin plugin = WolvMC.getPlugin(WolvMC.class).getServer().getPluginManager().getPlugin("WolvMC");
		   if (plugin != null && plugin instanceof WolvMC) {
				  Skript.registerEvent("WolvMC Reload", SimpleEvent.class, WolvMCReloadEvent.class, "wolvmc reload");
				  Skript.registerEvent("WolvMC Init Effects", SimpleEvent.class, WolvMCInitEffectsEvent.class, "wolvmc init effect[s]"); //TODO getPlayer
				  EventValues.registerEventValue(WolvMCInitEffectsEvent.class, Player.class, new Getter<Player, WolvMCInitEffectsEvent>() {
					    public Player get(WolvMCInitEffectsEvent e) {
					        return e.getPlayer();
					    }
					}, 0);
				  Skript.registerExpression(ExprMissionOfPlayer.class, Double.class, ExpressionType.PROPERTY, "mission %string% of %player%");
				  Skript.registerExpression(ExprRaceOfPlayer.class, String.class, ExpressionType.PROPERTY, "race of %player%", "%player%['s] race");
				  Skript.registerExpression(ExprPrefixRace.class, String.class, ExpressionType.PROPERTY, "prefix of race %string%");
				  Skript.registerExpression(ExprPlayTime.class, Integer.class, ExpressionType.PROPERTY, "play[ ]time of %player%", "%player%['s] play[ ]time");
				  Skript.registerExpression(ExprMissionsRace.class, String.class, ExpressionType.PROPERTY, "missions of race %string%");
				  Skript.registerExpression(ExprGoalMission.class, Double.class, ExpressionType.PROPERTY, "goal of [mission ]%string%");
				  Skript.registerExpression(ExprDescriptionMission.class, String.class, ExpressionType.PROPERTY, "description of [mission ]%string%");
				  Skript.registerExpression(ExprFinishMsgMission.class, String.class, ExpressionType.PROPERTY, "finish (message|msg) of [mission ]%string%");
				  Skript.registerExpression(ExprMissionRace.class, String.class, ExpressionType.PROPERTY, "race for [mission ]%string%");
				  Skript.registerExpression(ExprChanges.class, Integer.class, ExpressionType.PROPERTY, "change[s][ left] of %player%", "%player%['s] change[s][ left]");
				  Skript.registerExpression(ExprAllRaces.class, String.class, ExpressionType.PROPERTY, "all race[s]");
				  Skript.registerExpression(ExprCustomPrefix.class, String.class, ExpressionType.PROPERTY, "custom prefix of %player%", "%player%'s custom prefix", "cprefix of %player%", "%player%'s cprefix");
				  Skript.registerExpression(ExprMsgCooldown.class, String.class, ExpressionType.PROPERTY, "cooldown (message|msg) for %integer% sec[ond][s]"); 
				  Skript.registerCondition(CondCanUsePowerBlock.class, "%player% can use block power at %location%");
				  Skript.registerCondition(CondCanUseSafePower.class, "%player% can use safe power at %location%");
				  Skript.registerCondition(CondCanUsePowerBlockMSG.class, "%player% can use block power at %location% and send message if not");
				  Skript.registerCondition(CondCanUseSafePowerMSG.class, "%player% can use safe power at %location% and send message if not");
				  Skript.registerCondition(CondRaceExist.class, "race %string% exist");
				  Skript.registerCondition(CondFinishMission.class, "%player% has finish mission %string%");
				  Skript.registerEffect(EffCreateMission.class, "create mission %string% for race %string%[,] with goal %number%,[ with] description %string%( and with| ,) finish (message|msg) %string%");
				  Skript.registerEffect(EffCreateRace.class, "create race %string% with prefix %string%[ and][ choose] item %itemstack%");
				  Skript.registerEffect(EffOpenChangeInv.class, "open change inv[entory] to %player%");
				  Skript.registerEffect(EffOpenChooseInv.class, "open choose inv[entory] to %player%");
				  Skript.registerEffect(EffSaveData.class, "save data[s] of %player%");
				  Skript.registerEffect(EffSaveData.class, "wolvmc reload");
		   }
		  Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aWolvSK Enabled!"));
	  }
	  
	  public static Plugin getPlugin() {
		  return fr.nashoba24.wolvsk.Main.getPlugin(fr.nashoba24.wolvsk.Main.class);
	  }
}
