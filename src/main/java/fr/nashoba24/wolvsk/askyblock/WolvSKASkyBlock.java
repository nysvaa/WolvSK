package fr.nashoba24.wolvsk.askyblock;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.wasteofplastic.askyblock.events.*;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WolvSKASkyBlock {

	public static void register() {
		   if (WolvSK.getInstance().getServer().getPluginManager().getPlugin("ASkyBlock") != null) {
			   Skript.registerExpression(ExprASkyBlockHomeLocation.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) home[ location] of %player%", "(asb|askyblock) %player%['s] home[ location]");
			   Skript.registerExpression(ExprASkyBlockIslandCount.class, Integer.class, ExpressionType.PROPERTY, "(asb|askyblock) island count");
			   Skript.registerExpression(ExprASkyBlockIslandLevel.class, Long.class, ExpressionType.PROPERTY, "(asb|askyblock) [island ]level of %player%", "(asb|askyblock) %player%['s] [island ]level");
			   Skript.registerExpression(ExprASkyBlockHomeLocation.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) island[ location] of %player%", "(asb|askyblock) %player%['s] island[ location]");
			   Skript.registerExpression(ExprASkyBlockIslandName.class, String.class, ExpressionType.PROPERTY, "(asb|askyblock) island name of %player%", "(asb|askyblock) %player%['s] island name");
			   Skript.registerExpression(ExprASkyBlockIslandWorld.class, World.class, ExpressionType.PROPERTY, "(asb|askyblock)[ island] world");
			   Skript.registerExpression(ExprASkyBlockOwner.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock) owner of island at %location%", "(asb|askyblock) island at %location% owner");
			   Skript.registerExpression(ExprASkyBlockWarp.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) warp of %player%", "(asb|askyblock) %player%['s] warp");
			   Skript.registerExpression(ExprASkyBlockCoopIslands.class, Location.class, ExpressionType.PROPERTY, "(asb|askyblock) coop island[s] of %player%", "(asb|askyblock) %player%['s] coop island[s]");
			   Skript.registerExpression(ExprASkyBlockTeamLeader.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock)[ team] leader of team of %player%", "(asb|askyblock) %player%['s][ team] leader");
			   Skript.registerExpression(ExprASkyBlockTeamMembers.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock)[ team] members of team of %player%", "(asb|askyblock) %player%['s] team members");
			   Skript.registerExpression(ExprASkyBlockTopTen.class, OfflinePlayer.class, ExpressionType.PROPERTY, "(asb|askyblock) top (ten|10)");
			   Skript.registerCondition(CondASkyBlockHasIsland.class, "%player% has[ a[n]] (asb|askyblock) island", "%player% has(n't| not)[ a[n]] (asb|askyblock) island");
			   Skript.registerCondition(CondASkyBlockHasTeam.class, "%player% (has|is in)[ a[n]] (asb|askyblock) team", "%player% (has(n't| not)|is(n't not) in)[ a[n]] (asb|askyblock) team");
			   Skript.registerCondition(CondASkyBlockIsCoop.class, "%player% is (asb|askyblock) coop", "%player% is(n't| not) (asb|askyblock) coop");
			   Skript.registerCondition(CondASkyBlockIslandAt.class, "there is [a[n]] (asb|askyblock) island at %location%", "there is(n't| not) [a[n]] (asb|askyblock) island at %location%");
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
			   Skript.registerEvent("Island Exit Event", SimpleEvent.class, IslandExitEvent.class, "asb[ island] exit");
			   EventValues.registerEventValue(IslandExitEvent.class, Player.class, new Getter<Player, IslandExitEvent>() {
				   public Player get(IslandExitEvent e) {
					   return fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getPlayer(e.getPlayer());
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandExitEvent.class, Location.class, new Getter<Location, IslandExitEvent>() {
				   public Location get(IslandExitEvent e) {
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
			   Skript.registerEvent("Island Level Event", SimpleEvent.class, IslandPostLevelEvent.class, "asb[ island] level[ change]");
			   EventValues.registerEventValue(IslandPostLevelEvent.class, Player.class, new Getter<Player, IslandPostLevelEvent>() {
				   public Player get(IslandPostLevelEvent e) {
					   return fr.nashoba24.wolvsk.WolvSK.getInstance().getServer().getPlayer(e.getPlayer());
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandPostLevelEvent.class, Location.class, new Getter<Location, IslandPostLevelEvent>() {
				   public Location get(IslandPostLevelEvent e) {
					   return e.getIslandLocation();
				   }
			   }, 0);
			   EventValues.registerEventValue(IslandPostLevelEvent.class, Long.class, new Getter<Long, IslandPostLevelEvent>() {
				   public Long get(IslandPostLevelEvent e) {
					   return e.getLongLevel();
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
	}
	
}
