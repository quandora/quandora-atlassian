package ut.com.quandora.plugins.REST;

import org.junit.Test;
import com.quandora.plugins.REST.MyPluginComponent;
import com.quandora.plugins.REST.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}