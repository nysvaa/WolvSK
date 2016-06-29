package fr.nashoba24.wolvsk.supertrails;

import javax.annotation.Nullable;

import me.kvq.plugin.trails.Menu;
import me.kvq.plugin.trails.API.SuperTrailsAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprSuperTrailsType extends SimpleExpression<String>{
	private Expression<Player> player;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "wings type of player";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		Integer i = Menu.typee.get(player.getSingle(e).getName());
		if(i==null || i==0) {
			return null;
		}
		else if(i==1) {
			return new String[]{ "angel" };
		}
		else if(i==2) {
			return new String[]{ "butterfly" };
		}
		return null;
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if(((String) delta[0]).toLowerCase().startsWith("a")) {
				 Menu.typee.put(player.getSingle(e).getName(), Integer.valueOf(1));
				 SuperTrailsAPI.setTrail(55, player.getSingle(e));
			}
			else if(((String) delta[0]).toLowerCase().startsWith("b")) {
				Menu.typee.put(player.getSingle(e).getName(), Integer.valueOf(2));
				SuperTrailsAPI.setTrail(56, player.getSingle(e));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}

