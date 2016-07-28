package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Timespan;

public class WolvSKMisc {

	public static void registerAll() {
		   Skript.registerExpression(ExprNameOfBlock.class, String.class, ExpressionType.PROPERTY, "name of %block%", "%block%['s] name");
		   Skript.registerExpression(ExprBlockPower.class, Integer.class, ExpressionType.PROPERTY, "power of %block%", "%block%['s] power");
		   Skript.registerCondition(CondOdd.class, "%number% is odd");
		   Skript.registerCondition(CondEven.class, "%number% is even");
		   Skript.registerCondition(CondCooldownFinish.class, "cooldown %string% is finish", "cooldown %string% of %player% is finish");
		   Skript.registerEffect(EffCreateCooldown.class, "create cooldown %string% for %timespan%", "create cooldown %string% (for|of) %player% for %timespan%");
		   Skript.registerExpression(ExprCooldownLeftTime.class, Timespan.class, ExpressionType.PROPERTY, "cooldown[ left][ time] %string%", "cooldown[ left][ time] %string% of %player%");
		   Skript.registerExpression(ExprCountry.class, String.class, ExpressionType.PROPERTY, "country of ip %string%", "country of %player%", "country code of ip %string%", "country code of %player%", "ip %string%['s] country", "%player%['s] country", "ip %string%['s] country code", "%player%['s] country code");
	}
	
}
