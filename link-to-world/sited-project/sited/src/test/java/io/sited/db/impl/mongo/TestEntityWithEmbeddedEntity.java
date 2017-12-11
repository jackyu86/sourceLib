package io.sited.db.impl.mongo;

import io.sited.db.Entity;
import io.sited.db.Id;
import org.bson.types.ObjectId;

@Entity(name = "TestEntityWithEmbeddedEntity")
public class TestEntityWithEmbeddedEntity {
    @Id
    public ObjectId id;

    public TestEntityWithoutId testEntityWithoutId;
}