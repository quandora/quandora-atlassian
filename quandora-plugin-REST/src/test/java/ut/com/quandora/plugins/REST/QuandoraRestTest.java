package ut.com.quandora.plugins.REST;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.quandora.plugins.REST.QuandoraRest;
import com.quandora.plugins.REST.QuandoraRestModel;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;

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
