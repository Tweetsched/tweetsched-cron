package gk.tweetsched.cron.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;

/**
 * TwitterServiceTest class.
 * <p>
 * Date: June 12, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class TwitterServiceTest {
    private static final String TEST_TWEET = "This is test tweet!";
    @Mock
    private Twitter twitter;
    @Mock
    private TwitterFactory twitterFactory;
    @InjectMocks
    TwitterService twitterService = new TwitterService();

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testPublishTweet() throws TwitterException {
        when(twitterFactory.getInstance()).thenReturn(twitter);

        boolean result = twitterService.publishTweet(TEST_TWEET);
        assertTrue(result);

        verify(twitterFactory).getInstance();
        verify(twitter).updateStatus(eq(TEST_TWEET));
        verifyNoMoreInteractions(twitterFactory, twitter);
    }
}
