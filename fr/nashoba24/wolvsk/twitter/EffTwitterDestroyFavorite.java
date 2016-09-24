package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import fr.nashoba24.wolvsk.WolvSK;

public class EffTwitterDestroyFavorite extends Effect {
	
	private Expression<Long> id;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		id = (Expression<Long>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "unfavorite status";
	}
	
	@Override
	protected void execute(Event e) {
		if(WolvSKTwitter.tf==null) { return; }
		try {
			WolvSKTwitter.tf.getInstance().destroyFavorite(id.getSingle(e));
		} catch (TwitterException e1) {
			e1.printStackTrace();
			WolvSK.getInstance().getLogger().severe("Failed to unfavorite status: " + e1.getMessage());
		}
	}
}
