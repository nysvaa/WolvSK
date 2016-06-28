package fr.nashoba24.wolvsk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.wasteofplastic.askyblock.events.CoopJoinEvent;
import com.wasteofplastic.askyblock.events.CoopLeaveEvent;
import com.wasteofplastic.askyblock.events.IslandEnterEvent;
import com.wasteofplastic.askyblock.events.IslandLeaveEvent;
import com.wasteofplastic.askyblock.events.IslandLevelEvent;
import com.wasteofplastic.askyblock.events.IslandNewEvent;
import com.wasteofplastic.askyblock.events.IslandResetEvent;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import fr.nashoba24.wolvmc.events.WolvMCInitEffectsEvent;
import fr.nashoba24.wolvmc.events.WolvMCReloadEvent;
import fr.nashoba24.wolvsk.conditions.CondASkyBlockHasIsland;
import fr.nashoba24.wolvsk.conditions.CondASkyBlockHasTeam;
import fr.nashoba24.wolvsk.conditions.CondASkyBlockIsCoop;
import fr.nashoba24.wolvsk.conditions.CondASkyBlockIslandAt;
import fr.nashoba24.wolvsk.conditions.CondWolvMCCanUsePowerBlock;
import fr.nashoba24.wolvsk.conditions.CondWolvMCCanUsePowerBlockMSG;
import fr.nashoba24.wolvsk.conditions.CondWolvMCCanUseSafePower;
import fr.nashoba24.wolvsk.conditions.CondWolvMCCanUseSafePowerMSG;
import fr.nashoba24.wolvsk.conditions.CondWolvMCFinishMission;
import fr.nashoba24.wolvsk.conditions.CondWolvMCRaceExist;
import fr.nashoba24.wolvsk.effects.EffASkyBlockCalculateLevel;
import fr.nashoba24.wolvsk.effects.EffTSAddToGroup;
import fr.nashoba24.wolvsk.effects.EffTSBan;
import fr.nashoba24.wolvsk.effects.EffTSBanTemporary;
import fr.nashoba24.wolvsk.effects.EffTSBroadcastMessage;
import fr.nashoba24.wolvsk.effects.EffTSConnect;
import fr.nashoba24.wolvsk.effects.EffTSDisconnect;
import fr.nashoba24.wolvsk.effects.EffTSKickClient;
import fr.nashoba24.wolvsk.effects.EffTSPoke;
import fr.nashoba24.wolvsk.effects.EffTSRemoveFromGroup;
import fr.nashoba24.wolvsk.effects.EffTSSendChannelMessage;
import fr.nashoba24.wolvsk.effects.EffTSSendPV;
import fr.nashoba24.wolvsk.effects.EffWolvMCCreateMission;
import fr.nashoba24.wolvsk.effects.EffWolvMCCreateRace;
import fr.nashoba24.wolvsk.effects.EffWolvMCOpenChangeInv;
import fr.nashoba24.wolvsk.effects.EffWolvMCOpenChooseInv;
import fr.nashoba24.wolvsk.effects.EffWolvMCSaveData;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockCoopIslands;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockHomeLocation;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockIslandCount;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockIslandLevel;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockIslandName;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockIslandWorld;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockOwner;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockTeamLeader;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockTeamMembers;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockTopTen;
import fr.nashoba24.wolvsk.expressions.ExprASkyBlockWarp;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCAllRaces;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCChanges;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCCustomPrefix;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCDescriptionMission;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCFinishMsgMission;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCGoalMission;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCMissionOfPlayer;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCMissionRace;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCMissionsRace;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCMsgCooldown;
import fr.nashoba24.wolvsk.expressions.ExprNameOfBlock;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCPlayTime;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCPrefixRace;
import fr.nashoba24.wolvsk.expressions.ExprWolvMCRaceOfPlayer;

public class WolvSK extends JavaPlugin implements Listener {
	
	private static WolvSK instance;
	public static TS3Query ts3query;
	public static TS3Api ts3api;
	  
	  @Override
	  public void onDisable()
	  {
	    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&bWolvSK Disabled!"));
	  }
	  
