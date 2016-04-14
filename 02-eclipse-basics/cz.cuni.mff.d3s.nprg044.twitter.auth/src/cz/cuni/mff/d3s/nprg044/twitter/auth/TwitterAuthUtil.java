package cz.cuni.mff.d3s.nprg044.twitter.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAuthUtil {

	public static Twitter getTwitterInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();

		try (InputStream in = TwitterAuthUtil.class.getResourceAsStream("/twitter4j.properties")) {
			Properties properties = new Properties();
			properties.load(in);

			// authentication credentials
			cb.setOAuthConsumerKey(properties.getProperty("oauth.consumerKey"));
			cb.setOAuthConsumerSecret(properties.getProperty("oauth.consumerSecret"));
			cb.setOAuthAccessToken(properties.getProperty("oauth.accessToken"));
			cb.setOAuthAccessTokenSecret(properties.getProperty("oauth.accessTokenSecret"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		return twitter;
	}
}
