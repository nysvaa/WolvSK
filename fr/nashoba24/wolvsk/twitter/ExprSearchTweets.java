package fr.nashoba24.wolvsk.twitter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprSearchTweets extends SimpleExpression<String>{
	
	private Expression<String> search;
	
	@Override
	public boolean isSingle() {
		return false;
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		search = (Expression<String>) expr[0];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "search tweets";
	}
	
	@Override
	@Nullable
	protected String[] get(Event e) {
		if(WolvSKTwitter.tf==null) { return null; }
		try {
			List<Status> result = WolvSKTwitter.tf.getInstance().search(new Query(search.getSingle(e))).getTweets();
			ArrayList<String> tweets = new ArrayList<String>();
			for(Status tweet : result) {
				tweets.add("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
			}
			String[] l = new String[tweets.size()];
			l = tweets.toArray(l);
			return l;
		} catch (TwitterException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}

