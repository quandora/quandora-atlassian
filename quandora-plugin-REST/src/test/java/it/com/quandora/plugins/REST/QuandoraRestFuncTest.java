package it.com.quandora.plugins.REST;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuandoraRestFuncTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {

        assertEquals("wrong message","Hello World","Hello World");
    }
}
