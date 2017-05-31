package io.sited.db.impl.mongo;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.sited.db.Entity;
import io.sited.db.Id;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

/**
 * @author chi
 */
public class MongoCodecRegistryTest {
    @Rule
    public FongoRule fongoRule = new FongoRule();

    MongoCodecRegistry mongoCodecRegistry;
    MongoDatabase db;

    @Before
    public void setup() {
        mongoCodecRegistry = new MongoCodecRegistry();
        mongoCodecRegistry.register(TestEntity.class);
        db = fongoRule.getDatabase().withCodecRegistry(mongoCodecRegistry);
    }

    @Test
    public void codec() {
        MongoCollection<TestEntity> collection = db.getCollection("TestEntity", TestEntity.class);
        TestEntity testEntity = new TestEntity();
        collection.insertOne(testEntity);
        Assert.assertNotNull(testEntity.id);

        TestEntity loaded = collection.find().first();
        Assert.assertNotNull(loaded);
    }

    @Entity(name = "TestEntity")
    public static class TestEntity {
        @Id
        public ObjectId id;
        public String name = "some";
        public Double doubleValue = 1d;
        public Integer integerValue = 1;
        public Long longValue = 1L;
        public LocalDateTime createdDateTime;
        public LocalTime createdTime;
        public LocalDate createdDate;
        public Period period = Period.ofYears(1);
    }
}