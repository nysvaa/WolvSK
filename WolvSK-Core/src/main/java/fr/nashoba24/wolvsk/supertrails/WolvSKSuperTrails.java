package fr.nashoba24.wolvsk.supertrails;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import org.bukkit.Bukkit;

public class WolvSKSuperTrails {

	public static void register() {
		   if (Bukkit.getServer().getPluginManager().getPlugin("SuperTrails") != null) {
			   Skript.registerEffect(EffSuperTrailsHideTrails.class, "hide (trail[s]|wing[s]) to %player%");
			   Skript.registerEffect(EffSuperTrailsOpenMenu.class, "open (supertrails|st) (menu|gui|inv[entory]) %string% to %player%"); 
			   Skript.registerEffect(EffSuperTrailsRevealTrails.class, "reveal (trail[s]|wing[s]) to %player%"); 
			   Skript.registerEffect(EffSuperTrailsSetWings.class, "set wing[s] of %player% to[ color[s]] %string%, %string%(,| and) %string%");
			   Skript.registerExpression(ExprSuperTrailsTrail.class, Integer.class, ExpressionType.PROPERTY, "(trail[s]|wing[s]) of %player%", "%player%['s] (trail[s]|wing[s])");
			   Skript.registerExpression(ExprSuperTrailsWingsColor.class, String.class, ExpressionType.PROPERTY, "wing[s] color %integer% of %player%", "color %integer% of wing[s] of %player%", "%player%['s] wing[s] color %integer%");
			   Skript.registerExpression(ExprSuperTrailsType.class, String.class, ExpressionType.PROPERTY, "wing[s] type of %player%", "%player%['s] wing[s] type");
		   }
	}
	
}
