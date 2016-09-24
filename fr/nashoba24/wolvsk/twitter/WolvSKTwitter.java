package fr.nashoba24.wolvsk.twitter;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

public class WolvSKTwitter {

	public static TwitterFactory tf;
	
	public static void registerAll() {
		
	}
	
	public static void registerListener(Configuration conf) {
		StatusListener listener = new StatusListener(){
			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStatus(Status arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		TwitterStream twitterStream = new TwitterStreamFactory(conf).getInstance();
		twitterStream.addListener(listener);
		twitterStream.sample();
	}
	
}
