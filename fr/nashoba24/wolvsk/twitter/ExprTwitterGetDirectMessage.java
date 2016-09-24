package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.DirectMessage;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterGetDirectMessage extends SimpleExpression<DirectMessage>{
	
	private Expression<Long> id;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends DirectMessage> getReturnType() {
		return DirectMessage.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		id = (Expression<Long>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "direct message";
	}
	
	@Override
	@Nullable
	protected DirectMessage[] get(Event e) {
		if(WolvSKTwitter.tf==null) { return null; }
		try {
			return new DirectMessage[] { WolvSKTwitter.tf.getInstance().showDirectMessage(id.getSingle(e)) };
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}

