package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Model;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.FieldToken;
import io.sited.template.impl.token.NotToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;


/**
 * @author chi
 */
public class NotTokenCodeBuilderTest {
    @Test
    public void code() {
        Token token = new FieldToken("object", null);
        Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
        builders.put("Field", new FieldTokenCodeBuilder(builders));

        NotTokenCodeBuilder builder = new NotTokenCodeBuilder(builders);
        NotToken notToken = new NotToken(null);
        notToken.children.add(token);
        String code = builder.code(notToken, new CodeContext(Collections.singletonMap("model", Model.class), "context", Map.class, "value"));
        Assert.assertEquals("Object not_value1=null;if(context==null){not_value1=null;}else{java.lang.Object context_object1=((java.util.Map)context).get(\"object\");not_value1=context_object1;}value=io.sited.template.impl.code.Objects.not(not_value1);", code);
    }
}