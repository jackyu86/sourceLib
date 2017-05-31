package io.sited.http.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class ContentTypesTest {
    @Test
    public void mp4() {
        Assert.assertEquals("video/mp4", ContentTypes.of("some.mp4"));
    }

    @Test
    public void defaultContentType() {
        Assert.assertEquals("application/octet-stream", ContentTypes.of("some"));
    }
}