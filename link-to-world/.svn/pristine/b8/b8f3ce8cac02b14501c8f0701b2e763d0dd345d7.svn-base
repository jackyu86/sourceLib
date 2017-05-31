package io.sited;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class SitedTest {
    @Test
    public void properties() {
        System.setProperty("sited.host", "1.1.1.1");
        Sited.Application app = Sited.app();
        Assert.assertEquals("1.1.1.1", app.host);
        Assert.assertEquals(9090, app.port);
    }
}