package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Model;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.CompareToken;
import io.sited.template.impl.token.FieldToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class CompareTokenCodeBuilderTest {
    @Test
    public void code() {
        Token token = new FieldToken("model", null);
        token.children.add(new FieldToken("stringValue", token));

        Token token2 = new FieldToken("model", null);
        token2.children.add(new FieldToken("stringValue", token2));

        CompareToken compareToken = new CompareToken("==", null);
        compareToken.addChild(token);
        compareToken.addChild(token2);

        Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
        builders.put("Field", new FieldTokenCodeBuilder(builders));
        CompareTokenCodeBuilder builder = new CompareTokenCodeBuilder(builders);

        String code = builder.code(compareToken, new CodeContext(Collections.singletonMap("model", Model.class), "context", Map.class, "value"));
        Assert.assertEquals("Object compare_value1 = null;if(context==null){compare_value1=\"${model.stringValue}\";}else{io.sited.template.impl.Model context_model1=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");if(context_model1==null){compare_value1=null;}else{java.lang.String context_model1_stringValue2=((io.sited.template.impl.Model)context_model1).stringValue;compare_value1=io.sited.template.impl.code.Objects.object(context_model1_stringValue2);}}Object compare_value2 = null;if(context==null){compare_value2=\"${model.stringValue}\";}else{io.sited.template.impl.Model context_model3=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");if(context_model3==null){compare_value2=null;}else{java.lang.String context_model3_stringValue4=((io.sited.template.impl.Model)context_model3).stringValue;compare_value2=io.sited.template.impl.code.Objects.object(context_model3_stringValue4);}}value=io.sited.template.impl.code.Objects.equals(compare_value1, compare_value2);", code);

    }
}