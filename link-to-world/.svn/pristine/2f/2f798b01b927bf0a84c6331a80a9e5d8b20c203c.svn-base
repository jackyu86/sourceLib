package io.sited.db.impl.mongo;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "test_entity_with_string_id")
public class TestEntityWithStringId {
    @Id
    public String id;
    @Field(name = "description")
    public String description = "Description";
    @Field(name = "timestamp")
    public LocalDateTime timestamp = LocalDateTime.now();
}
