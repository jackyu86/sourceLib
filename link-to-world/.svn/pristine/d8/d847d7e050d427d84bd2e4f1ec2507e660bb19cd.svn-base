package io.sited.db.impl.mongo;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.sited.db.Entity;
import io.sited.db.Id;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author chi
 */
public class MongoInstallerTest {
    @Rule
    public FongoRule rule = new FongoRule();
    MongoInstaller<TestInstallEntity> installer;
    MongoCollection<Document> collection;

    @Before
    public void setup() {
        MongoDatabase db = rule.getDatabase();
        installer = new MongoInstaller<>(TestInstallEntity.class, db);
        collection = db.getCollection("test_install_entity");
    }

    @Test
    public void install() {
        installer.installIfEmpty("mongo/test_install_entity.json");
        Assert.assertEquals(2, collection.count());
    }


    @Entity(name = "test_install_entity")
    public static class TestInstallEntity {
        @Id
        public ObjectId id = new ObjectId();
        public String name;
    }
}