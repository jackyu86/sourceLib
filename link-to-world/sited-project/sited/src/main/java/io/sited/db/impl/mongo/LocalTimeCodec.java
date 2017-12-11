package io.sited.db.impl.mongo;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chi
 */
public class LocalTimeCodec implements Codec<LocalTime> {
    @Override
    public LocalTime decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            reader.readNull();
            return null;
        } else {
            String time = reader.readString();
            return LocalTime.parse(time);
        }
    }

    @Override
    public void encode(BsonWriter writer, LocalTime value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        } else {
            writer.writeString(value.format(DateTimeFormatter.ISO_TIME));
        }
    }

    @Override
    public Class<LocalTime> getEncoderClass() {
        return LocalTime.class;
    }
}
