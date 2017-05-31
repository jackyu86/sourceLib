package io.sited.db.impl.mongo;

import io.sited.util.Types;
import org.bson.BsonBinarySubType;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CodecHelper {
    private final Logger logger = LoggerFactory.getLogger(CodecHelper.class);
    private final Map<String, Type> mappings;
    private final CodecRegistry registry;

    public CodecHelper(Map<String, Type> mappings, CodecRegistry registry) {
        this.mappings = mappings;
        this.registry = registry;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void writeValue(final BsonWriter writer, final Object value, final EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        } else if (value instanceof Iterable) {
            writeIterable(writer, (Iterable<Object>) value, encoderContext.getChildContext());
        } else if (value instanceof Map) {
            writeMap(writer, (Map<String, Object>) value, encoderContext.getChildContext());
        } else {
            Codec codec = registry.get(value.getClass());
            encoderContext.encodeWithChildContext(codec, writer, value);
        }
    }

    public void writeMap(final BsonWriter writer, final Map<String, Object> map, final EncoderContext encoderContext) {
        writer.writeStartDocument();

        for (final Map.Entry<String, Object> entry : map.entrySet()) {
            writer.writeName(entry.getKey());
            writeValue(writer, entry.getValue(), encoderContext);
        }
        writer.writeEndDocument();
    }

    public void writeIterable(final BsonWriter writer, final Iterable<Object> list, final EncoderContext encoderContext) {
        writer.writeStartArray();
        for (final Object value : list) {
            writeValue(writer, value, encoderContext);
        }
        writer.writeEndArray();
    }

    public Object readValue(String fieldName, BsonReader reader, DecoderContext decoderContext) {
        if (!mappings.containsKey(fieldName)) {
            logger.warn("unmapped field, field={}", fieldName);
            reader.skipValue();
            return null;
        } else {
            return readValue(mappings.get(fieldName), reader, decoderContext);
        }
    }

    @SuppressWarnings("unchecked")
    public Object readValue(Type fieldType, final BsonReader reader, final DecoderContext decoderContext) {
        BsonType bsonType = reader.getCurrentBsonType();
        if (bsonType == BsonType.NULL) {
            reader.readNull();
            return null;
        } else if (bsonType == BsonType.ARRAY) {
            if (fieldType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) fieldType;
                return readList(parameterizedType.getActualTypeArguments()[0], reader, decoderContext);
            } else {
                return readList(Object.class, reader, decoderContext);
            }
        } else if (bsonType == BsonType.BINARY) {
            byte bsonSubType = reader.peekBinarySubType();
            if (bsonSubType == BsonBinarySubType.UUID_STANDARD.getValue() || bsonSubType == BsonBinarySubType.UUID_LEGACY.getValue()) {
                return registry.get(UUID.class).decode(reader, decoderContext);
            }
        }
        Class<?> type = Types.raw(fieldType);
        return registry.get(type).decode(reader, decoderContext);
    }

    public List<Object> readList(Type itemType, final BsonReader reader, final DecoderContext decoderContext) {
        reader.readStartArray();
        List<Object> list = new ArrayList<Object>();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            list.add(readValue(itemType, reader, decoderContext));
        }
        reader.readEndArray();
        return list;
    }
}