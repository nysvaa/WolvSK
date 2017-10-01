package fr.nashoba24.wolvsk.essentials;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class CondEssentialsIgnore extends Condition {

    private Expression<Player> player1;
    private Expression<Player> player2;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        player1 = (Expression<Player>) expr[0];
        player2 = (Expression<Player>) expr[1];
        setNegated(i == 1);
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "player ignore";
    }

    @Override
    public boolean check(Event e) {
    	Essentials ess = ((Essentials) WolvSK.getInstance().getServer().getPluginManager().getPlugin("Essentials"));
    	User user = ess.getUser(player1.getSingle(e));
    	if(user==null) { return false; }
        if(user._getIgnoredPlayers().contains(player2.getSingle(e).getName().toLowerCase())) {
        	return isNegated() ? false : true;
        }
        else {
        	return isNegated() ? true : false;
        }
    }

}