package fr.nashoba24.wolvsk.twitter;

import javax.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ExpressionType;
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
		Skript.registerEffect(EffTwitterCreateFavorite.class, "favorite %tweet%");
		Skript.registerEffect(EffTwitterDeleteDirectMessage.class, "delete (direct message|dm) with id %long%");
		Skript.registerEffect(EffTwitterCreateFavorite.class, "unfavorite %tweet%");
		Skript.registerEffect(EffTwitterFollow.class, "follow %twitterer%");
		Skript.registerEffect(EffTwitterFollow.class, "report %twitterer% for spam[ing]");
		Skript.registerEffect(EffTwitterRetweet.class, "retweet %tweet%");
		Skript.registerEffect(EffTwitterUnblockUser.class, "(un|de)block %twitterer%");
		Skript.registerEffect(EffTwitterUnfollow.class, "unfollow %twitterer%");
		Skript.registerExpression(ExprSearchTweets.class, Status.class, ExpressionType.PROPERTY, "search tweet[s] (for|with query) %string%");
		Skript.registerExpression(ExprStatusUser.class, User.class, ExpressionType.PROPERTY, "(author|twitterer) of %tweet%");
		Skript.registerExpression(ExprTwitterAccessLevel.class, Integer.class, ExpressionType.PROPERTY, "twitter access level");
		Skript.registerExpression(ExprTwitterFavoriteCount.class, Integer.class, ExpressionType.PROPERTY, "my twitter favorite count", "twitter favorite count of %twitterer%");
		Skript.registerExpression(ExprTwitterFollowersCount.class, Integer.class, ExpressionType.PROPERTY, "my twitter follower[s] count", "twitter follower[s] count of %twitterer%");
		Skript.registerExpression(ExprTwitterFollowersList.class, User.class, ExpressionType.PROPERTY, "my [twitter ]follower[s]", "[twitter ]follower[s] of %twitterer%");
		Skript.registerExpression(ExprTwitterFollowersCount.class, Integer.class, ExpressionType.PROPERTY, "my twitter friend[s] count", "twitter friend[s] count of %twitterer%");
		Skript.registerExpression(ExprTwitterFriendsList.class, User.class, ExpressionType.PROPERTY, "my twitter friend[s]", "twitter friend[s] of %twitterer%");
		Skript.registerExpression(ExprTwitterGetDirectMessage.class, String.class, ExpressionType.PROPERTY, "(direct message|dm) with id %long%");
		Skript.registerExpression(ExprTwitterGetDirectMessages.class, String.class, ExpressionType.PROPERTY, "direct messages");
		Skript.registerExpression(ExprTwitterGetFavorites.class, Status.class, ExpressionType.PROPERTY, "favo[u]rites tweets");
		Skript.registerExpression(ExprTwitterHomeTimeline.class, Status.class, ExpressionType.PROPERTY, "home timeline");
		Skript.registerExpression(ExprTwitterIncomingFriendships.class, Long.class, ExpressionType.PROPERTY, "incoming friendship[s]");
		Skript.registerExpression(ExprTwitterLanguage.class, String.class, ExpressionType.PROPERTY, "twitter language");
		Skript.registerExpression(ExprTwitterMentionTimeline.class, Status.class, ExpressionType.PROPERTY, "mention timeline");
		Skript.registerExpression(ExprTwitterRateLimit.class, Integer.class, ExpressionType.PROPERTY, "twitter rate limit");
		Skript.registerExpression(ExprTwitterRateLimitRemaining.class, Integer.class, ExpressionType.PROPERTY, "twitter rate limit remaining");
		Skript.registerExpression(ExprTwitterRateLimitResetTime.class, Integer.class, ExpressionType.PROPERTY, "twitter rate limit reset time");
		Skript.registerExpression(ExprTwitterRateLimitResetTime.class, Integer.class, ExpressionType.PROPERTY, "twitter time until reset rate limit");
		Skript.registerExpression(ExprTwitterRetweets.class, Status.class, ExpressionType.PROPERTY, "retweet[s] of %tweet%");
		Skript.registerExpression(ExprTwitterSearchUser.class, User.class, ExpressionType.PROPERTY, "search user[s] (for|with query) %string%");
		Skript.registerExpression(ExprTwitterSleepEndTime.class, String.class, ExpressionType.PROPERTY, "twitter sleep end time");
		Skript.registerExpression(ExprTwitterSleepStartTime.class, String.class, ExpressionType.PROPERTY, "twitter sleep start time");
		Skript.registerExpression(ExprTwitterStatusByID.class, Status.class, ExpressionType.PROPERTY, "tweet with id %long%");
		Skript.registerExpression(ExprTwitterStatusFavoriteCount.class, Integer.class, ExpressionType.PROPERTY, "favorite[s] count of %tweet%");
		Skript.registerExpression(ExprTwitterStatusRetweetsCount.class, Integer.class, ExpressionType.PROPERTY, "retweet[s] count of %tweet%");
		Skript.registerExpression(ExprTwitterStatusText.class, String.class, ExpressionType.PROPERTY, "text of %tweet%");
		Skript.registerExpression(ExprTwitterUserByID.class, User.class, ExpressionType.PROPERTY, "user with id %long%");
		Skript.registerExpression(ExprTwitterUserByScreenName.class, User.class, ExpressionType.PROPERTY, "user with [screen ]name %string%");
		Skript.registerExpression(ExprTwitterUserDescription.class, String.class, ExpressionType.PROPERTY, "description of %twitterer%");
		Skript.registerExpression(ExprTwitterUserID.class, Long.class, ExpressionType.PROPERTY, "id of %twitterer%");
		Skript.registerExpression(ExprTwitterUserName.class, String.class, ExpressionType.PROPERTY, "(username|screen name) of %twitterer%");
		Skript.registerExpression(ExprTwitterUsersBlocked.class, User.class, ExpressionType.PROPERTY, "twitter blocked users");
		Skript.registerExpression(ExprTwitterUserStatusCount.class, Integer.class, ExpressionType.PROPERTY, "status count of %twitterer%");
		Skript.registerExpression(ExprTwitterUserTimeline.class, Status.class, ExpressionType.PROPERTY, "timeline of %twitterer%");
		Skript.registerExpression(ExprTwitterOutgoingFriendships.class, Long.class, ExpressionType.PROPERTY, "outgoing friendship[s]");
	}
}
