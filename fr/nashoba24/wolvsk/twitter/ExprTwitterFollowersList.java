package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.User;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprTwitterFollowersList extends SimpleExpression<User>{
	
	private Expression<User> user;
	private boolean self = true;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends User> getReturnType() {
		return User.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern == 1) {
			self = false;
			user = (Expression<User>) expr[0];
		}
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "followers";
	}
	
	@Override
	@Nullable
	protected User[] get(Event e) {
		if(WolvSKTwitter.tf==null) { return null; }
		try {
			if(self) {
				return (User[]) WolvSKTwitter.tf.getInstance().getFollowersList(WolvSKTwitter.tf.getInstance().getId(), -1).toArray();
			}
			else {
				return (User[]) WolvSKTwitter.tf.getInstance().getFollowersList(user.getSingle(e).getId(), -1).toArray();
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}

