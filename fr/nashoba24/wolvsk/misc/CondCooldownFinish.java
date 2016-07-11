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
		if(i==1) {
			name = (Expression<String>) expr[0];
			player = (Expression<Player>) expr[1];
			pl = true;
		}
		else if(i==0) {
			name = (Expression<String>) expr[0];
		}
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "cooldown is finish";
    }

    @Override
    public boolean check(Event e) {
    	if(pl) {
    		if(WolvSK.cooldowns.containsKey(name.getSingle(e) + "." + player.getSingle(e).getName())) {
    			if(WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getName())<System.currentTimeMillis()) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    		else {
    			return true;
    		}
    	}
    	else {
    		if(WolvSK.cooldowns.containsKey(name.getSingle(e))) {
    			if(WolvSK.cooldowns.get(name.getSingle(e))<System.currentTimeMillis()) {
    				return true;
    			}
    			else {
    				return false;
    			}
    		}
    		else {
    			return true;
    		}
    	}
    }

}