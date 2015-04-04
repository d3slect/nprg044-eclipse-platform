package cz.cuni.mff.d3s.nprg044.twitter.auth;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterAuthUtil {

	public static Twitter getTwitterInstance()
	{
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		// authentication credentials
		cb.setOAuthConsumerKey("******");
		cb.setOAuthConsumerSecret("******");
		cb.setOAuthAccessToken("******");
		cb.setOAuthAccessTokenSecret("******");
	
		TwitterFactory tf = new TwitterFactory(cb.build());
		
		Twitter twitter = tf.getInstance();
		
		return twitter;
	}
}
