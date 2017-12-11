package io.sited.http.exception;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class BadRequestExceptionTest {
    @Test
    public void message() {
        BadRequestException badRequestException = new BadRequestException("test", "test invalid");
        Assert.assertEquals("InvalidField{path=test, messageKey=test invalid, message=null, args=[]}", badRequestException.getMessage());
    }
}