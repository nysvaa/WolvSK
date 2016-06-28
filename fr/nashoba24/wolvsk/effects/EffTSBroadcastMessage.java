package fr.nashoba24.wolvsk.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class EffTSBroadcastMessage extends Effect {
	
	private Expression<String> msg;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		msg = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "ts3 broadcast";
	}
	
	@Override
	protected void execute(Event e) {
		if(WolvSK.ts3api != null) {
			WolvSK.ts3api.broadcast(msg.getSingle(e));
		}
		else {
			WolvSK.getInstance().getLogger().severe("You are not connected to a Teamspeak server!");
		}
	}
}
