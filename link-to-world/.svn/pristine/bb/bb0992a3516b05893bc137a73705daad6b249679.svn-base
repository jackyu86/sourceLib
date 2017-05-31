package io.sited.db.impl.mongo;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.sited.db.Entity;
import io.sited.db.Id;
import io.sited.db.MongoRepository;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * @author chi
 */
public class MongoRepositoryImplTest {
    @Rule
    public FongoRule fongoRule = new FongoRule();
    MongoRepository<TestCollection> repository;

    @Before
    public void setup() {
        MongoCodecRegistry codecRegistry = new MongoCodecRegistry();
        codecRegistry.register(TestCollection.class);
        MongoDatabase db = fongoRule.getDatabase().withCodecRegistry(codecRegistry);
        MongoCollection<TestCollection> collection = db.getCollection("TestCollection", TestCollection.class);
        repository = new MongoRepositoryImpl<>(TestCollection.class, collection);

        TestCollection testCollection = new TestCollection();
        testCollection.field = "field";
        testCollection.createdBy = "test";
        testCollection.createdTime = now();
        repository.insert(testCollection);
    }

    @Test
    public void query() {
        long count = repository.query().count();
        Assert.assertNotNull(count > 0);
    }

    @Entity(name = "TestCollection")
    public static class TestCollection {
        @Id
        public ObjectId id;
        public String field;
        public LocalDateTime createdTime;
        public String createdBy;
    }
}