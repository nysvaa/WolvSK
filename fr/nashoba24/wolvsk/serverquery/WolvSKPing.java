package fr.nashoba24.wolvsk.serverquery;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

public class WolvSKPing {

	public static void registerAll() {
		   Skript.registerExpression(ExprCountServer.class, Integer.class, ExpressionType.PROPERTY, "(number of player[s]|player[s] count) (on|in|of) server[ with ip] %string%");
		   Skript.registerExpression(ExprServerMaxPlayers.class, Integer.class, ExpressionType.PROPERTY, "max[imum][ of] player[s] (on|in|of|for) server[ with ip] %string%");
		   Skript.registerExpression(ExprServerMOTD.class, String.class, ExpressionType.PROPERTY, "motd (of|for) server[ with ip] %string%");
		   Skript.registerExpression(ExprServerVersion.class, String.class, ExpressionType.PROPERTY, "version (of|for) server[ with ip] %string%");
	}
	
}
