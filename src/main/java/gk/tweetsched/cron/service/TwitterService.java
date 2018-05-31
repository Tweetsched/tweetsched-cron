package gk.tweetsched.cron.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static gk.tweetsched.cron.util.Constants.OAUTH_CONSUMER_KEY;
import static gk.tweetsched.cron.util.Constants.OAUTH_CONSUMER_SECRET;
import static gk.tweetsched.cron.util.Constants.OAUTH_ACCESS_TOKEN;
import static gk.tweetsched.cron.util.Constants.OAUTH_ACCESS_TOKEN_SECRET;

/**
 * TwitterService class.
 * <p>
 * Date: May 27, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class TwitterService {
    private static final Logger LOGGER = LogManager.getLogger(TwitterService.class);
    private TwitterFactory twitterFactory;

    public TwitterService() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(System.getenv(OAUTH_CONSUMER_KEY))
                .setOAuthConsumerSecret(System.getenv(OAUTH_CONSUMER_SECRET))
                .setOAuthAccessToken(System.getenv(OAUTH_ACCESS_TOKEN))
                .setOAuthAccessTokenSecret(System.getenv(OAUTH_ACCESS_TOKEN_SECRET));
        this.twitterFactory = new TwitterFactory(cb.build());
    }

    public boolean publishTweet(String tweet) {
        Twitter twitter = twitterFactory.getInstance();
        try {
            twitter.updateStatus(tweet);
            return true;
        } catch (TwitterException e) {
            LOGGER.error(e);
            return false;
        }
    }
}
