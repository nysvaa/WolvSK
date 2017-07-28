package fr.nashoba24.wolvsk.misc;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class CondCooldownFinish extends Condition {

	private Expression<String> name;
	private Expression<Player> player;
	private boolean pl = false;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
		if(i==1 || i==3) {
			name = (Expression<String>) expr[0];
			player = (Expression<Player>) expr[1];
			pl = true;
		}
		else if(i==0 || i==2) {
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
    	if(pl) {
    		if(player.getSingle(e)!=null) {
	    		if(WolvSK.cooldowns.containsKey(name.getSingle(e) + "." + player.getSingle(e).getName())) {
	    			return isNegated() ? !(WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getName())<System.currentTimeMillis()) : (WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getName())<System.currentTimeMillis());
	    		}
    		}
	    	return !isNegated();
    	}
    	else {
    		if(WolvSK.cooldowns.containsKey(name.getSingle(e))) {
    			return isNegated() ? !(WolvSK.cooldowns.get(name.getSingle(e))<System.currentTimeMillis()) : (WolvSK.cooldowns.get(name.getSingle(e))<System.currentTimeMillis());
    		}
    		return !isNegated();
    	}
    }

}