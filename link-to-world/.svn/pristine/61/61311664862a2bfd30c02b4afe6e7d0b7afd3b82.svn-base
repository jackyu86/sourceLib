package io.sited.http.impl;

import io.sited.api.impl.WebServiceClientBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class WebServiceClientBuilderTest {
    WebServiceClientBuilder<TestWebService> builder = new WebServiceClientBuilder<>(TestWebService.class, "http://some", null, false);

    @Test
    public void build() {
        TestWebService testWebService = builder.build();
        Assert.assertNotNull(testWebService);
    }
}