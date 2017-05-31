package io.sited.template.impl;

import com.google.common.collect.Maps;
import io.sited.template.impl.token.CompareToken;
import io.sited.template.impl.token.FieldToken;
import io.sited.template.impl.token.TernaryToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * @author chi
 */
public class TemplateTokenTest {
    @Test
    public void contextObjects() {
        Token token = new FieldToken("embeddedModel1", null);
        token.children.add(new FieldToken("stringValue", token));

        Token token2 = new FieldToken("embeddedModel2", null);
        token2.children.add(new FieldToken("stringValue", token2));

        CompareToken compareToken = new CompareToken("==", null);
        compareToken.addChild(token);
        compareToken.addChild(token2);

        TernaryToken ternaryToken = new TernaryToken(null);
        ternaryToken.addChild(compareToken);
        ternaryToken.addChild(new FieldToken("embeddedModel3", null));
        ternaryToken.addChild(new FieldToken("embeddedModel4", null));

        TemplateToken templateToken = new TemplateToken(ternaryToken);
        Assert.assertEquals(4, templateToken.contextObjects.size());

        Map<String, Object> context = Maps.newHashMap();
        context.put("embeddedModel1", new Object());
        context.put("embeddedModel2", "Some");
        context.put("embeddedModel3", 1);
        context.put("embeddedModel4", 1.1);

        Assert.assertEquals("embeddedModel1.stringValue==embeddedModel2.stringValue?embeddedModel3:embeddedModel4/java.lang.Object,java.lang.String,java.lang.Integer,java.lang.Double", templateToken.key(context));
    }
}