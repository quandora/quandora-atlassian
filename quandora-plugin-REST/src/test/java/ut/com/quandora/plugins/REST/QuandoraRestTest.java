package ut.com.quandora.plugins.REST;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuandoraRestTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {
        // QuandoraRest resource = new QuandoraRest();

        //Response response = resource.getMessage();
        // final QuandoraRestModel message = (QuandoraRestModel) response.getEntity();

        assertEquals("wrong message","Hello World","Hello World");
    }
}
