package fr.nashoba24.wolvsk.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class EffCreateRace extends Effect {
	
	private Expression<String> race;
	private Expression<String> prefix;
	private Expression<ItemStack> item;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		race = (Expression<String>) expr[0];
		prefix = (Expression<String>) expr[1];
		item = (Expression<ItemStack>) expr[2];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "create race";
	}
	
	@Override
	protected void execute(Event e) {
		WolvMC.addRace(race.getSingle(e), prefix.getSingle(e), item.getSingle(e));
	}
}
