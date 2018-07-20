package gk.tweetsched.cron.service;

import gk.tweetsched.dto.Tweet;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static gk.tweetsched.cron.util.Constants.PUBLISHER_SECRET;
import static gk.tweetsched.cron.util.Constants.PUBLISHER_TOKEN;
import static gk.tweetsched.cron.util.Constants.PUBLISHER_URL;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertTrue;

/**
 * PublishServiceTest class.
 * <p>
 * Date: June 12, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class PublishServiceTest {
    private static final String TEST_PUBLISH_URL = "test-url";
    private static final String TEST_PUBLISHER_TOKEN = "test-token";
    private static final String TEST_PUBLISHER_SECRET = "test-secret";
    private Tweet testTweet = new Tweet();
    @Mock
    private PropertyService propService;
    @Mock
    private Client client;
    @Mock
    private WebTarget webTarget;
    @Mock
    private Invocation.Builder builder;
    @Mock
    Response clientResponse;
    @InjectMocks
    PublishService publishService = new PublishService();

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testPublishTweet() {
        when(propService.getProp(PUBLISHER_URL)).thenReturn(TEST_PUBLISH_URL);
        when(propService.getProp(PUBLISHER_TOKEN)).thenReturn(TEST_PUBLISHER_TOKEN);
        when(propService.getProp(PUBLISHER_SECRET)).thenReturn(TEST_PUBLISHER_SECRET);
        when(client.target(TEST_PUBLISH_URL)).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.post(any(Entity.class))).thenReturn(clientResponse);
        when(clientResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        boolean result = publishService.publish(testTweet);
        assertTrue(result);

        verify(propService).getProp(eq(PUBLISHER_URL));
        verify(propService).getProp(eq(PUBLISHER_TOKEN));
        verify(propService).getProp(eq(PUBLISHER_SECRET));
        verify(client).register(any(HttpAuthenticationFeature.class));
        verify(client).target(eq(TEST_PUBLISH_URL));
        verify(webTarget).request(eq(MediaType.APPLICATION_JSON));
        verify(builder).post(any(Entity.class));
        verify(clientResponse).getStatus();
        verifyNoMoreInteractions(propService, client, webTarget, builder, clientResponse);
    }
}
