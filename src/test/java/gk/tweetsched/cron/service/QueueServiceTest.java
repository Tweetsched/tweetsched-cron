package gk.tweetsched.cron.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gk.tweetsched.cron.util.Constants.TWEETS_HASH;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

/**
 * QueueServiceTest class.
 * <p>
 * Date: June 12, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class QueueServiceTest {
    private static final String TEST_TWEET_ID = "1";
    private static final String TEST_TWEET_MESSAGE = "This is test tweet!";
    private static final String TEST_TWEET = "{\"id\":\"1\", \"message\":\"This is test tweet!\"}";
    private ScanResult<Map.Entry<String, String>> scanResults;
    @Mock
    private Jedis jedis;
    @Mock
    private TwitterService twitterService;
    @Mock
    private JedisPool pool;
    @InjectMocks
    QueueService queueService = new QueueService();

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testProcessNextForEmptyQueue() {
        List<Map.Entry<String, String>> result = new ArrayList<>();
        scanResults = new ScanResult(new byte[0], result);

        when(pool.getResource()).thenReturn(jedis);
        when(jedis.hscan(TWEETS_HASH, SCAN_POINTER_START)).thenReturn(scanResults);

        queueService.processNext();

        verify(pool).getResource();
        verify(jedis).hscan(eq(TWEETS_HASH), eq(SCAN_POINTER_START));
        verifyNoMoreInteractions(pool);
    }

    @Test
    public void testProcessNext() {
        Map<String, String> resultsMap = new HashMap<>();
        resultsMap.put(TEST_TWEET_ID, TEST_TWEET);
        scanResults = new ScanResult(new byte[0], new ArrayList(resultsMap.entrySet()));

        when(pool.getResource()).thenReturn(jedis);
        when(jedis.hscan(TWEETS_HASH, SCAN_POINTER_START)).thenReturn(scanResults);
        when(twitterService.publishTweet(TEST_TWEET_MESSAGE)).thenReturn(true);

        queueService.processNext();

        verify(pool).getResource();
        verify(jedis).hscan(eq(TWEETS_HASH), eq(SCAN_POINTER_START));
        verify(twitterService).publishTweet(eq(TEST_TWEET_MESSAGE));
        verify(jedis).hdel(eq(TWEETS_HASH), eq(TEST_TWEET_ID));
        verifyNoMoreInteractions(pool, twitterService);
    }
}
