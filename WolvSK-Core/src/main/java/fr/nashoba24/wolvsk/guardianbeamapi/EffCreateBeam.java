package fr.nashoba24.wolvsk.guardianbeamapi;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.jaxonbrown.guardianBeam.beam.Beam;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.HashMap;

public class EffCreateBeam extends Effect {

	public static HashMap<String, Beam> list = new HashMap<String, Beam>();
	public static HashMap<String, Location> startpos = new HashMap<String, Location>();
	public static HashMap<String, Location> stoppos = new HashMap<String, Location>();
	
	private Expression<Location> loc1;
	private Expression<Location> loc2;
	private Expression<String> id;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern==0) {
			loc1 = (Expression<Location>) expr[0];
			loc2 = (Expression<Location>) expr[1];
			id = (Expression<String>) expr[2];
		}
		else if(matchedPattern==1) {
			loc1 = (Expression<Location>) expr[1];
			loc2 = (Expression<Location>) expr[2];
			id = (Expression<String>) expr[0];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "create guardian beam";
	}
	
	@Override
	protected void execute(Event e) {
		if(list.containsKey(id.getSingle(e))) {
			Beam beam = EffCreateBeam.list.get(id.getSingle(e));
			EffCreateBeam.list.remove(id.getSingle(e));
			EffCreateBeam.startpos.remove(id.getSingle(e));
			EffCreateBeam.stoppos.remove(id.getSingle(e));
			beam.stop();
		}
		Beam beam = new Beam(loc1.getSingle(e), loc2.getSingle(e));
		list.put(id.getSingle(e), beam);
		startpos.put(id.getSingle(e), loc1.getSingle(e));
		stoppos.put(id.getSingle(e), loc2.getSingle(e));
		beam.start();
	}
}
