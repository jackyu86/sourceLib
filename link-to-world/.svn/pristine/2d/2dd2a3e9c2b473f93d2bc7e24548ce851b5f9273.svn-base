package io.sited.form;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.util.Map;

/**
 * @author chi
 */
public class Field implements JsonSerializable {
    public String name;
    public String groupName;
    public FieldType<?> type;
    public String displayName;
    public Map<String, Object> options;
    public Object defaultValue;
    public Boolean editable = true;
    public Boolean multiple = false;
    public Boolean hidden = false;
    public String displayAs;

    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", name);
        gen.writeStringField("displayName", displayName);
        gen.writeStringField("groupName", groupName);
        gen.writeStringField("type", type.name());
        gen.writeObjectField("options", options);
        gen.writeObjectField("defaultValue", defaultValue);
        gen.writeObjectField("editable", editable);
        gen.writeObjectField("multiple", multiple);
        gen.writeObjectField("hidden", hidden);
        gen.writeObjectField("displayAs", displayAs);
        gen.writeEndObject();
    }

    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {

    }
}
