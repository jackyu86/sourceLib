package io.sited.form.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.sited.form.FieldType;

import java.io.IOException;

/**
 * @author chi
 */
public class FieldTypeJSONSerializer<T> extends JsonSerializer<T> {
    private final FieldType<T> type;

    public FieldTypeJSONSerializer(FieldType<T> type) {
        this.type = type;
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(type.codec().encode(value));
        }
    }
}
