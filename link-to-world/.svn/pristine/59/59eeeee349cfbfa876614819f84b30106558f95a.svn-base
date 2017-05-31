package io.sited.template.impl.code;

import com.google.common.collect.Maps;
import io.sited.template.impl.CodeContext;
import io.sited.template.impl.ExpressionTokenizerImpl;
import io.sited.template.impl.Model;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.template.impl.token.AddToken;
import io.sited.template.impl.token.FieldToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

/**
 * @author chi
 */
public class AddTokenCodeBuilderTest {
    @Test
    public void code() {
        Token token = new FieldToken("model", null);
        token.children.add(new FieldToken("stringValue", token));

        Token token2 = new FieldToken("model", null);
        token2.children.add(new FieldToken("stringValue", token2));

        AddToken addToken = new AddToken(null);
        addToken.addChild(token);
        addToken.addChild(token2);

        Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
        builders.put("Field", new FieldTokenCodeBuilder(builders));
        AddTokenCodeBuilder builder = new AddTokenCodeBuilder(builders);

        String code = builder.code(addToken, new CodeContext(Collections.singletonMap("model", Model.class), "context", Map.class, "value"));
        Assert.assertEquals("Object add_value1 = null;if(context==null){add_value1=\"${model.stringValue}\";}else{io.sited.template.impl.Model context_model1=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");if(context_model1==null){add_value1=null;}else{java.lang.String context_model1_stringValue2=((io.sited.template.impl.Model)context_model1).stringValue;add_value1=io.sited.template.impl.code.Objects.object(context_model1_stringValue2);}}Object add_value2 = null;if(context==null){add_value2=\"${model.stringValue}\";}else{io.sited.template.impl.Model context_model3=(io.sited.template.impl.Model)((java.util.Map)context).get(\"model\");if(context_model3==null){add_value2=null;}else{java.lang.String context_model3_stringValue4=((io.sited.template.impl.Model)context_model3).stringValue;add_value2=io.sited.template.impl.code.Objects.object(context_model3_stringValue4);}}value=io.sited.template.impl.code.Objects.add(add_value1, add_value2);", code);
    }

    @Test
    public void expr() {
        String expr = new ExpressionTokenizerImpl().tokenize("product.name + 'some'").toString();
        Assert.assertEquals("product.name+\'some\'", expr);
    }
}