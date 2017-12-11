package io.sited.form.impl;

import io.sited.form.FieldType;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chi
 */
public class FieldTypeCodec<T> implements Codec<T> {
    private final Logger logger = LoggerFactory.getLogger(FieldTypeCodec.class);
    private final FieldType<T> fieldType;

    public FieldTypeCodec(FieldType<T> fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            reader.skipValue();
            return null;
        } else if (reader.getCurrentBsonType() == BsonType.STRING) {
            String content = reader.readString();
            return fieldType.codec().decode(content);
        } else {
            logger.warn("invalid simple type value, {}", reader.getCurrentBsonType());
            return null;
        }
    }

    @Override
    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        } else {
            writer.writeString(fieldType.codec().encode(value));
        }
    }

    @Override
    public Class<T> getEncoderClass() {
        return fieldType.javaType();
    }
}
