package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Model;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.FieldToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class FieldTokenCodeBuilderTest {
    @Test
    public void code() {
        Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
        FieldTokenCodeBuilder builder = new FieldTokenCodeBuilder(builders);
        builders.put("Field", builder);

        Token mapToken = new FieldToken("model", null);
        CodeContext context = new CodeContext(Collections.singletonMap("model", Model.class), "context", Map.class, "value");
        Assert.assertEquals("if(context==null){value=null;}else{io.sited.template.impl.Model context_model1=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");value=context_model1;}", builder.code(mapToken, context));

        Token objectToken = new FieldToken("model", null);
        objectToken.children.add(new FieldToken("stringValue", objectToken));
        Assert.assertEquals("if(context==null){value=\"${model.stringValue}\";}else{io.sited.template.impl.Model context_model2=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");if(context_model2==null){value=null;}else{java.lang.String context_model2_stringValue3=((io.sited.template.impl.Model)context_model2).stringValue;value=io.sited.template.impl.code.Objects.object(context_model2_stringValue3);}}", builder.code(objectToken, context));
    }
}