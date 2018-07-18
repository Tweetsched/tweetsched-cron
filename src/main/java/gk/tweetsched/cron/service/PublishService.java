package gk.tweetsched.cron.service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import gk.tweetsched.dto.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

import static gk.tweetsched.cron.util.Constants.PUBLISHER_URL;

/**
 * PublishService class.
 * <p>
 * Date: May 27, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
@Service
public class PublishService {
    private Client client = Client.create();
    private Gson gson = new Gson();
    @Autowired
    private PropertyService propService;

    public boolean publish(Tweet tweet) {
        WebResource webResource = client.resource(propService.getProp(PUBLISHER_URL));
        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class, gson.toJson(tweet));
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
