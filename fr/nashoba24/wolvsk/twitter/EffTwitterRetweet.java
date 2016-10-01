package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Status;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTwitterRetweet extends Effect {
	
	private Expression<Status> status;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		status = (Expression<Status>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "retweet status";
	}
	
	@Override
	protected void execute(Event e) {
		if(WolvSKTwitter.tf==null) { return; }
		try {
			WolvSKTwitter.tf.getInstance().retweetStatus(status.getSingle(e).getId());
		} catch (TwitterException e1) {
			e1.printStackTrace();
			System.out.println("Failed to retweet: " + e1.getMessage());
		}
	}
}
