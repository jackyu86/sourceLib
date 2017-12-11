package io.sited.db.impl.mongo;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 *
 */
public class EnumCodec<T extends Enum<T>> implements Codec<T> {
    private final Class<T> enumClass;

    public EnumCodec(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public void encode(BsonWriter writer, T value, EncoderContext context) {
        if (value == null) {
            writer.writeNull();
        } else {
            writer.writeString(value.name());
        }
    }

    @Override
    public T decode(BsonReader reader, DecoderContext context) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            reader.readNull();
            return null;
        } else {
            return Enum.valueOf(enumClass, reader.readString());
        }
    }

    @Override
    public Class<T> getEncoderClass() {
        return enumClass;
    }
}
