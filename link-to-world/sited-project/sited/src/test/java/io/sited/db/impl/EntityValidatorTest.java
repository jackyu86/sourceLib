package io.sited.db.impl;

import io.sited.StandardException;
import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;
import org.junit.Test;

/**
 * @author chi
 */
public class EntityValidatorTest {
    @Test
    public void validate() {
        new EntityValidator(ValidEntity.class).validate();
    }

    @Test(expected = StandardException.class)
    public void failed() {
        new EntityValidator(InvalidEntity.class).validate();
    }

    @Test(expected = StandardException.class)
    public void failedWithMethods() {
        new EntityValidator(InvalidEntityWithMethod.class).validate();
    }

    @Entity(name = "entity")
    public static class ValidEntity {
        @Id
        public ObjectId id;
        @Field(name = "name")
        public String name;
    }

    @Entity(name = "entity")
    public static class InvalidEntity {
        @Id
        public final ObjectId id = new ObjectId();
        @Field(name = "name")
        public String name;
    }

    @Entity(name = "entity")
    public static class InvalidEntityWithMethod {
        @Id
        public ObjectId id;

        public ObjectId id() {
            return id;
        }
    }
}