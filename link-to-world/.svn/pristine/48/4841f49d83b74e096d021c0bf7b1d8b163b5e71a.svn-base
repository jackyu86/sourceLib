package io.sited;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author chi
 */
public class BinderTest {
    private Binder<Object> binder;

    @Before
    public void setup() {
        binder = new Binder<>(new Binder<>(null));
    }

    @Test
    public void bind() {
        binder.bind(Object.class, new Object());
        Assert.assertNotNull(binder.require(Object.class));
    }

    @Test
    public void export() {
        binder.bind(String.class, "some");
        binder.export(String.class);
        Assert.assertEquals(1, binder.exportedTypes().size());
    }

    @Test
    public void provider() {
        binder.bind(Integer.class, 1);
        Assert.assertNotNull(binder.provider(Integer.class));
    }
}