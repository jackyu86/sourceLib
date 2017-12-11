package io.sited.db.impl.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Duration;

/**
 * @author chi
 */
public class DurationCodec implements Codec<Duration> {
    @Override
    public Duration decode(BsonReader reader, DecoderContext decoderContext) {
        String value = reader.readString();
        if (value.contains("D")) {
            return Duration.parse("P" + value.toUpperCase());
        } else {
            return Duration.parse("PT" + value.toUpperCase());
        }
    }

    @Override
    public void encode(BsonWriter writer, Duration value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        } else {
            String content = value.toString();
            if (content.contains("D")) {
                writer.writeString(content.substring("P".length()));
            } else {
                writer.writeString(content.substring("PT".length()));
            }
        }
    }

    @Override
    public Class<Duration> getEncoderClass() {
        return Duration.class;
    }
}
