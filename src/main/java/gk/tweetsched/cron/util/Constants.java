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
    public static final String REDIS_URL = "REDIS_URL";
    public static final String REDIS_PORT = "REDIS_PORT";
    public static final String REDIS_PASSWORD = "REDIS_PASSWORD";

    public static final String DEFAULT_CRON_EXPRESSION = "0 */15 2-5 * * *";
    public static final String CRON_EXPRESSION = "CRON_EXPRESSION";

    public static final String TWEETS_HASH = "tweets";
}
