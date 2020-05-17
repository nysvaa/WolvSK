package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;


public class CondCooldownFinish extends Condition {

	private Expression<String> name;
	private Expression<Player> player;
	private boolean pl = false;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
		if (i == 1 || i == 3) {
			name = (Expression<String>) expr[0];
			player = (Expression<Player>) expr[1];
			pl = true;
		} else if (i == 0 || i == 2) {
			name = (Expression<String>) expr[0];
		}
		setNegated(i == 2 || i == 3);
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "cooldown is finished";
	}

	@Override
	public boolean check(Event e) {
		if (pl) {
			if (player.getSingle(e) != null) {
				if (WolvSK.cooldowns.containsKey(name.getSingle(e) + "." + player.getSingle(e).getUniqueId())) {
					if (WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getUniqueId()) < System.currentTimeMillis()) {
						WolvSK.cooldowns.remove(name.getSingle(e) + "." + player.getSingle(e).getUniqueId());
						return !isNegated();
					}
					return isNegated();
				}
			}
			return !isNegated();
		} else {
			if (WolvSK.cooldowns.containsKey(name.getSingle(e))) {
				if (WolvSK.cooldowns.get(name.getSingle(e)) < System.currentTimeMillis()) {
					WolvSK.cooldowns.remove(name.getSingle(e));
					return !isNegated();
				}
				return isNegated();
			}
			return !isNegated();
		}
	}
}