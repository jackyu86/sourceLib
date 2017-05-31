package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Model;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.CompareToken;
import io.sited.template.impl.token.FieldToken;
import io.sited.template.impl.token.TernaryToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class TernaryTokenCodeBuilderTest {
    @Test
    public void code() {
        Token token = new FieldToken("model", null);
        token.children.add(new FieldToken("stringValue", token));

        Token token2 = new FieldToken("model", null);
        token2.children.add(new FieldToken("stringValue", token2));

        CompareToken compareToken = new CompareToken("==", null);
        compareToken.addChild(token);
        compareToken.addChild(token2);

        TernaryToken ternaryToken = new TernaryToken(null);
        ternaryToken.addChild(compareToken);
        ternaryToken.addChild(new FieldToken("model", null));
        ternaryToken.addChild(new FieldToken("model", null));

        Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
        builders.put("Field", new FieldTokenCodeBuilder(builders));
        builders.put("Compare", new CompareTokenCodeBuilder(builders));
        TernaryTokenCodeBuilder builder = new TernaryTokenCodeBuilder(builders);
        String code = builder.code(ternaryToken, new CodeContext(Collections.singletonMap("model", Model.class), "context", Map.class, "value"));
        Assert.assertEquals("Object ternary_condition1 = null;Object compare_value1 = null;if(context==null){compare_value1=\"${model.stringValue}\";}else{io.sited.template.impl.Model context_model1=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");if(context_model1==null){compare_value1=null;}else{java.lang.String context_model1_stringValue2=((io.sited.template.impl.Model)context_model1).stringValue;compare_value1=io.sited.template.impl.code.Objects.object(context_model1_stringValue2);}}Object compare_value2 = null;if(context==null){compare_value2=\"${model.stringValue}\";}else{io.sited.template.impl.Model context_model3=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");if(context_model3==null){compare_value2=null;}else{java.lang.String context_model3_stringValue4=((io.sited.template.impl.Model)context_model3).stringValue;compare_value2=io.sited.template.impl.code.Objects.object(context_model3_stringValue4);}}ternary_condition1=io.sited.template.impl.code.Objects.equals(compare_value1, compare_value2);Object ternary_value2 = null;if(context==null){ternary_value2=null;}else{io.sited.template.impl.Model context_model5=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");ternary_value2=context_model5;}Object ternary_value3 = null;if(context==null){ternary_value3=null;}else{io.sited.template.impl.Model context_model6=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");ternary_value3=context_model6;}if(((Boolean)ternary_condition1).booleanValue()){value=ternary_value2;}else{value=ternary_value3;}", code);
    }
}