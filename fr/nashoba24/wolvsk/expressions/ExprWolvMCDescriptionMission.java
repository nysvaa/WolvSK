package fr.nashoba24.wolvsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import fr.nashoba24.wolvmc.WolvMC;

public class ExprWolvMCDescriptionMission extends SimpleExpression<String>{
	private Expression<String> mission;
	
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
		mission = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "description of mission";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{ WolvMC.getMissionDescr(mission.getSingle(e)) };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			WolvMC.setMissionDescr(mission.getSingle(e), (String) delta[0]);
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

