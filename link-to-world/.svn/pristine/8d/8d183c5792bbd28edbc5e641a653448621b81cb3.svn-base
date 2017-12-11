package io.sited.template.impl.code;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class ObjectsTest {
    @Test
    public void add() {
        Object value = Objects.add(1, 1);
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(2, value);

        Object value2 = Objects.add(1.0, 1.0);
        Assert.assertTrue(value2 instanceof Double);
        Assert.assertEquals(2.0, value2);

        Object value3 = Objects.add(1.0, 1);
        Assert.assertTrue(value3 instanceof Double);
        Assert.assertEquals(2.0, value3);

        Object value4 = Objects.add("value1", "value2");
        Assert.assertTrue(value4 instanceof String);
        Assert.assertEquals("value1value2", value4);
    }
}