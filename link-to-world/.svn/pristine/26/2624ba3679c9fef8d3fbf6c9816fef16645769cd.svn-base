package io.sited.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

import io.sited.StandardException;

/**
 * @author chi
 */
public class StandardExceptionTest {
    @Test
    public void message() {
        StandardException exception = new StandardException("message {}", 1, new RuntimeException());
        Assert.assertEquals("message 1", exception.getMessage());
        Assert.assertNotNull(exception.getSuppressed());
    }

    @Test
    public void stackTrace() {
        StandardException exception = new StandardException("message {}", 1, new RuntimeException());
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
    }
}