package io.sited.form.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.sited.form.FieldType;

import java.io.IOException;

/**
 * @author chi
 */
public class FieldTypeJSONDeserializer<T> extends JsonDeserializer<T> {
    private final FieldType<T> type;

    public FieldTypeJSONDeserializer(FieldType<T> type) {
        this.type = type;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if (value == null) {
            return null;
        } else {
            return type.codec().decode(value);
        }
    }
}
