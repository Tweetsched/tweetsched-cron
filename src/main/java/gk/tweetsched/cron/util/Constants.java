package gk.tweetsched.cron.util;

/**
 * Constants class.
 * <p>
 * Date: May 28, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class Constants {
    public static final String REDIS_URL = "redis-url";
    public static final String REDIS_PORT = "redis-port";
    public static final String REDIS_PASSWORD = "redis-password";
    public static final String PUBLISHER_URL = "publisher-url";
    public static final String PUBLISHER_TOKEN = "publisher-token";
    public static final String PUBLISHER_SECRET = "publisher-secret";
    public static final String DEFAULT_CRON_EXPRESSION = "0 0 1-3 * * *";
    public static final String CRON_EXPRESSION = "cron-expression";
    public static final String TWEETS_HASH = "tweets";
}
