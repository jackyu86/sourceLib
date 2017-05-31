package io.sited.template.impl;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class ExpressionTokenizerImplTest {
    @Test
    public void field() throws Exception {
        ExpressionTokenizerImpl tokenizer = new ExpressionTokenizerImpl();
        Token token = tokenizer.tokenize("object.name");
        Assert.assertEquals(1, token.children.size());
        Assert.assertEquals("object", token.content);
        Assert.assertEquals("name", token.children.get(0).content);
    }

    @Test
    public void ternary() {
        ExpressionTokenizerImpl tokenizer = new ExpressionTokenizerImpl();
        Token token = tokenizer.tokenize("object.condition?object.result1:object.result2");
        Assert.assertEquals(3, token.children.size());
        Assert.assertEquals("object.condition?object.result1:object.result2", token.toString());
    }

    @Test
    public void compare() {
        ExpressionTokenizerImpl tokenizer = new ExpressionTokenizerImpl();
        Token token = tokenizer.tokenize("object.result1>=object.result2");
        Assert.assertEquals(2, token.children.size());
        Assert.assertEquals("object.result1>=object.result2", token.toString());
    }

    @Test
    public void method() {
        ExpressionTokenizerImpl tokenizer = new ExpressionTokenizerImpl();
        Token token = tokenizer.tokenize("object.method(object.result1,object.result2)");
        Assert.assertEquals(1, token.children.size());
        Assert.assertEquals("object.method(object.result1,object.result2)", token.toString());
    }
}