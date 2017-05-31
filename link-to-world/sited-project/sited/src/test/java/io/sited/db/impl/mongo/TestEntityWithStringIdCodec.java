package io.sited.db.impl.mongo;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

/**
 * @author chi
 */
public class TestEntityWithStringIdCodec implements CollectibleCodec<TestEntityWithStringId> {
    @Override
    public TestEntityWithStringId generateIdIfAbsentFromDocument(TestEntityWithStringId document) {
        document.id = UUID.randomUUID().toString();
        return document;
    }

    @Override
    public boolean documentHasId(TestEntityWithStringId document) {
        return document.id != null;
    }

    @Override
    public BsonValue getDocumentId(TestEntityWithStringId document) {
        return new BsonString(document.id);
    }

    @Override
    public TestEntityWithStringId decode(BsonReader reader, DecoderContext decoderContext) {
        TestEntityWithStringId entity = new TestEntityWithStringId();
        reader.readStartDocument();

        while (reader.readBsonType() != org.bson.BsonType.END_OF_DOCUMENT) {
            String name = reader.readName();
            if ("_id".equals(name)) {
                entity.id = reader.readString();
            } else if ("description".equals(name)) {
                entity.description = reader.readString();
            } else {
                reader.skipValue();
            }
        }

        reader.readEndDocument();
        return entity;
    }

    @Override
    public void encode(BsonWriter writer, TestEntityWithStringId value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeString("_id", value.id);
        writer.writeString("description", value.description);
        writer.writeEndDocument();
    }

    @Override
    public Class<TestEntityWithStringId> getEncoderClass() {
        return TestEntityWithStringId.class;
    }
}
