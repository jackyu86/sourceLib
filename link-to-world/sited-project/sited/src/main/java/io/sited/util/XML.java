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
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.google.common.base.Charsets;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;

/**
 * @author chi
 */
public class XML {
    private static final ObjectMapper XML_MAPPER = new XmlMapper(new JacksonXmlModule());

    static {
        XML_MAPPER.registerModule(new JSONModule());
        XML_MAPPER.setDateFormat(new ISO8601DateFormat());
        XML_MAPPER.setAnnotationIntrospector(new JaxbAnnotationIntrospector(TypeFactory.defaultInstance()));
        XML_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        XML_MAPPER.configure(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME, false);
        XML_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    public static <T> void addSerializer(Class<T> type, JsonSerializer<T> serializer) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(type, serializer);
        XML_MAPPER.registerModule(simpleModule);
    }

    public static <T> void addDeserializer(Class<T> type, JsonDeserializer<T> deserializer) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(type, deserializer);
        XML_MAPPER.registerModule(simpleModule);
    }

    public static <T> String toXML(T instance) {
        try {
            return XML_MAPPER.writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toXMLBytes(Object instance) {
        try {
            return XML_MAPPER.writeValueAsBytes(instance);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> T fromXML(String xml, Type type) {
        try {
            JavaType javaType = XML_MAPPER.getTypeFactory().constructType(type);
            return XML_MAPPER.readValue(xml, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromXML(byte[] xml, Type type) {
        return fromXML(new String(xml, Charsets.UTF_8), type);
    }
}
