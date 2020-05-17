package fr.nashoba24.wolvsk.supertrails;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.kvq.plugin.trails.API.SuperTrailsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprSuperTrailsWingsColor extends SimpleExpression<String>{
	private Expression<Player> player;
	private Expression<Integer> color;
	
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
		if(matchedPattern==0 || matchedPattern==1) {
			player = (Expression<Player>) expr[1];
			color = (Expression<Integer>) expr[0];
		}
		else if(matchedPattern==2) {
			player = (Expression<Player>) expr[0];
			color = (Expression<Integer>) expr[1];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "wings color of player";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(color.getSingle(e)==1) {
			return new String[]{ SuperTrailsAPI.getWingsColor1(player.getSingle(e)) };
		}
		else if(color.getSingle(e)==2) {
			return new String[]{ SuperTrailsAPI.getWingsColor2(player.getSingle(e)) };
		}
		else if(color.getSingle(e)==3) {
			return new String[]{ SuperTrailsAPI.getWingsColor3(player.getSingle(e)) };
		}
		else {
			return null;
		}
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if(color.getSingle(e)==1) {
				SuperTrailsAPI.SetWings(player.getSingle(e), ((String) delta[0]).toUpperCase(), SuperTrailsAPI.getWingsColor2(player.getSingle(e)), SuperTrailsAPI.getWingsColor3(player.getSingle(e)));
			}
			else if(color.getSingle(e)==2) {
				SuperTrailsAPI.SetWings(player.getSingle(e), SuperTrailsAPI.getWingsColor1(player.getSingle(e)), ((String) delta[0]).toUpperCase(), SuperTrailsAPI.getWingsColor3(player.getSingle(e)));
			}
			else if(color.getSingle(e)==3) {
				SuperTrailsAPI.SetWings(player.getSingle(e), SuperTrailsAPI.getWingsColor1(player.getSingle(e)), SuperTrailsAPI.getWingsColor2(player.getSingle(e)), ((String) delta[0]).toUpperCase());
			}
			else {
				return;
			}
		}
		else if (mode == ChangeMode.RESET) {
			if(color.getSingle(e)==1) {
				SuperTrailsAPI.SetWings(player.getSingle(e), null, SuperTrailsAPI.getWingsColor2(player.getSingle(e)), SuperTrailsAPI.getWingsColor3(player.getSingle(e)));
			}
			else if(color.getSingle(e)==2) {
				SuperTrailsAPI.SetWings(player.getSingle(e), SuperTrailsAPI.getWingsColor1(player.getSingle(e)), null, SuperTrailsAPI.getWingsColor3(player.getSingle(e)));
			}
			else if(color.getSingle(e)==3) {
				SuperTrailsAPI.SetWings(player.getSingle(e), SuperTrailsAPI.getWingsColor1(player.getSingle(e)), SuperTrailsAPI.getWingsColor2(player.getSingle(e)), null);
			}
			else {
				return;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET)
			return CollectionUtils.array(String.class);
		return null;
	}
}

