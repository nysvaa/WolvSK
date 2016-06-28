package fr.nashoba24.wolvsk.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class EffTSSendChannelMessage extends Effect {
	
	private Expression<String> msg;
	private Expression<Integer> id;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		msg = (Expression<String>) expr[0];
		if(expr.length==2) {
			id = (Expression<Integer>) expr[1];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "ts3 send channel message";
	}
	
	@Override
	protected void execute(Event e) {
		if(WolvSK.ts3api != null) {
			if(id == null) {
				WolvSK.ts3api.sendChannelMessage(msg.getSingle(e));
			}
			else {
				WolvSK.ts3api.sendChannelMessage(id.getSingle(e), msg.getSingle(e));
			}
		}
		else {
			WolvSK.getInstance().getLogger().severe("You are not connected to a Teamspeak server!");
		}
	}
}
