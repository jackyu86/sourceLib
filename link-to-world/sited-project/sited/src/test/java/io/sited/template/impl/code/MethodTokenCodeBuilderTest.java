package io.sited.template.impl.code;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Model;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.FieldToken;
import io.sited.template.impl.token.MethodToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class MethodTokenCodeBuilderTest {
    @Test
    public void code() {
        Token token = new FieldToken("model", null);
        token.children.add(new MethodToken("toString", token, Lists.newArrayList()));

        Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
        FieldTokenCodeBuilder fieldTokenCodeBuilder = new FieldTokenCodeBuilder(builders);
        builders.put("Field", fieldTokenCodeBuilder);
        builders.put("Method", new MethodTokenCodeBuilder(builders));

        Assert.assertEquals("if(context==null){value=\"${model.toString()}\";}else{io.sited.template.impl.Model context_model1=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");value=io.sited.template.impl.code.Objects.object(context_model1.toString());}", fieldTokenCodeBuilder.code(token, new CodeContext(Collections.singletonMap("model", Model.class), "context", Map.class, "value")));
    }
}