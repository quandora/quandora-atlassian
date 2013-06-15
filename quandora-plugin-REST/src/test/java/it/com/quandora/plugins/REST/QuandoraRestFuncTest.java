package it.com.quandora.plugins.REST;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.quandora.plugins.REST.QuandoraRest;
import com.quandora.plugins.REST.QuandoraRestModel;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

public class QuandoraRestFuncTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {

        String baseUrl = System.getProperty("baseurl");
        String resourceUrl = baseUrl + "/rest/quandorarest/1.0/message";

        RestClient client = new RestClient();
        Resource resource = client.resource(resourceUrl);

        QuandoraRestModel message = resource.get(QuandoraRestModel.class);

        assertEquals("wrong message","Hello World",message.getMessage());
    }
}
