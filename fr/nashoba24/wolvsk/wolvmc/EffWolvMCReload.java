package fr.nashoba24.wolvsk.wolvmc;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class EffWolvMCReload extends Effect {
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "wolvmc reload";
	}
	
	@Override
	protected void execute(Event e) {
		WolvMC.reloadConfig(true);
	}
}
