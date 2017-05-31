package io.sited.template.impl.code;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.FilterToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author chi
 */
public class FilterTokenCodeBuilderTest {
    @Test
    public void code() {
        Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
        StringTokenCodeBuilder stringTokenCodeBuilder = new StringTokenCodeBuilder(builders);
        builders.put("String", stringTokenCodeBuilder);
        builders.put("Filter", new FilterTokenCodeBuilder(builders));

        Token token = new Token("String", "{}", null);
        Token filterToken = new FilterToken("filter", null, Lists.newArrayList());
        token.children.add(filterToken);

        Assert.assertEquals("Object string_value1=\"{}\";Object filter_variable1=null;Object[] filter_params2 = new Object[0];filter_variable1=engine.filter(\"filter\").filter(string_value1, filter_params2);value=filter_variable1;", stringTokenCodeBuilder.code(token, new CodeContext(Maps.newHashMap(), "context", Map.class, "value")));
    }
}