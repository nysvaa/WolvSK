package fr.nashoba24.wolvsk.minigames;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffMakeLeaveArena extends Effect {
	
	private Expression<Player> p;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		p = (Expression<Player>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "make player leave arena";
	}
	
	@Override
	protected void execute(Event e) {
		if(p.getSingle(e)!=null) 
		Minigames.leave(p.getSingle(e), true, false, null);
	}
}
