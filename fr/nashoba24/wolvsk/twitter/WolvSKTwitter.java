package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class WolvSKTwitter {

	public static TwitterFactory tf;
	
	public static void registerAll() {
		Classes.registerClass(new ClassInfo<User>(User.class, "twitterer").user("twitterer").name("twitterer").parser(new Parser<User>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public User parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(User arg0, int arg1) {
				return "@" + arg0.getScreenName();
			}

			@Override
			public String toVariableNameString(User arg0) {
				return "@" + arg0.getScreenName();
			}
		   
		}));
		Classes.registerClass(new ClassInfo<Status>(Status.class, "tweet").user("tweet").name("tweet").parser(new Parser<Status>() {

			@Override
			public String getVariableNamePattern() {
				return ".+";
			}

			@Override
			@Nullable
			public Status parse(String arg0, ParseContext arg1) {
				return null;
			}

			@Override
			public String toString(Status arg0, int arg1) {
				return arg0.getText();
			}

			@Override
			public String toVariableNameString(Status arg0) {
				return arg0.getText();
			}
		   
		}));
		Skript.registerCondition(CondIsFollowedBy.class, "%twitterer% is followed by");
		Skript.registerCondition(CondIsFollowing.class, "%twitterer% is following");
		Skript.registerCondition(CondIsPossiblySensitive.class, "%tweet% is possibly sensitive");
		Skript.registerCondition(CondIsRetweeted.class, "%tweet% is retweeted");
		Skript.registerCondition(CondIsRetweetedByMe.class, "%tweet% is retweeted by me");
		Skript.registerCondition(CondTwitterDiscoverableByEmail.class, "twitter account is discoverable by [e]mail");
		Skript.registerCondition(CondTwitterGeoEnabled.class, "twitter account is geo enabled");
		Skript.registerCondition(CondTwitterDiscoverableByEmail.class, "%tweet% is favorited");
		Skript.registerEffect(EffPostTweet.class, "tweet %string%");
		Skript.registerEffect(EffRemoveTweet.class, "destroy %tweet%");
		Skript.registerEffect(EffTwitterBlockUser.class, "block %twitterer%");
		Skript.registerEffect(EffTwitterConnect.class, "twitter connect with consumer key %string% and consumer secret %string%");
	}
	
}
