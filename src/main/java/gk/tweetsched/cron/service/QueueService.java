package gk.tweetsched.cron.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.annotation.PostConstruct;

import static gk.tweetsched.cron.util.Constants.REDIS_URL;
import static gk.tweetsched.cron.util.Constants.REDIS_PORT;
import static gk.tweetsched.cron.util.Constants.REDIS_PASSWORD;
import static gk.tweetsched.cron.util.Constants.TWEETS_HASH;
import static gk.tweetsched.cron.util.Constants.MESSAGE;
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
@Service
public class QueueService {
    private static final Logger LOGGER = LogManager.getLogger(QueueService.class);
    @Autowired
    private PropertyService propService;
    @Autowired
    private TwitterService twitterService;
    private JedisPool pool;
    private Gson gson = new Gson();

    @PostConstruct
    private void init() {
        this.pool = new JedisPool(
                new JedisPoolConfig(),
                propService.getProp(REDIS_URL),
                Integer.valueOf(propService.getProp(REDIS_PORT)),
                Protocol.DEFAULT_TIMEOUT,
                propService.getProp(REDIS_PASSWORD));
    }

    public void processNext() {
        try (Jedis jedis = pool.getResource()) {
            List<Entry<String, String>> result = jedis.hscan(TWEETS_HASH, SCAN_POINTER_START).getResult();
            if (result.isEmpty()) {
                LOGGER.info("Tweets queue is empty");
                return;
            }
            Entry<String, String> tweetEntry = result.get(0);
            String id = tweetEntry.getKey();
            JsonObject tweet = gson.fromJson(tweetEntry.getValue(), JsonObject.class);
            boolean isSuccess = twitterService.publishTweet(tweet.get(MESSAGE).getAsString());
            if (isSuccess) {
                jedis.hdel(TWEETS_HASH, id);
                LOGGER.info("Tweet was posted");
            } else {
                LOGGER.error("Tweet was not posted");
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }
}
