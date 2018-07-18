package gk.tweetsched.cron.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import gk.tweetsched.dto.Tweet;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static gk.tweetsched.cron.util.Constants.PUBLISHER_URL;
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
    private static final String TEST_PUBLISH_URL = "";
    private Gson gson = new Gson();
    private Tweet testTweet = new Tweet();
    @Mock
    private PropertyService propService;
    @Mock
    private Client client;
    @Mock
    private WebResource webResource;
    @Mock
    private WebResource.Builder webResourceBuilder;
    @Mock
    ClientResponse clientResponse;
    @InjectMocks
    PublishService publishService = new PublishService();

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testPublishTweet() {
        when(propService.getProp(PUBLISHER_URL)).thenReturn(TEST_PUBLISH_URL);
        when(client.resource(TEST_PUBLISH_URL)).thenReturn(webResource);
        when(webResource.type("application/json")).thenReturn(webResourceBuilder);
        when(webResourceBuilder.post(ClientResponse.class, gson.toJson(testTweet))).thenReturn(clientResponse);
        when(clientResponse.getStatus()).thenReturn(Response.Status.OK.getStatusCode());

        boolean result = publishService.publish(testTweet);
        assertTrue(result);

        verify(propService).getProp(eq(PUBLISHER_URL));
        verify(client).resource(eq(TEST_PUBLISH_URL));
        verify(webResource).type(eq("application/json"));
        verify(webResourceBuilder).post(eq(ClientResponse.class), eq(gson.toJson(testTweet)));
        verify(clientResponse).getStatus();
        verifyNoMoreInteractions(propService, client, webResource, webResourceBuilder, clientResponse);
    }
}
