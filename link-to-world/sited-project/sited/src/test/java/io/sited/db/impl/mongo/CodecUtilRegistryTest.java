package io.sited.db.impl.mongo;

import com.github.fakemongo.junit.FongoRule;
import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.sited.db.Entity;
import io.sited.db.Id;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author chi
 */
public class CodecUtilRegistryTest {
    @Rule
    public FongoRule fongoRule = new FongoRule();
    MongoCodecRegistry codecRegistry = new MongoCodecRegistry();
    MongoCollection<User> collection;

    @Before
    public void setup() {
        codecRegistry.register(User.class);
        MongoDatabase db = fongoRule.getDatabase().withCodecRegistry(codecRegistry);

        collection = db.getCollection("User", User.class);
    }

    @Test
    public void save() {
        User user = new User();
        user.username = "some";
        user.password = "some";
        user.gender = Gender.FEMALE;

        user.children = Lists.newArrayList();
        User child = new User();
        child.username = "some";
        user.children.add(child);

        collection.insertOne(user);
        Assert.assertNotNull(user.id);
    }

    public enum Gender {
        FEMALE
    }

    @Entity(name = "user")
    public static class User {
        @Id
        public ObjectId id;
        public String username;
        public String password;
        public List<User> children;
        public Integer age = 1;
        public Long number = 1L;
        public Double d = 1d;
        public List<DayOfWeek> genders = Lists.newArrayList(DayOfWeek.FRIDAY);
        public Gender gender;
        public LocalDateTime localDateTime = LocalDateTime.now();
        public Duration duration = Duration.ofHours(1);
        public LocalTime localTime = LocalTime.now();
    }
}