package io.sited.form;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import io.sited.validator.Validator;

import java.io.IOException;
import java.util.List;

/**
 * @author chi
 */
public interface FieldType<T> extends Validator, JsonSerializable {
    String name();

    Class<T> javaType();

    FieldCodec<T> codec();

    List<String> displayAs();

    List<FieldOption> options();

    T normalize(Object value);

    @Override
    default void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", name());

        if (displayAs() != null) {
            gen.writeArrayFieldStart("displayAs");
            for (String view : displayAs()) {
                gen.writeString(view);
            }
            gen.writeEndArray();
        }

        if (options() != null) {
            gen.writeArrayFieldStart("options");
            for (FieldOption option : options()) {
                gen.writeObject(option);
            }
            gen.writeEndArray();
        }

        gen.writeEndObject();
    }

    @Override
    default void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
    }

    interface FieldCodec<T> {
        String encode(T data);

        T decode(String value);
    }
}
