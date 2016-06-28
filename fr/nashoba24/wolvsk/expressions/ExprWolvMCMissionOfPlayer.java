package fr.nashoba24.wolvsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import fr.nashoba24.wolvmc.WolvMC;

public class ExprWolvMCMissionOfPlayer extends SimpleExpression<Double>{
	private Expression<Player> player;
	private Expression<String> mission;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		player = (Expression<Player>) expr[1];
		mission = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "mission of player";
	}
	
	@Override
	@Nullable
	protected Double[] get(Event e) {
		return new Double[]{ WolvMC.getPlayerMission(mission.getSingle(e), player.getSingle(e).getName()) };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			WolvMC.addNbToMission(mission.getSingle(e), player.getSingle(e).getName(), (WolvMC.getPlayerMission(mission.getSingle(e), player.getSingle(e).getName()) * -1.00 ) + ((Number) delta[0]).doubleValue());
		}
		else if (mode == ChangeMode.RESET) {
			WolvMC.addNbToMission(mission.getSingle(e), player.getSingle(e).getName(), WolvMC.getPlayerMission(mission.getSingle(e), player.getSingle(e).getName()) * -1.00);
		}
		else if(mode == ChangeMode.ADD) {
			WolvMC.addNbToMission(mission.getSingle(e), player.getSingle(e).getName(), ((Number) delta[0]).doubleValue());
		}
		else if(mode == ChangeMode.REMOVE) {
			WolvMC.addNbToMission(mission.getSingle(e), player.getSingle(e).getName(), ((Number) delta[0]).doubleValue() * -1.00);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE)
			return CollectionUtils.array(Number.class);
		return null;
	}
}

