package gk.tweetsched.cron.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * PropertyService class.
 * <p>
 * Date: June 12, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
@Service
public class PropertyService {
    private static final Logger LOGGER = LogManager.getLogger(PropertyService.class);
    private static final String PROPERTIES_FILE = "tweetsched.properties";
    private Properties props;

    public PropertyService() {
        try {
            FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
            props = new Properties();
            props.load(fis);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    public String getProp(String key) {
        return props.getProperty(key);
    }
}
