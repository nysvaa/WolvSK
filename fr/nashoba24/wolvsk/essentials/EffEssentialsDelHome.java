package fr.nashoba24.wolvsk.essentials;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class EffEssentialsDelHome extends Effect {

	private Expression<Player> player;
	private Expression<String> home;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[1];
		home = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "delete home";
	}
	
	@Override
	protected void execute(Event e) {
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player.getSingle(e));
    	if(user!=null) {
    		try {
				user.delHome(home.getSingle(e));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
	}
}
