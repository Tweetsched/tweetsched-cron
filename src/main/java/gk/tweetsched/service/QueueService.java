package gk.tweetsched.service;

import redis.clients.jedis.Jedis;
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
    private static final String TWEETS_HASH = "tweets";
    private static final String REDIS_URL = "127.0.0.1";
    TwitterService twitterService = new TwitterService();

    public QueueService() {}

    public void processNext() {
        Jedis jedis = new Jedis(REDIS_URL);
        List<Entry<String, String>> result =  jedis.hscan(TWEETS_HASH, SCAN_POINTER_START).getResult();
        if(result.isEmpty()) {
            return;
        }
        Entry<String, String> tweetEntry = result.get(0);
        String id = tweetEntry.getKey();
        String tweet = tweetEntry.getValue();
        boolean isSuccess =  twitterService.publishTweet(tweet);
        if(isSuccess) {
            jedis.hdel(TWEETS_HASH, id);
        }
        jedis.close();
    }
}
