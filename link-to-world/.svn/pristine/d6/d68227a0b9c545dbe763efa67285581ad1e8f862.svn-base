package io.sited.http.impl;

import io.sited.http.HttpModule;
import io.sited.http.ServerResponse;
import io.sited.test.SiteRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author chi
 */
public class HealthCheckControllerTest {
    @Rule
    public SiteRule siteRule = new SiteRule(new HttpModule());

    @Test
    public void healthCheck() throws Exception {
        ServerResponse response = siteRule.get("/health-check").execute();
        Assert.assertEquals(200, response.statusCode());
    }
}