package fr.nashoba24.wolvsk.guardianbeamapi;

import javax.annotation.Nullable;

import net.jaxonbrown.guardianBeam.beam.Beam;

import org.bukkit.Location;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprEndPositionBeam extends SimpleExpression<Location>{
	
	private Expression<String> id;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		id = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "end position of beam";
	}
	
	@Override
	@Nullable
	protected Location[] get(Event e) {
		Location loc = EffCreateBeam.stoppos.get(id.getSingle(e));
		if(loc==null) {
			return null;
		}
		return new Location[]{ loc };
	}
	
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			Beam beam = EffCreateBeam.list.get(id.getSingle(e));
			if(beam==null) {
				return;
			}
			EffCreateBeam.stoppos.put(id.getSingle(e), (Location) delta[0]);
			beam.setEndingPosition((Location) delta[0]);
			beam.update();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Location.class);
		return null;
	}
}