	  @Override
	  public void onEnable()
	  {
		   instance = this;
		   Bukkit.getPluginManager().registerEvents(new ExprNameOfBlock(), this);
		   Skript.registerAddon(this);
		   Skript.registerExpression(ExprNameOfBlock.class, String.class, ExpressionType.PROPERTY, "name of %block%", "%block%['s] name");
		   Skript.registerEffect(EffTSConnect.class, "(teamspeak|ts[3])[ server] connect to %string% with user %string% and (login|credentials) %string%, %string%[ on query port %integer%]");
		   Skript.registerEffect(EffTSDisconnect.class, "(teamspeak|ts[3])[ server] disconnect");
		   //Skript.registerEffect(EffTSSendChannelMessage.class, "(teamspeak|ts[3])[ server][ send] channel message %string%[ (in|to) channel id %integer%]");
		   Skript.registerEffect(EffTSBroadcastMessage.class, "(teamspeak|ts[3])[ server][ send] broadcast[ message] %string%");
		   Skript.registerEffect(EffTSKickClient.class, "(teamspeak|ts[3])[ server] kick[ client] %string% (due to|because) %string%[ from server]");
		   Skript.registerEffect(EffTSBanTemporary.class, "(teamspeak|ts[3])[ server] tempban[ client] %string% (due to|because) %string% for %integer% second[s]");
		   Skript.registerEffect(EffTSBan.class, "(teamspeak|ts[3])[ server] ban[ client] %string% (due to|because) %string%");
		   //Skript.registerEffect(EffTSAddToGroup.class, "(teamspeak|ts[3])[ server] add[ client] %string% to group id %integer%");
		   //Skript.registerEffect(EffTSRemoveFromGroup.class, "(teamspeak|ts[3])[ server] remove[ client] %string% from group id %integer%");
		   Skript.registerEffect(EffTSPoke.class, "(teamspeak|ts[3])[ server] poke[ client] %string% with (message|msg) %string%");
		   Skript.registerEffect(EffTSSendPV.class, "(teamspeak|ts[3])[ server][ send] (private|pv) (message|msg) %string%[ to][ client] %string%");
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
		   if (Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock") != null) {
			   Skript.registerExpression(ExprASkyBlockHomeLocation.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) home[ location] of %player%", "(asb|askyblock) %player%['s] home[ location]");
			   Skript.registerExpression(ExprASkyBlockIslandCount.class, Integer.class, ExpressionType.PROPERTY, "(asb|askyblock) island count");
			   Skript.registerExpression(ExprASkyBlockIslandLevel.class, Integer.class, ExpressionType.PROPERTY, "(asb|askyblock) [island ]level of %player%", "(asb|askyblock) %player%['s] [island ]level");
			   Skript.registerExpression(ExprASkyBlockHomeLocation.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) island[ location] of %player%", "(asb|askyblock) %player%['s] island[ location]");
			   Skript.registerExpression(ExprASkyBlockIslandName.class, String.class, ExpressionType.PROPERTY, "(asb|askyblock) island name of %player%", "(asb|askyblock) %player%['s] island name");
			   Skript.registerExpression(ExprASkyBlockIslandWorld.class, World.class, ExpressionType.PROPERTY, "(asb|askyblock)[ island] world");
			   Skript.registerExpression(ExprASkyBlockOwner.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock) owner of island at %location%", "(asb|askyblock) island at %location% owner");
			   //Skript.registerExpression(ExprASkyBlockSpawnRange.class, Integer.class, ExpressionType.PROPERTY, "(asb|askyblock) spawn range");
			   Skript.registerExpression(ExprASkyBlockWarp.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) warp of %player%", "(asb|askyblock) %player%['s] warp");
			   Skript.registerExpression(ExprASkyBlockCoopIslands.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) coop island[s] of %player%", "(asb|askyblock) %player%['s] coop island[s]");
			   Skript.registerExpression(ExprASkyBlockTeamLeader.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock)[ team] leader of team of %player%", "(asb|askyblock) %player%['s][ team] leader");
			   Skript.registerExpression(ExprASkyBlockTeamMembers.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock)[ team] members of team of %player%", "(asb|askyblock) %player%['s] team members");
			   Skript.registerExpression(ExprASkyBlockTopTen.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock) top (ten|10)");
			   Skript.registerCondition(CondASkyBlockHasIsland.class, "%player% has[ a[n]] (asb|askyblock) island");
			   Skript.registerCondition(CondASkyBlockHasTeam.class, "%player% (has|is in)[ a[n]] (asb|askyblock) team");
			   Skript.registerCondition(CondASkyBlockIsCoop.class, "%player% is (asb|askyblock) coop");
			   Skript.registerCondition(CondASkyBlockIslandAt.class, "there is [a[n]] (asb|askyblock) island at %location%");
			   Skript.registerEffect(EffASkyBlockCalculateLevel.class, "asb calculate level of %player%");
			   Skript.registerEvent("Coop Join Event", SimpleEvent.class, CoopJoinEvent.class, "asb coop join");
			   EventValues.registerEventValue(CoopJoinEvent.class, Player.class, new Getter<Player, CoopJoinEvent>() {
				   public Player get(CoopJoinEvent e) {
					   return fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getPlayer(e.getPlayer());
				   }
			   }, 0);
			   EventValues.registerEventValue(CoopJoinEvent.class, Location.class, new Getter<Location, CoopJoinEvent>() {
				   public Location get(CoopJoinEvent e) {
					   return e.getIslandLocation();
				   }
			   }, 0);
			   Skript.registerEvent("Coop Leave Event", SimpleEvent.class, CoopLeaveEvent.class, "asb coop leave");
			   EventValues.registerEventValue(CoopLeaveEvent.class, Player.class, new Getter<Player, CoopLeaveEvent>() {
				   public Player get(CoopLeaveEvent e) {
					   return fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getPlayer(e.getPlayer());
				   }
			   }, 0);
			   EventValues.registerEventValue(CoopLeaveEvent.class, Location.class, new Getter<Location, CoopLeaveEvent>() {
				   public Location get(CoopLeaveEvent e) {
					   return e.getIslandLocation();
				   }
			   }, 0);
			   Skript.registerEvent("Island Leave Event", SimpleEvent.class, IslandLeaveEvent.class, "asb[ island] leave");
			   EventValues.registerEventValue(IslandLeaveEvent.class, Player.class, new Getter<Player, IslandLeaveEvent>() {
				   public Player get(IslandLeaveEvent e) {
					   return fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getPlayer(e.getPlayer());
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandLeaveEvent.class, Location.class, new Getter<Location, IslandLeaveEvent>() {
				   public Location get(IslandLeaveEvent e) {
					   return e.getIslandLocation();
				   }
			   }, 0);
			   Skript.registerEvent("Island Enter Event", SimpleEvent.class, IslandEnterEvent.class, "asb[ island] enter");
			   EventValues.registerEventValue(IslandEnterEvent.class, Player.class, new Getter<Player, IslandEnterEvent>() {
				   public Player get(IslandEnterEvent e) {
					   return fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getPlayer(e.getPlayer());
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandEnterEvent.class, Location.class, new Getter<Location, IslandEnterEvent>() {
				   public Location get(IslandEnterEvent e) {
					   return e.getIslandLocation();
				   }
			   }, 0);
			   Skript.registerEvent("Island Level Event", SimpleEvent.class, IslandLevelEvent.class, "asb[ island] level[ change]");
			   EventValues.registerEventValue(IslandLevelEvent.class, Player.class, new Getter<Player, IslandLevelEvent>() {
				   public Player get(IslandLevelEvent e) {
					   return fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getPlayer(e.getPlayer());
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandLevelEvent.class, Location.class, new Getter<Location, IslandLevelEvent>() {
				   public Location get(IslandLevelEvent e) {
					   return e.getIslandLocation();
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandLevelEvent.class, Integer.class, new Getter<Integer, IslandLevelEvent>() {
				   public Integer get(IslandLevelEvent e) {
					   return e.getLevel();
				   }
			   }, 0);
			   Skript.registerEvent("Island New Event", SimpleEvent.class, IslandNewEvent.class, "asb new[ island]");
			   EventValues.registerEventValue(IslandNewEvent.class, Player.class, new Getter<Player, IslandNewEvent>() {
				   public Player get(IslandNewEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandNewEvent.class, Location.class, new Getter<Location, IslandNewEvent>() {
				   public Location get(IslandNewEvent e) {
					   return e.getIslandLocation();
				   }
			   }, 0);
			   Skript.registerEvent("Island Reset Event", SimpleEvent.class, IslandResetEvent.class, "asb reset[ island]");
			   EventValues.registerEventValue(IslandResetEvent.class, Player.class, new Getter<Player, IslandResetEvent>() {
				   public Player get(IslandResetEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
		   }
		   Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aWolvSK Enabled!"));
	  }
	  
	  public static WolvSK getInstance() {
		    return WolvSK.instance;
	  }
}
