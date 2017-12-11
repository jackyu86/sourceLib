package io.sited.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class PriorityListTest {
    @Test
    public void sort() {
        PriorityList<Integer> list = new PriorityList<>((o1, o2) -> o1 - o2);
        list.add(3);
        list.add(1);
        list.add(2);
        Assert.assertEquals(1, (int) list.get(0));
    }
}