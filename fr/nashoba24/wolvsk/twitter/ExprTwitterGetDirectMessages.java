package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterGetDirectMessages extends SimpleExpression<DirectMessage>{
	
	private Expression<Integer> page;
	private boolean paging = false;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends DirectMessage> getReturnType() {
		return DirectMessage.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern == 1) {
			page = (Expression<Integer>) expr[0];
			paging = true;
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "direct messages";
	}
	
	@Override
	@Nullable
	protected DirectMessage[] get(Event e) {
		if(WolvSKTwitter.tf==null) { return null; }
		try {
			if(paging) {
				return (DirectMessage[]) WolvSKTwitter.tf.getInstance().getDirectMessages(new Paging(page.getSingle(e))).toArray();
			}
			else {
				return (DirectMessage[]) WolvSKTwitter.tf.getInstance().getDirectMessages().toArray();
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}

