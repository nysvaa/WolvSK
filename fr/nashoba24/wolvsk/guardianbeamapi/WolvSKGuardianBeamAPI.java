package fr.nashoba24.wolvsk.guardianbeamapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class WolvSKGuardianBeamAPI {

	public static void registerAll() {
		   if (Bukkit.getServer().getPluginManager().getPlugin("GuardianBeamAPI") != null && Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			   Skript.registerExpression(ExprStartPositionBeam.class, Location.class, ExpressionType.PROPERTY, "start[ing] location of[ guardian] beam[ with id] %string%");
			   Skript.registerExpression(ExprEndPositionBeam.class, Location.class, ExpressionType.PROPERTY, "end[ing] location of[ guardian] beam[ with id] %string%");
			   Skript.registerEffect(EffCreateBeam.class, "create[ guardian] beam (from|between) %location% (to|and) %location% (with id|named) %string%", "create[ guardian] beam (with id|named) %string% (from|between) %location% (to|and) %location%");
			   Skript.registerEffect(EffStopBeam.class, "stop[ guardian] beam (with id|named) %string%");
		   }
	}
	
}
