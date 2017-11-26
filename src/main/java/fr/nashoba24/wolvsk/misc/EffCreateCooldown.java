package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffCreateCooldown extends Effect {
	
	private Expression<String> name;
	private Expression<Player> player;
	private Expression<Timespan> time;
	private boolean pl = false;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern==1) {
			name = (Expression<String>) expr[0];
			player = (Expression<Player>) expr[1];
			time = (Expression<Timespan>) expr[2];
			pl = true;
		}
		else if(matchedPattern==0) {
			name = (Expression<String>) expr[0];
			time = (Expression<Timespan>) expr[1];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "create cooldown";
	}
	
	@Override
	protected void execute(Event e) {
		if(pl) {
			WolvSK.cooldowns.put(name.getSingle(e) + "." + player.getSingle(e).getName(), System.currentTimeMillis() + time.getSingle(e).getMilliSeconds());
		}
		else {
			WolvSK.cooldowns.put(name.getSingle(e), System.currentTimeMillis() + time.getSingle(e).getMilliSeconds());
		}
	}
}
