package gk.tweetsched.cron.service;

import redis.clients.jedis.Jedis;

import static gk.tweetsched.cron.util.Constants.REDIS_URL;
import static gk.tweetsched.cron.util.Constants.REDIS_PORT;
import static gk.tweetsched.cron.util.Constants.REDIS_PASSWORD;
import static gk.tweetsched.cron.util.Constants.TWEETS_HASH;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

import java.util.List;
import java.util.Map.Entry;

/**
 * QueueService class.
 * <p>
 * Date: May 27, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class QueueService {
    private TwitterService twitterService = new TwitterService();

    public QueueService() {}

    public void processNext() {
        Jedis jedis = new Jedis(System.getenv(REDIS_URL),
                Integer.valueOf(System.getenv(REDIS_PORT)));
        jedis.auth(System.getenv(REDIS_PASSWORD));

        List<Entry<String, String>> result = jedis.hscan(TWEETS_HASH, SCAN_POINTER_START).getResult();
        if (result.isEmpty()) {
            return;
        }
        Entry<String, String> tweetEntry = result.get(0);
        String id = tweetEntry.getKey();
        String tweet = tweetEntry.getValue();
        boolean isSuccess = twitterService.publishTweet(tweet);
        if (isSuccess) {
            jedis.hdel(TWEETS_HASH, id);
        }
        jedis.close();
    }
}
