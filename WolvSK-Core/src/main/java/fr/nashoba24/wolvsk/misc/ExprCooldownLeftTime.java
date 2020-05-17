package fr.nashoba24.wolvsk.misc;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprCooldownLeftTime extends SimpleExpression<Timespan> {
	
	private Expression<String> name;
	private Expression<Player> player;
	private boolean pl = false;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Timespan> getReturnType() {
		return Timespan.class;
	}
	
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
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "cooldown left time";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	@Nullable
	protected Timespan[] get(Event e) {
    	if(pl) {
    		if(WolvSK.cooldowns.containsKey(name.getSingle(e) + "." + player.getSingle(e).getName())) {
    			if(WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getName())<System.currentTimeMillis()) {
    				return new Timespan[] { Timespan.fromTicks(0) };
    			}
    			else {
    				Long ms = WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getName()) - System.currentTimeMillis();
    				Long ticks = ms / 50;
    				Integer ticks2 = ticks.intValue();
    				return new Timespan[] { Timespan.fromTicks(ticks2) };
    			}
    		}
    		else {
    			return new Timespan[] { Timespan.fromTicks(0) };
    		}
    	}
    	else {
    		if(WolvSK.cooldowns.containsKey(name.getSingle(e))) {
    			if(WolvSK.cooldowns.get(name.getSingle(e))<System.currentTimeMillis()) {
    				return new Timespan[] { Timespan.fromTicks(0) };
    			}
    			else {
    				long ms = WolvSK.cooldowns.get(name.getSingle(e)) - System.currentTimeMillis();
    				long ticks = ms / 50;
    				int ticks2 = (int) ticks;
    				return new Timespan[] { Timespan.fromTicks(ticks2) };
    			}
    		}
    		else {
    			return new Timespan[] { Timespan.fromTicks(0) };
    		}
    	}
	}
	
	@Override
	public void change(Event e, Object[] delta, ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if(pl) {
				WolvSK.cooldowns.put(name.getSingle(e) + "." + player.getSingle(e).getName(), System.currentTimeMillis() + ((Timespan) delta[0]).getMilliSeconds());
			}
			else {
				WolvSK.cooldowns.put(name.getSingle(e), System.currentTimeMillis() + ((Timespan) delta[0]).getMilliSeconds());
			}
		}
		else if (mode == ChangeMode.RESET) {
			if(pl) {
				WolvSK.cooldowns.remove(name.getSingle(e) + "." + player.getSingle(e).getName());
			}
			else {
				WolvSK.cooldowns.remove(name.getSingle(e));
			}
		}
		else if(mode == ChangeMode.ADD) {
			if(pl) {
				if(WolvSK.cooldowns.containsKey(name.getSingle(e) + "." + player.getSingle(e).getName())) {
					Long ms = WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getName());
					WolvSK.cooldowns.put(name.getSingle(e) + "." + player.getSingle(e).getName(), ms + ((Timespan) delta[0]).getMilliSeconds());
				}
			}
			else {
				if(WolvSK.cooldowns.containsKey(name.getSingle(e))) {
					Long ms = WolvSK.cooldowns.get(name.getSingle(e));
					WolvSK.cooldowns.put(name.getSingle(e), ms + ((Timespan) delta[0]).getMilliSeconds());
				}
			}
		}
		else if(mode == ChangeMode.REMOVE) {
			if(pl) {
				if(WolvSK.cooldowns.containsKey(name.getSingle(e) + "." + player.getSingle(e).getName())) {
					Long ms = WolvSK.cooldowns.get(name.getSingle(e) + "." + player.getSingle(e).getName());
					WolvSK.cooldowns.put(name.getSingle(e) + "." + player.getSingle(e).getName(), ms - ((Timespan) delta[0]).getMilliSeconds());
				}
			}
			else {
				if(WolvSK.cooldowns.containsKey(name.getSingle(e))) {
					Long ms = WolvSK.cooldowns.get(name.getSingle(e));
					WolvSK.cooldowns.put(name.getSingle(e), ms - ((Timespan) delta[0]).getMilliSeconds());
				}
			}
		}
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Timespan.class);
		return null;
	}
}

