package io.sited.db.impl.mongo;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author chi
 */
public class CodecBuilderTest {
    @Rule
    public FongoRule fongoRule = new FongoRule();
    public MongoCollection<TestEntityWithoutId> collection;

    @Before
    public void setup() {
        MongoCodecRegistry registry = new MongoCodecRegistry();
        registry.add(new CodecBuilder<>(TestEntity.class, registry).build());
        collection = fongoRule.getDatabase().withCodecRegistry(registry).getCollection("TestEntity", TestEntityWithoutId.class);
    }

    @Test
    public void build() {
        TestEntityWithoutId testEntity = new TestEntityWithoutId();
        testEntity.description = "Name";
        testEntity.id = 1L;
        testEntity.timestamp = LocalDateTime.now();

        collection.insertOne(testEntity);

        testEntity = collection.find().first();
        Assert.assertNotNull(testEntity.id);
        Assert.assertEquals("Name", testEntity.description);
        Assert.assertNotNull(testEntity.timestamp);
    }
}