package fr.nashoba24.wolvsk.guardianbeamapi;

import javax.annotation.Nullable;

import net.jaxonbrown.guardianBeam.beam.Beam;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffStopBeam extends Effect {
	
	private Expression<String> id;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		id = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "stop guardian beam";
	}
	
	@Override
	protected void execute(Event e) {
		Beam beam = EffCreateBeam.list.get(id.getSingle(e));
		if(beam==null) {
			return;
		}
		EffCreateBeam.list.remove(id.getSingle(e));
		EffCreateBeam.startpos.remove(id.getSingle(e));
		EffCreateBeam.stoppos.remove(id.getSingle(e));
		beam.stop();
	}
}
