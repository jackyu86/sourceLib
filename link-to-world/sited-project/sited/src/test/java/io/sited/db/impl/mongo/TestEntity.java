package io.sited.db.impl.mongo;

import io.sited.db.Entity;
import io.sited.db.Id;
import org.bson.types.ObjectId;

@Entity(name = "TestEntity")
public class TestEntity {
    @Id
    public ObjectId id;
    public String name;
}