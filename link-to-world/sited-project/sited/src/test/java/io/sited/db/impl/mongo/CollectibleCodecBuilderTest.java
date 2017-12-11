package io.sited.db.impl.mongo;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

/**
 * @author chi
 */
public class CollectibleCodecBuilderTest {
    @Rule
    public FongoRule fongoRule = new FongoRule();
    MongoDatabase db;
    MongoCollection<TestEntity> collection;
    MongoCollection<TestEntityWithEmbeddedEntity> collection2;

    @Before
    public void setup() {
        MongoCodecRegistry registry = new MongoCodecRegistry();
        registry.add(new CollectibleCodecBuilder<>(TestEntityWithEmbeddedEntity.class, registry).build());
        registry.add(new CollectibleCodecBuilder<>(TestEntity.class, registry).build());
        registry.add(new CodecBuilder<>(TestEntityWithoutId.class, registry).build());
        registry.add(new CollectibleCodecBuilder<>(TestEntityWithStringId.class, registry).build());
        db = fongoRule.getDatabase("mock").withCodecRegistry(registry);

        collection = db.getCollection("TestEntity", TestEntity.class);
        collection2 = db.getCollection("TestEntityWithEmbeddedEntity", TestEntityWithEmbeddedEntity.class);
    }


    @Test
    public void build() {
        TestEntity testEntity = new TestEntity();
        testEntity.name = "Name";

        collection.insertOne(testEntity);
        Assert.assertNotNull(testEntity.id);

        testEntity = collection.find().first();
        Assert.assertNotNull(testEntity.id);
        Assert.assertEquals("Name", testEntity.name);
    }

    @Test
    public void buildEmbeddedEntity() {
        TestEntityWithEmbeddedEntity entity = new TestEntityWithEmbeddedEntity();
        entity.testEntityWithoutId = new TestEntityWithoutId();

        collection2.insertOne(entity);
        Assert.assertNotNull(entity.id);

        entity = collection2.find().first();
        Assert.assertNotNull(entity.id);
        Assert.assertNotNull(entity.testEntityWithoutId.id);
    }

    @Test
    public void buildEntityWithStringId() {
        TestEntityWithStringId entity = new TestEntityWithStringId();
        String id = UUID.randomUUID().toString();
        entity.id = id;
        entity.description = "description";
        MongoCollection<TestEntityWithStringId> collection = db.getCollection("test_entity_with_string_id", TestEntityWithStringId.class);
        collection.insertOne(entity);
        Assert.assertEquals(id, entity.id);
        TestEntityWithStringId loaded = collection.find(new Document("_id", id)).first();
        Assert.assertEquals(id, loaded.id);
    }
}