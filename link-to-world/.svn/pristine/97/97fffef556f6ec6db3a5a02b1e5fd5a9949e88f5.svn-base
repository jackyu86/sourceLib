package io.sited.db.impl.mongo;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author chi
 */
public class CodecTest {
    @Rule
    public FongoRule rule = new FongoRule();

    @Test
    public void save() {
        MongoCollection<Document> collection = rule.getDatabase()
            .withCodecRegistry(new MongoCodecRegistry())
            .getCollection("some");
        collection.insertOne(new Document());
        collection.find().first();
    }

    @Test
    public void get() {
        CodecRegistry codecRegistry = MongoClient.getDefaultCodecRegistry();

        CodecProvider provider = (CodecProvider) codecRegistry;
        Codec<Document> codec = provider.get(Document.class, codecRegistry);
        Assert.assertNotNull(codec);
    }
}
