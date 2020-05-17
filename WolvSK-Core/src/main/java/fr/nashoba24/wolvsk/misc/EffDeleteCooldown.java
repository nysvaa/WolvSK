package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffDeleteCooldown extends Effect {
	
	private Expression<String> name;
	private Expression<Player> player;
	private boolean pl = false;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern==1) {
			name = (Expression<String>) expr[0];
			player = (Expression<Player>) expr[1];
			pl = true;
		}
		else if(matchedPattern==0) {
			name = (Expression<String>) expr[0];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "delete cooldown";
	}
	
	@Override
	protected void execute(Event e) {
		if(pl) {
			WolvSK.cooldowns.remove(name.getSingle(e) + "." + player.getSingle(e).getUniqueId());
		}
		else {
			WolvSK.cooldowns.remove(name.getSingle(e));
		}
	}
}
