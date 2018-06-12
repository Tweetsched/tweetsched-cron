package gk.tweetsched.cron.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Properties;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * PropertyServiceTest class.
 * <p>
 * Date: June 12, 2018
 * <p>
 *
 * @author Gleb Kosteiko.
 */
public class PropertyServiceTest {
    private static final String TEST_PROPERTY = "TEST_PROPERTY";
    private static final String TEST_PROPERTY_VALUE = "test prop value";
    @Mock
    private Properties props;
    @InjectMocks
    PropertyService propertyService = new PropertyService();

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testGetProp() {
        when(props.getProperty(TEST_PROPERTY)).thenReturn(TEST_PROPERTY_VALUE);
        String result = propertyService.getProp(TEST_PROPERTY);
        assertEquals(TEST_PROPERTY_VALUE, result);

        verify(props).getProperty(eq(TEST_PROPERTY));
        verifyNoMoreInteractions(props);
    }
}
