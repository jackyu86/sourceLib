package io.sited.template.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.template.impl.token.AddToken;
import io.sited.template.impl.token.CompareToken;
import io.sited.template.impl.token.FieldToken;
import io.sited.template.impl.token.FilterToken;
import io.sited.template.impl.token.MethodToken;
import io.sited.template.impl.token.NotToken;
import io.sited.template.impl.token.TernaryToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class ExpressionCompilerImplTest {
    private final ExpressionEngine expressionEngine = new ExpressionEngine();
    private final ExpressionCompilerImpl expressionCompiler = new ExpressionCompilerImpl(expressionEngine);

    @Before
    public void setup() {
        expressionEngine.setFilter("length", (value, params) -> ((String) value).length());
    }

    @Test
    public void integerValue() {
        Token token = new Token("Integer", "1234", null);
        Expression expression = expressionCompiler.compile(token, Maps.newHashMap());
        Object value = expression.eval(Maps.newHashMap());
        Assert.assertEquals(1234, value);
    }

    @Test
    public void booleanValue() {
        Token token = new Token("Boolean", "true", null);
        Expression expression = expressionCompiler.compile(token, Maps.newHashMap());
        Object value = expression.eval(Maps.newHashMap());
        Assert.assertTrue((Boolean) value);
    }

    @Test
    public void stringValue() {
        Token token = new Token("String", "string", null);
        Expression expression = expressionCompiler.compile(token, Maps.newHashMap());
        Object value = expression.eval(Maps.newHashMap());
        Assert.assertEquals("string", value);
    }

    @Test
    public void doubleValue() {
        Token token = new Token("Double", "1.1", null);
        Expression expression = expressionCompiler.compile(token, Maps.newHashMap());
        Object value = expression.eval(Maps.newHashMap());
        Assert.assertEquals(1.1, value);
    }

    @Test
    public void add() {
        Token token = new Token("Double", "1.1", null);
        Token token2 = new Token("Double", "1.1", null);

        AddToken addToken = new AddToken(null);
        addToken.addChild(token);
        addToken.addChild(token2);

        Expression expression = expressionCompiler.compile(addToken, Maps.newHashMap());
        Object value = expression.eval(Maps.newHashMap());
        Assert.assertEquals(2.2, value);
    }

    @Test
    public void field() {
        Token objectToken = new FieldToken("model", null);
        objectToken.children.add(new FieldToken("stringValue", objectToken));
        Expression expression = expressionCompiler.compile(objectToken, Collections.singletonMap("model", Model.class));
        Object value = expression.eval(Collections.singletonMap("model", new Model()));
        Assert.assertEquals("string", value);
    }

    @Test
    public void lt() {
        Token token = new Token("Double", "1.1", null);
        Token token2 = new Token("Double", "0.0", null);

        CompareToken compareToken = new CompareToken("<", null);
        compareToken.addChild(token);
        compareToken.addChild(token2);

        Expression expression = expressionCompiler.compile(compareToken, Maps.newHashMap());
        Object value = expression.eval(Maps.newHashMap());
        Assert.assertFalse((Boolean) value);
    }

    @Test
    public void notEquals() {
        Token token = new Token("Double", "1.1", null);
        Token token2 = new Token("Double", "0.0", null);

        CompareToken compareToken = new CompareToken("!=", null);
        compareToken.addChild(token);
        compareToken.addChild(token2);

        Expression expression = expressionCompiler.compile(compareToken, Maps.newHashMap());
        Object value = expression.eval(Maps.newHashMap());
        Assert.assertTrue((Boolean) value);
    }

    @Test
    public void not() {
        Token notToken = new NotToken(null);
        Token token = new FieldToken("model", notToken);
        token.children.add(new FieldToken("booleanValue", token));
        notToken.addChild(token);

        Expression expression = expressionCompiler.compile(notToken, Collections.singletonMap("model", Model.class));
        Object value = expression.eval(Collections.singletonMap("model", new Model()));
        Assert.assertFalse((Boolean) value);
    }

    @Test
    public void ternary() {
        Token token = new FieldToken("model", null);
        token.children.add(new FieldToken("stringValue", token));

        Token token2 = new FieldToken("model", null);
        token2.children.add(new FieldToken("stringValue", token2));

        CompareToken compareToken = new CompareToken("==", null);
        compareToken.addChild(token);
        compareToken.addChild(token2);

        TernaryToken ternaryToken = new TernaryToken(null);
        ternaryToken.addChild(compareToken);
        ternaryToken.addChild(token);
        ternaryToken.addChild(token2);

        Expression expression = expressionCompiler.compile(ternaryToken, Collections.singletonMap("model", Model.class));
        Object value = expression.eval(Collections.singletonMap("model", new Model()));
        Assert.assertEquals("string", value);
    }

    @Test
    public void filter() {
        Token token = new FieldToken("model", null);
        FieldToken fieldToken = new FieldToken("stringValue", token);
        token.children.add(fieldToken);

        Token filterToken = new FilterToken("length", token, Lists.newArrayList());
        fieldToken.children.add(filterToken);
        Expression expression = expressionCompiler.compile(token, Collections.singletonMap("model", Model.class));

        Map<String, Object> context = Maps.newHashMap();
        context.put("model", new Model());
        Assert.assertEquals(6, expression.eval(context));
    }

    @Test
    public void method() {
        Token token = new FieldToken("model", null);
        token.children.add(new MethodToken("toString", token, Lists.newArrayList()));

        Expression expression = expressionCompiler.compile(token, Collections.singletonMap("model", Model.class));
        Object value = expression.eval(Collections.singletonMap("model", new Model()));
        Assert.assertEquals("toString", value);

        Token token2 = new FieldToken("model", null);
        token2.children.add(new MethodToken("length", token2, Lists.newArrayList(token)));
        Expression expression2 = expressionCompiler.compile(token2, Collections.singletonMap("model", Model.class));
        Object value2 = expression2.eval(Collections.singletonMap("model", new Model()));
        Assert.assertEquals(8, value2);
    }
}