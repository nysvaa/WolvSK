package fr.nashoba24.wolvsk.askyblock;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondASkyBlockHasIsland extends Condition {

    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        player = (Expression<Player>) expr[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "player has island";
    }

    @Override
    public boolean check(Event e) {
        if(ASkyBlockAPI.getInstance().hasIsland(player.getSingle(e).getUniqueId())) {
        	return true;
        }
        else {
        	return false;
        }
    }

}