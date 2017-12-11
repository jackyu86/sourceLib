package io.sited.template.impl;

import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.template.impl.code.AddTokenCodeBuilder;
import io.sited.template.impl.code.BooleanTokenCodeBuilder;
import io.sited.template.impl.code.CompareTokenCodeBuilder;
import io.sited.template.impl.code.DoubleTokenCodeBuilder;
import io.sited.template.impl.code.FieldTokenCodeBuilder;
import io.sited.template.impl.code.FilterTokenCodeBuilder;
import io.sited.template.impl.code.IntegerTokenCodeBuilder;
import io.sited.template.impl.code.MethodTokenCodeBuilder;
import io.sited.template.impl.code.NotTokenCodeBuilder;
import io.sited.template.impl.code.NullTokenCodeBuilder;
import io.sited.template.impl.code.StringTokenCodeBuilder;
import io.sited.template.impl.code.TernaryTokenCodeBuilder;
import io.sited.util.CodeBuilder;
import io.sited.util.DynamicInstanceBuilder;

import java.util.Map;

/**
 * @author chi
 */
public class ExpressionCompilerImpl implements ExpressionCompiler {
    private final Map<String, TokenCodeBuilder> builders = Maps.newHashMap();
    private final ExpressionEngine engine;

    public ExpressionCompilerImpl(ExpressionEngine engine) {
        this.engine = engine;
        builders.put("Field", new FieldTokenCodeBuilder(builders));
        builders.put("Integer", new IntegerTokenCodeBuilder(builders));
        builders.put("Double", new DoubleTokenCodeBuilder(builders));
        builders.put("String", new StringTokenCodeBuilder(builders));
        builders.put("Boolean", new BooleanTokenCodeBuilder(builders));
        builders.put("Add", new AddTokenCodeBuilder(builders));
        builders.put("Compare", new CompareTokenCodeBuilder(builders));
        builders.put("Ternary", new TernaryTokenCodeBuilder(builders));
        builders.put("Method", new MethodTokenCodeBuilder(builders));
        builders.put("Not", new NotTokenCodeBuilder(builders));
        builders.put("Null", new NullTokenCodeBuilder(builders));
        builders.put("Filter", new FilterTokenCodeBuilder(builders));
    }

    @Override
    public Expression compile(Token token, Map<String, Class<?>> bindings) {
        TokenCodeBuilder tokenCodeBuilder = codeBuilder(token.type);
        String tokenCode = tokenCodeBuilder.code(token, new CodeContext(bindings, "context", Map.class, "value"));
        DynamicInstanceBuilder<Expression> builder = new DynamicInstanceBuilder<>(Expression.class, Expression.class.getCanonicalName());
        builder.addField(new CodeBuilder().append("final %s engine;", ExpressionEngine.class.getCanonicalName()).build());
        builder.constructor(new Class[]{ExpressionEngine.class}, "{this.engine = $1;}");
        CodeBuilder code = new CodeBuilder();
        code.append("public Object eval(%s context) {", Object.class.getCanonicalName());
        code.append("Object value=null;");
        code.append(tokenCode);
        code.append("return value;");
        code.append("}");
        builder.addMethod(code.build());
        return builder.build(engine);
    }

    private TokenCodeBuilder codeBuilder(String type) {
        if (!builders.containsKey(type)) {
            throw new StandardException("missing code build, type={}", type);
        }
        return builders.get(type);
    }
}
