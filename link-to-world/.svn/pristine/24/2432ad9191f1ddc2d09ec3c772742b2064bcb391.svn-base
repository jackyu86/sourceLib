package io.sited.template.impl.attribute;

import com.google.common.collect.Sets;
import io.sited.template.Attribute;
import io.sited.template.AttributeWriter;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

/**
 * @author chi
 */
public class AttributeImpl implements Attribute {
    private static final Set<String> BOOL_ATTRIBUTES = Sets.newHashSet("checked", "selected", "disabled", "readonly", "multiple", "ismap",
        "defer", "declare", "noresize", "nowrap", "noshade", "compact");
    private final String name;
    private final String value;
    private final String defaultValue;
    private final AttributeWriter attributeWriter;

    public AttributeImpl(String name, String value, String defaultValue, AttributeWriter attributeWriter) {
        this.name = name;
        this.value = value;
        this.defaultValue = defaultValue;
        this.attributeWriter = attributeWriter;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String value() {
        return value;
    }

    public String rawValue() {
        return defaultValue;
    }

    @Override
    public boolean isBoolAttribute() {
        return BOOL_ATTRIBUTES.contains(name);
    }

    @Override
    public void output(TemplateContext context, Writer writer) throws IOException {
        attributeWriter.output(this, context, writer);
    }

    @Override
    public Object eval(Map<String, Object> bindings) {
        return attributeWriter.eval(this, bindings);
    }
}
