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
public class BooleanTokenCodeBuilderTest {
    @Test
    public void code() {
        Token token = new Token("Boolean", "true", null);
        String code = new BooleanTokenCodeBuilder(Maps.newHashMap()).code(token, new CodeContext(Maps.newHashMap(), "context", Map.class, "value"));
        Assert.assertEquals("value=Boolean.valueOf(true);", code);
    }
}