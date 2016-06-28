package fr.nashoba24.wolvsk.conditions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class CondFinishMission extends Condition {


    private Expression<Player> player;
    private Expression<String> mission;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expr, int i, Kleenean kl, ParseResult pr) {
        player = (Expression<Player>) expr[0];
        mission = (Expression<String>) expr[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean b) {
        return "If a player has finish a mission";
    }

    @Override
    public boolean check(Event e) {
        if(WolvMC.hasFinishMission(mission.getSingle(e), player.getSingle(e).getName())) {
        	return true;
        }
        else {
        	return false;
        }
    }

}