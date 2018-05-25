package fr.nashoba24.wolvsk.essentials;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import fr.nashoba24.wolvsk.WolvSK;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffEssentialsMakePlayerIgnore extends Effect {

	private Expression<Player> player1;
	private Expression<Player> player2;
	private boolean set = true;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player1 = (Expression<Player>) expr[0];
		player2 = (Expression<Player>) expr[1];
		if(matchedPattern==1) {
			set = false;
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "make player ignore";
	}
	
	@Override
	protected void execute(Event e) {
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player1.getSingle(e));
    	User user2 = ess.getUser(player2.getSingle(e));
    	if(user!=null && user2!=null) {
    		user.setIgnoredPlayer(user2, set);
    	}
	}
}
