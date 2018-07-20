package gk.tweetsched.cron.service;

import com.google.gson.Gson;
import gk.tweetsched.dto.Tweet;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static gk.tweetsched.cron.util.Constants.PUBLISHER_SECRET;
import static gk.tweetsched.cron.util.Constants.PUBLISHER_TOKEN;
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
    private Gson gson = new Gson();
    private Client client = ClientBuilder.newClient();
    @Autowired
    private PropertyService propService;

    public boolean publish(Tweet tweet) {
        client.register(HttpAuthenticationFeature.basic(propService.getProp(PUBLISHER_TOKEN), propService.getProp(PUBLISHER_SECRET)));
        WebTarget webTarget = client.target(propService.getProp(PUBLISHER_URL));
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(gson.toJson(tweet), MediaType.APPLICATION_JSON_TYPE));
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
