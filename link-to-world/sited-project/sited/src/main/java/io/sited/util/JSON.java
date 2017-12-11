package io.sited.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;

/**
 * @author chi
 */
public final class JSON {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    static {
        JSON_MAPPER.registerModule(new JSONModule());
        JSON_MAPPER.registerModule(new AfterburnerModule());
        JSON_MAPPER.setDateFormat(new ISO8601DateFormat());
        JSON_MAPPER.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSON_MAPPER.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, false);
        JSON_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static <T> void addSerializer(Class<T> type, JsonSerializer<T> serializer) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(type, serializer);
        JSON_MAPPER.registerModule(simpleModule);
    }

    public static <T> void addDeserializer(Class<T> type, JsonDeserializer<T> deserializer) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(type, deserializer);
        JSON_MAPPER.registerModule(simpleModule);
    }

    public static <T> String toJSON(T object) {
        try {
            return JSON_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJSONBytes(Object instance) {
        try {
            return JSON_MAPPER.writeValueAsBytes(instance);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> T fromJSON(String json, Type instanceType) {
        try {
            JavaType javaType = JSON_MAPPER.getTypeFactory().constructType(instanceType);
            return JSON_MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJSON(byte[] json, Type instanceType) {
        JavaType type = JSON_MAPPER.getTypeFactory().constructType(instanceType);
        try {
            return JSON_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> T convert(Object object, Type type) {
        JavaType javaType = JSON_MAPPER.getTypeFactory().constructType(type);
        return JSON_MAPPER.convertValue(object, javaType);
    }
}
