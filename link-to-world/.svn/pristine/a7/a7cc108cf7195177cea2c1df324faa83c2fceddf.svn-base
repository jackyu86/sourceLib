package io.sited.util;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author chi
 */
public class TypesTest {
    @Test
    public void className() {
        Type type = Types.generic(List.class, String.class);
        Assert.assertEquals("java.util.List", Types.className(type));
        Assert.assertEquals("java.lang.String", Types.className(String.class));
    }
}