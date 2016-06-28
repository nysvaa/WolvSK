package fr.nashoba24.wolvsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprASkyBlockIslandName extends SimpleExpression<String>{
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
		return "asb island name of player";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{ ASkyBlockAPI.getInstance().getIslandName(player.getSingle(e).getUniqueId()) };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			ASkyBlockAPI.getInstance().setIslandName(player.getSingle(e).getUniqueId(), (String) delta[0]);
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

