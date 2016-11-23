package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.ConfigurationBuilder;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTwitterConnect extends Effect {
	
	private Expression<String> consumer_key;
	private Expression<String> consumer_secret;
	private boolean debug = false;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		if(matchedPattern == 0) {
			debug = false;
		}
		else {
			debug = true;
		}
		consumer_key = (Expression<String>) expr[0];
		consumer_secret = (Expression<String>) expr[1];
		return true;
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "connect to twitter";
	}
	
	@Override
	protected void execute(Event e) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(debug)
		  .setOAuthConsumerKey(consumer_key.getSingle(e))
		  .setOAuthConsumerSecret(consumer_secret.getSingle(e));
		WolvSKTwitter.tf = new TwitterFactory(cb.build());
		try {
			OAuth2Token tokens = WolvSKTwitter.tf.getInstance().getOAuth2Token();
			cb.setOAuth2AccessToken(tokens.getAccessToken());
			cb.setOAuth2TokenType(tokens.getTokenType());
			AccessToken tokens2 = WolvSKTwitter.tf.getInstance().getOAuthAccessToken();
			cb.setOAuthAccessToken(tokens2.getToken());
			cb.setOAuthAccessTokenSecret(tokens2.getTokenSecret());
			WolvSKTwitter.tf = new TwitterFactory(cb.build());
			if(!new TwitterFactory(cb.build()).getInstance().verifyCredentials().isVerified()) {
				WolvSKTwitter.tf = null;
				return;
			}
		} catch (TwitterException e1) {
			e1.printStackTrace();
			WolvSKTwitter.tf = null;
			return;
		}
		WolvSKTwitter.registerEvents(debug, consumer_key.getSingle(e), consumer_secret.getSingle(e));
	}
}
