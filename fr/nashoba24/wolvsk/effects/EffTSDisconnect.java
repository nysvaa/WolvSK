package fr.nashoba24.wolvsk.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTSDisconnect extends Effect {
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "ts3 disconnect";
	}
	
	@Override
	protected void execute(Event e) {
		if(fr.nashoba24.wolvsk.WolvSK.ts3api != null) {
			fr.nashoba24.wolvsk.WolvSK.ts3api.logout();
			fr.nashoba24.wolvsk.WolvSK.ts3api = null;
		}
		if(fr.nashoba24.wolvsk.WolvSK.ts3query != null) {
			fr.nashoba24.wolvsk.WolvSK.ts3query.exit();
			fr.nashoba24.wolvsk.WolvSK.ts3query = null;
		}
	}
}
