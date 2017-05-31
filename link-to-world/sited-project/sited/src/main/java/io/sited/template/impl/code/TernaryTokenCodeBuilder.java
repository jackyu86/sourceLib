package io.sited.template.impl.code;

import io.sited.template.impl.CodeContext;
import io.sited.template.impl.Token;
import io.sited.template.impl.TokenCodeBuilder;
import io.sited.util.CodeBuilder;

import java.util.Map;

/**
 * @author chi
 */
public class TernaryTokenCodeBuilder extends TokenCodeBuilder {
    public TernaryTokenCodeBuilder(Map<String, TokenCodeBuilder> builders) {
        super(builders);
    }

    @Override
    public String code(Token token, CodeContext context) {
        CodeBuilder builder = new CodeBuilder();

        String conditionName = variable("ternary", "condition");
        builder.append("Object %s = null;", conditionName);
        Token condition = token.children.get(0);
        builder.append(builder(condition.type).code(condition, context.returnName(conditionName)));

        String value1 = variable("ternary", "value");
        builder.append("Object %s = null;", value1);
        Token operand1 = token.children.get(1);
        builder.append(builder(operand1.type).code(operand1, context.returnName(value1)));

        String value2 = variable("ternary", "value");
        builder.append("Object %s = null;", value2);
        Token operand2 = token.children.get(2);
        builder.append(builder(operand2.type).code(operand2, context.returnName(value2)));

        builder.append("if(((Boolean)%s).booleanValue()){", conditionName);
        builder.append("%s=%s;", context.returnName, value1);
        builder.append("}else{");
        builder.append("%s=%s;", context.returnName, value2);
        builder.append("}");
        return builder.build();
    }

}
