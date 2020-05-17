package fr.nashoba24.wolvsk.essentials;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprEssentialsLastAccountName extends SimpleExpression<String>{
	
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
		return "last account name";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player.getSingle(e));
    	if(user == null) { return null; }
    	return new String[] { user.getLastAccountName() };
	}
}

