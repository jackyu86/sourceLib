package io.sited.template.impl.filter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class EllipsisFilterTest {
    @Test
    public void limit() {
        EllipsisFilter filter = new EllipsisFilter();
        Object value = filter.filter("xxxxxx", new Object[]{1});
        Assert.assertEquals("x...", value);
    }
}