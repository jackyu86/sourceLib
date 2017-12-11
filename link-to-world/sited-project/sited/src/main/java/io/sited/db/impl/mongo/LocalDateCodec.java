package io.sited.db.impl.mongo;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author chi
 */
public class LocalDateCodec implements Codec<LocalDate> {
    @Override
    public LocalDate decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            reader.readNull();
            return null;
        } else {
            LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(reader.readDateTime()), ZoneId.systemDefault());
            return date.toLocalDate();
        }
    }

    @Override
    public void encode(BsonWriter writer, LocalDate value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        } else {
            writer.writeDateTime(value.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
    }

    @Override
    public Class<LocalDate> getEncoderClass() {
        return LocalDate.class;
    }
}
