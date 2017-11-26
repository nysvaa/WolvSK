package fr.nashoba24.wolvsk.wolvmc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.nashoba24.wolvmc.events.WolvMCInitEffectsEvent;
import fr.nashoba24.wolvmc.events.WolvMCReloadEvent;

public class WolvSKWolvMC {

	public static void register() {
		   if (Bukkit.getServer().getPluginManager().getPlugin("WolvMC") != null) {
				  Skript.registerEvent("WolvMC Reload", SimpleEvent.class, WolvMCReloadEvent.class, "wolvmc reload");
				  Skript.registerEvent("WolvMC Init Effects", SimpleEvent.class, WolvMCInitEffectsEvent.class, "wolvmc init effect[s]");
				  EventValues.registerEventValue(WolvMCInitEffectsEvent.class, Player.class, new Getter<Player, WolvMCInitEffectsEvent>() {
					    public Player get(WolvMCInitEffectsEvent e) {
					        return e.getPlayer();
					    }
					}, 0);
				  Skript.registerExpression(ExprWolvMCMissionOfPlayer.class, Double.class, ExpressionType.PROPERTY, "mission %string% of %player%");
				  Skript.registerExpression(ExprWolvMCRaceOfPlayer.class, String.class, ExpressionType.PROPERTY, "race of %player%", "%player%['s] race");
				  Skript.registerExpression(ExprWolvMCPrefixRace.class, String.class, ExpressionType.PROPERTY, "prefix of race %string%");
				  Skript.registerExpression(ExprWolvMCPlayTime.class, Integer.class, ExpressionType.PROPERTY, "play[ ]time of %player%", "%player%['s] play[ ]time");
				  Skript.registerExpression(ExprWolvMCMissionsRace.class, String.class, ExpressionType.PROPERTY, "missions of race %string%");
				  Skript.registerExpression(ExprWolvMCGoalMission.class, Double.class, ExpressionType.PROPERTY, "goal of [mission ]%string%");
				  Skript.registerExpression(ExprWolvMCDescriptionMission.class, String.class, ExpressionType.PROPERTY, "description of [mission ]%string%");
				  Skript.registerExpression(ExprWolvMCFinishMsgMission.class, String.class, ExpressionType.PROPERTY, "finish (message|msg) of [mission ]%string%");
				  Skript.registerExpression(ExprWolvMCMissionRace.class, String.class, ExpressionType.PROPERTY, "race for [mission ]%string%");
				  Skript.registerExpression(ExprWolvMCChanges.class, Integer.class, ExpressionType.PROPERTY, "change[s][ left] of %player%", "%player%['s] change[s][ left]");
				  Skript.registerExpression(ExprWolvMCAllRaces.class, String.class, ExpressionType.PROPERTY, "all race[s]");
				  Skript.registerExpression(ExprWolvMCCustomPrefix.class, String.class, ExpressionType.PROPERTY, "custom prefix of %player%", "%player%['s] custom prefix", "cprefix of %player%", "%player%['s] cprefix");
				  Skript.registerExpression(ExprWolvMCMsgCooldown.class, String.class, ExpressionType.PROPERTY, "cooldown (message|msg) for %integer% sec[ond][s]");
				  Skript.registerCondition(CondWolvMCCanUsePowerBlock.class, "%player% can use block power at %location%");
				  Skript.registerCondition(CondWolvMCCanUseSafePower.class, "%player% can use safe power at %location%");
				  Skript.registerCondition(CondWolvMCCanUsePowerBlockMSG.class, "%player% can use block power at %location% and send message if not");
				  Skript.registerCondition(CondWolvMCCanUseSafePowerMSG.class, "%player% can use safe power at %location% and send message if not");
				  Skript.registerCondition(CondWolvMCRaceExist.class, "race %string% exist[s]");
				  Skript.registerCondition(CondWolvMCFinishMission.class, "%player% has finish mission %string%");
				  Skript.registerEffect(EffWolvMCCreateMission.class, "create mission %string% for race %string%[,] with goal %number%,[ with] description %string%( and with| ,) finish (message|msg) %string%");
				  Skript.registerEffect(EffWolvMCCreateRace.class, "create race %string% with prefix %string%[ and][ choose] item %itemstack%");
				  Skript.registerEffect(EffWolvMCOpenChangeInv.class, "open change inv[entory] to %player%");
				  Skript.registerEffect(EffWolvMCOpenChooseInv.class, "open choose inv[entory] to %player%");
				  Skript.registerEffect(EffWolvMCSaveData.class, "save data[s] of %player%");
				  Skript.registerEffect(EffWolvMCSaveData.class, "wolvmc reload");

		   }
	}

}
