package io.sited.file.domain;


import io.sited.db.Entity;
import io.sited.db.Field;
import io.sited.db.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chi
 */
@Entity(name = "file_file")
public class File {
    @Id
    public ObjectId id;
    @Field(name = "directory_id")
    public ObjectId directoryId;
    @Field(name = "path")
    public String path;
    @Field(name = "title")
    public String title;
    @Field(name = "description")
    public String description;
    @Field(name = "tags")
    public List<String> tags;
    @Field(name = "extension")
    public String extension;
    @Field(name = "length")
    public Long length;
    @Field(name = "created_time")
    public LocalDateTime createdTime;
    @Field(name = "created_by")
    public String createdBy;
    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
    @Field(name = "updated_by")
    public String updatedBy;
}
