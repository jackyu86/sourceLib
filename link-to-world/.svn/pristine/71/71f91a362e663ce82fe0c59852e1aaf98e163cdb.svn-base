package io.sited.db.impl.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Period;

/**
 * @author chi
 */
public class PeriodCodec implements Codec<Period> {
    @Override
    public Period decode(BsonReader reader, DecoderContext decoderContext) {
        String value = reader.readString();
        return Period.parse("P" + value.toUpperCase());
    }

    @Override
    public void encode(BsonWriter writer, Period value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        } else {
            String content = value.toString();
            writer.writeString(content.substring("P".length()));
        }
    }

    @Override
    public Class<Period> getEncoderClass() {
        return Period.class;
    }
}
