package fr.nashoba24.wolvsk;

import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.UserBalanceUpdateEvent;

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
import fr.nashoba24.wolvsk.askyblock.CondASkyBlockHasIsland;
import fr.nashoba24.wolvsk.askyblock.CondASkyBlockHasTeam;
import fr.nashoba24.wolvsk.askyblock.CondASkyBlockIsCoop;
import fr.nashoba24.wolvsk.askyblock.CondASkyBlockIslandAt;
import fr.nashoba24.wolvsk.askyblock.EffASkyBlockCalculateLevel;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockCoopIslands;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockHomeLocation;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockIslandCount;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockIslandLevel;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockIslandName;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockIslandWorld;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockOwner;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockTeamLeader;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockTeamMembers;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockTopTen;
import fr.nashoba24.wolvsk.askyblock.ExprASkyBlockWarp;
import fr.nashoba24.wolvsk.essentials.CondEssentialsAFK;
import fr.nashoba24.wolvsk.essentials.ExprEssentialsHome;
import fr.nashoba24.wolvsk.essentials.ExprEssentialsHomes;
import fr.nashoba24.wolvsk.essentials.ExprEssentialsLogoutLocation;
import fr.nashoba24.wolvsk.misc.ExprNameOfBlock;
import fr.nashoba24.wolvsk.teamspeak.CondTSIsOnline;
import fr.nashoba24.wolvsk.teamspeak.CondTSIsQuery;
import fr.nashoba24.wolvsk.teamspeak.EffTSBan;
import fr.nashoba24.wolvsk.teamspeak.EffTSBanTemporary;
import fr.nashoba24.wolvsk.teamspeak.EffTSBroadcastMessage;
import fr.nashoba24.wolvsk.teamspeak.EffTSConnect;
import fr.nashoba24.wolvsk.teamspeak.EffTSDisconnect;
import fr.nashoba24.wolvsk.teamspeak.EffTSKickClient;
import fr.nashoba24.wolvsk.teamspeak.EffTSPoke;
import fr.nashoba24.wolvsk.teamspeak.EffTSSendChannelMessage;
import fr.nashoba24.wolvsk.teamspeak.EffTSSendPV;
import fr.nashoba24.wolvsk.teamspeak.ExprTSClientID;
import fr.nashoba24.wolvsk.teamspeak.ExprTSClients;
import fr.nashoba24.wolvsk.teamspeak.ExprTSDescription;
import fr.nashoba24.wolvsk.teamspeak.ExprTSIP;
import fr.nashoba24.wolvsk.teamspeak.ExprTSIPSList;
import fr.nashoba24.wolvsk.teamspeak.ExprTSNickname;
import fr.nashoba24.wolvsk.wolvmc.CondWolvMCCanUsePowerBlock;
import fr.nashoba24.wolvsk.wolvmc.CondWolvMCCanUsePowerBlockMSG;
import fr.nashoba24.wolvsk.wolvmc.CondWolvMCCanUseSafePower;
import fr.nashoba24.wolvsk.wolvmc.CondWolvMCCanUseSafePowerMSG;
import fr.nashoba24.wolvsk.wolvmc.CondWolvMCFinishMission;
import fr.nashoba24.wolvsk.wolvmc.CondWolvMCRaceExist;
import fr.nashoba24.wolvsk.wolvmc.EffWolvMCCreateMission;
import fr.nashoba24.wolvsk.wolvmc.EffWolvMCCreateRace;
import fr.nashoba24.wolvsk.wolvmc.EffWolvMCOpenChangeInv;
import fr.nashoba24.wolvsk.wolvmc.EffWolvMCOpenChooseInv;
import fr.nashoba24.wolvsk.wolvmc.EffWolvMCSaveData;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCAllRaces;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCChanges;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCCustomPrefix;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCDescriptionMission;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCFinishMsgMission;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCGoalMission;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCMissionOfPlayer;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCMissionRace;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCMissionsRace;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCMsgCooldown;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCPlayTime;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCPrefixRace;
import fr.nashoba24.wolvsk.wolvmc.ExprWolvMCRaceOfPlayer;

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
		   Skript.registerEffect(EffTSSendChannelMessage.class, "(teamspeak|ts[3])[ server][ send] channel (message|msg) %string%[ (in|to) channel id %integer%]");
		   Skript.registerEffect(EffTSBroadcastMessage.class, "(teamspeak|ts[3])[ server][ send] broadcast[ message] %string%");
		   Skript.registerEffect(EffTSKickClient.class, "(teamspeak|ts[3])[ server] kick[ client] %string% (due to|because) %string%[ from server]");
		   Skript.registerEffect(EffTSBanTemporary.class, "(teamspeak|ts[3])[ server] tempban[ client] %string% (due to|because) %string% for %integer% second[s]");
		   Skript.registerEffect(EffTSBan.class, "(teamspeak|ts[3])[ server] ban[ client] %string% (due to|because) %string%");
		   //Skript.registerEffect(EffTSAddToGroup.class, "(teamspeak|ts[3])[ server] add[ client] %string% to group id %integer%");
		   //Skript.registerEffect(EffTSRemoveFromGroup.class, "(teamspeak|ts[3])[ server] remove[ client] %string% from group id %integer%");
		   Skript.registerEffect(EffTSPoke.class, "(teamspeak|ts[3])[ server] poke[ client] %string% with (message|msg) %string%");
		   Skript.registerEffect(EffTSSendPV.class, "(teamspeak|ts[3])[ server][ send] (private|pv) (message|msg) %string%[ to][ client] %string%");
		   Skript.registerCondition(CondTSIsOnline.class, "(teamspeak|ts[3]) client %string% is online");
		   Skript.registerCondition(CondTSIsQuery.class, "(teamspeak|ts[3])['s] addon is connected", "[the] addon is connect[ed] to (teamspeak|ts[3])");
		   Skript.registerExpression(ExprTSClientID.class, Integer.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) id of[ client] %string%");
		   Skript.registerExpression(ExprTSDescription.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) description of[ client] %string%"); //TODO On ne peux pas la get
		   Skript.registerExpression(ExprTSIP.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) ip of[ client] %string%"); 
		   Skript.registerExpression(ExprTSIPSList.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3])[ client[s]] correspond[ing][s] to ip %string%", "(teamspeak|ts[3]) ip[s] correspond[ing][s] to %string%");
		   Skript.registerExpression(ExprTSNickname.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) (nickname|nick|name) of[ client] %string%");
		   Skript.registerExpression(ExprTSClients.class, String.class, ExpressionType.PROPERTY, "(teamspeak|ts[3]) client[s] (online[s]|connect[ed])");
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
			   Skript.registerCondition(CondEssentialsAFK.class, "%player% is[ in] god[ mode]");
			   Skript.registerCondition(CondEssentialsAFK.class, "%player% is vanish[ed]"); //Ajouter support SuperVanish
			   Skript.registerExpression(ExprEssentialsHomes.class, String.class, ExpressionType.PROPERTY, "homes of %player%", "%player%['s] homes");
			   Skript.registerExpression(ExprEssentialsHome.class, Location.class, ExpressionType.PROPERTY, "home %string% of %player%", "%player%['s] home %string%");
			   Skript.registerExpression(ExprEssentialsHome.class, Location.class, ExpressionType.PROPERTY, "home of %player%", "%player%['s] home");
			   Skript.registerExpression(ExprEssentialsLogoutLocation.class, Location.class, ExpressionType.PROPERTY, "log[ ]out[ location] of %player%", "%player%['s] log[ ]out[ location]");
		   }
		   Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "&aWolvSK Enabled!"));
	  }
	  
	  public static WolvSK getInstance() {
		    return WolvSK.instance;
	  }
}
