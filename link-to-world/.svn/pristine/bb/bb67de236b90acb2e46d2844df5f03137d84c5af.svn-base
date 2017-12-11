package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author chi
 */
public class DoubleTokenCodeBuilderTest {
    @Test
    public void code() {
        Token token = new Token("Double", "1234.22", null);
        String code = new DoubleTokenCodeBuilder(Maps.newHashMap()).code(token, new CodeContext(Maps.newHashMap(), "context", Map.class, "value"));
        Assert.assertEquals("value=Double.valueOf(1234.22);", code);
    }
}