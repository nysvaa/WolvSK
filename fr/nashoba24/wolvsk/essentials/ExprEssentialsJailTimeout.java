package fr.nashoba24.wolvsk.essentials;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import fr.nashoba24.wolvsk.WolvSK;

public class ExprEssentialsJailTimeout extends SimpleExpression<Long>{
	
	private Expression<Player> player;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Long> getReturnType() {
		return Long.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "jail timeout of player";
	}
	
	@Override
	@Nullable
	protected Long[] get(Event e) {
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player.getSingle(e));
    	if(user == null) { return null; }
    	return new Long[] { user.getJailTimeout() };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player.getSingle(e));
    	if(user == null) { return; }
		if (mode == ChangeMode.SET) {
			user.setJailTimeout((Long) delta[0]);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Long.class);
		return null;
	}
}

