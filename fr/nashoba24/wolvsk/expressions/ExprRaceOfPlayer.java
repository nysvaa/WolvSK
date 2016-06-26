/*package fr.nashoba24.wolvsk.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvmc.WolvMC;

public class ExprRaceOfPlayer extends SimpleExpression<String> {
	private Expression<Player> player;

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2,
			ParseResult arg3) {
		player = (Expression<Player>) expr[0];
		return false;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "race of player";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{ WolvMC.getRace(player.getSingle(e).getName())};
	}

}*/

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

public class ExprRaceOfPlayer extends SimpleExpression<String>{
	private Expression<Player> player;
	
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
		player = (Expression<Player>) expr[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "race of player";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		return new String[]{ WolvMC.getRace(player.getSingle(e).getName()) };
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			WolvMC.setRace(player.getSingle(e), (String) delta[0]);
		}
		else if(mode == ChangeMode.RESET) {
			WolvMC.setRace(player.getSingle(e), "");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET)
			return CollectionUtils.array(String.class);
		return null;
	}
}
