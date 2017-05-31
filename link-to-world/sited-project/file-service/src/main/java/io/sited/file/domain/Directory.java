package io.sited.file.domain;

import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author chi
 */
@Entity(name = "file_directory")
public class Directory {
    @Id
    public ObjectId id;
    @Field(name = "parent_id")
    public ObjectId parentId;
    @Field(name = "name")
    public String name;
    @Field(name = "display_name")
    public String displayName;
    @Field(name = "display_order")
    public Integer displayOrder;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
