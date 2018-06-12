package gk.tweetsched.cron.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import javax.annotation.PostConstruct;

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
@Service
public class TwitterService {
    private static final Logger LOGGER = LogManager.getLogger(TwitterService.class);
    @Autowired
    private PropertyService propService;
    private TwitterFactory twitterFactory;

    @PostConstruct
    private void init() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(false)
                .setOAuthConsumerKey(propService.getProp(OAUTH_CONSUMER_KEY))
                .setOAuthConsumerSecret(propService.getProp(OAUTH_CONSUMER_SECRET))
                .setOAuthAccessToken(propService.getProp(OAUTH_ACCESS_TOKEN))
                .setOAuthAccessTokenSecret(propService.getProp(OAUTH_ACCESS_TOKEN_SECRET));
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
