package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Model;
import io.sited.template.impl.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class StringTokenCodeBuilderTest {
    @Test
    public void code() {
        StringTokenCodeBuilder builder = new StringTokenCodeBuilder(Maps.newHashMap());
        String code = builder.code(new Token("String", "some", null), new CodeContext(Collections.singletonMap("embeddedModel", Model.class), "context", Map.class, "value"));
        Assert.assertEquals("value=\"some\";", code);
    }
}