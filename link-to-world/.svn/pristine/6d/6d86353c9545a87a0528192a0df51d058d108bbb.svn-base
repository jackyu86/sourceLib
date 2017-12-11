package io.sited.util;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author chi
 */
public class JSONTest {
    @Test
    public void objectId() {
        ObjectId objectId = new ObjectId("5531effd1b92e6ef4da65a47");
        Assert.assertEquals("\"5531effd1b92e6ef4da65a47\"", JSON.toJSON(objectId));
    }

    @Test
    public void convertValue() {
        Assert.assertEquals(1, (int) JSON.convert("1", int.class));
    }

    @Test
    public void localDateTime() {
        LocalDateTime time = LocalDateTime.of(2000, 1, 1, 0, 0, 0, 1000 * 1000);
        Assert.assertEquals("\"1999-12-31T16:00:00.001Z\"", JSON.toJSON(time));
        Assert.assertEquals(time, JSON.fromJSON("\"1999-12-31T16:00:00.001Z\"", LocalDateTime.class));
    }

    @Test
    public void localDate() {
        LocalDate time = LocalDate.of(2000, 1, 1);
        Assert.assertEquals("\"1999-12-31T16:00:00Z\"", JSON.toJSON(time));
        Assert.assertEquals(time, JSON.fromJSON("\"1999-12-31T16:00:00Z\"", LocalDate.class));
    }
}
