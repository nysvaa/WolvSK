package fr.nashoba24.wolvsk.playerpoints;

import org.bukkit.Bukkit;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class WolvSKPlayerPoints {

	public static void registerAll() {
		if (Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints") != null) {
			Skript.registerEffect(EffPayPoints.class, "make %player% pay %integer% points to %player%");
			Skript.registerExpression(ExprPoints.class, Integer.class, ExpressionType.PROPERTY, "point[s] of %player%", "%player%['s] point[s]");
		}
	}
	
}
