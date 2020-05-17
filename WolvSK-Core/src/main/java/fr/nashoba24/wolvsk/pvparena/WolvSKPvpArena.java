package fr.nashoba24.wolvsk.pvparena;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.events.*;
import net.slipcor.pvparena.managers.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class WolvSKPvpArena {

	public static void register() {
		   if (Bukkit.getServer().getPluginManager().getPlugin("pvparena") != null) {
			   Classes.registerClass(new ClassInfo<Arena>(Arena.class, "pvparena").user("pvparena").name("pvparena").parser(new Parser<Arena>() {

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				@Nullable
				public Arena parse(String arg0, ParseContext arg1) {
					return ArenaManager.getArenaByName(arg0);
				}

				@Override
				public String toString(Arena arg0, int arg1) {
					return arg0.toString();
				}

				@Override
				public String toVariableNameString(Arena arg0) {
					return arg0.toString();
				}
			   
			   }));
			   Skript.registerEvent("pvparena death event", SimpleEvent.class, PADeathEvent.class, "(pa|pvparena) death");
			   EventValues.registerEventValue(PADeathEvent.class, Player.class, new Getter<Player, PADeathEvent>() {
				   public Player get(PADeathEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PADeathEvent.class, Arena.class, new Getter<Arena, PADeathEvent>() {
				   public Arena get(PADeathEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena end event", SimpleEvent.class, PAEndEvent.class, "(pa|pvparena) end");
			   EventValues.registerEventValue(PAEndEvent.class, Arena.class, new Getter<Arena, PAEndEvent>() {
				   public Arena get(PAEndEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena exit event", SimpleEvent.class, PAExitEvent.class, "(pa|pvparena) exit");
			   EventValues.registerEventValue(PAExitEvent.class, Player.class, new Getter<Player, PAExitEvent>() {
				   public Player get(PAExitEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PAExitEvent.class, Arena.class, new Getter<Arena, PAExitEvent>() {
				   public Arena get(PAExitEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena join event", SimpleEvent.class, PAJoinEvent.class, "(pa|pvparena) join");
			   EventValues.registerEventValue(PAJoinEvent.class, Player.class, new Getter<Player, PAJoinEvent>() {
				   public Player get(PAJoinEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PAJoinEvent.class, Arena.class, new Getter<Arena, PAJoinEvent>() {
				   public Arena get(PAJoinEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena kill event", SimpleEvent.class, PAKillEvent.class, "(pa|pvparena) kill");
			   EventValues.registerEventValue(PAKillEvent.class, Player.class, new Getter<Player, PAKillEvent>() {
				   public Player get(PAKillEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PAKillEvent.class, Arena.class, new Getter<Arena, PAKillEvent>() {
				   public Arena get(PAKillEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena leave event", SimpleEvent.class, PALeaveEvent.class, "(pa|pvparena) leave");
			   EventValues.registerEventValue(PALeaveEvent.class, Player.class, new Getter<Player, PALeaveEvent>() {
				   public Player get(PALeaveEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PALeaveEvent.class, Arena.class, new Getter<Arena, PALeaveEvent>() {
				   public Arena get(PALeaveEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena lose event", SimpleEvent.class, PALoseEvent.class, "(pa|pvparena) lose");
			   EventValues.registerEventValue(PALoseEvent.class, Player.class, new Getter<Player, PALoseEvent>() {
				   public Player get(PALoseEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PALoseEvent.class, Arena.class, new Getter<Arena, PALoseEvent>() {
				   public Arena get(PALoseEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena player class change event", SimpleEvent.class, PAPlayerClassChangeEvent.class, "(pa|pvparena)[ player] class change");
			   EventValues.registerEventValue(PAPlayerClassChangeEvent.class, Player.class, new Getter<Player, PAPlayerClassChangeEvent>() {
				   public Player get(PAPlayerClassChangeEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PAPlayerClassChangeEvent.class, Arena.class, new Getter<Arena, PAPlayerClassChangeEvent>() {
				   public Arena get(PAPlayerClassChangeEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   EventValues.registerEventValue(PAPlayerClassChangeEvent.class, String.class, new Getter<String, PAPlayerClassChangeEvent>() {
				   public String get(PAPlayerClassChangeEvent e) {
					   return e.getArenaClass().getName();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena start event", SimpleEvent.class, PAStartEvent.class, "(pa|pvparena) start");
			   EventValues.registerEventValue(PAStartEvent.class, Arena.class, new Getter<Arena, PAStartEvent>() {
				   public Arena get(PAStartEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena team change event", SimpleEvent.class, PATeamChangeEvent.class, "(pa|pvparena)[ player] team change");
			   EventValues.registerEventValue(PATeamChangeEvent.class, Player.class, new Getter<Player, PATeamChangeEvent>() {
				   public Player get(PATeamChangeEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PATeamChangeEvent.class, Arena.class, new Getter<Arena, PATeamChangeEvent>() {
				   public Arena get(PATeamChangeEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   EventValues.registerEventValue(PATeamChangeEvent.class, String.class, new Getter<String, PATeamChangeEvent>() {
				   public String get(PATeamChangeEvent e) {
					   return e.getTo().getName();
				   }
			   }, 0);
			   Skript.registerEvent("pvparena win event", SimpleEvent.class, PAWinEvent.class, "(pa|pvparena) win");
			   EventValues.registerEventValue(PAWinEvent.class, Player.class, new Getter<Player, PAWinEvent>() {
				   public Player get(PAWinEvent e) {
					   return e.getPlayer();
				   }
			   }, 0);
			   EventValues.registerEventValue(PAWinEvent.class, Arena.class, new Getter<Arena, PAWinEvent>() {
				   public Arena get(PAWinEvent e) {
					   return e.getArena();
				   }
			   }, 0);
			   Skript.registerExpression(ExprPVPArenaArena.class, Arena.class, ExpressionType.PROPERTY, "(pvp[ ]arena|pa) %string%");
			   Skript.registerExpression(ExprPVPArenaPlayerArena.class, Arena.class, ExpressionType.PROPERTY, "(pvp[ ]arena|pa) of %player%", "%player%['s] (pvp[ ]arena|pa)");
			   Skript.registerEffect(EffPVPArenaRemoveArena.class, "remove (pvp[ ]arena|pa) %arena%");
		   }
	}
	
}
