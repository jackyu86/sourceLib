package io.sited.db.impl.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.Map;

/**
 * @author chi
 */
class MapCodec implements Codec<Map> {
    private final Codec<Document> documentCodec;

    MapCodec(Codec<Document> documentCodec) {
        this.documentCodec = documentCodec;
    }

    @Override
    public Map decode(BsonReader reader, DecoderContext decoderContext) {
        return documentCodec.decode(reader, decoderContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void encode(BsonWriter writer, Map value, EncoderContext encoderContext) {
        Document document = new Document();
        document.putAll(value);
        documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<Map> getEncoderClass() {
        return Map.class;
    }
}
