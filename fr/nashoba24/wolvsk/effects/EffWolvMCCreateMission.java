package fr.nashoba24.wolvsk.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class EffWolvMCCreateMission extends Effect {
	
	private Expression<String> mission;
	private Expression<String> race;
	private Expression<Number> goal;
	private Expression<String> descr;
	private Expression<String> finishmsg;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		mission = (Expression<String>) expr[0];
		race = (Expression<String>) expr[1];
		goal = (Expression<Number>) expr[2];
		descr = (Expression<String>) expr[3];
		finishmsg = (Expression<String>) expr[4];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "create mission";
	}
	
	@Override
	protected void execute(Event e) {
		String s = descr.getSingle(e).replaceAll("\\*goal\\*", goal.getSingle(e).toString());
		WolvMC.addMission(mission.getSingle(e), goal.getSingle(e).doubleValue(), race.getSingle(e), s, finishmsg.getSingle(e));
	}
}
