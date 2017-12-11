package io.sited.file.api.directory;

import org.bson.types.ObjectId;

/**
 * @author chi
 */
public class DirectoryRequest {
    public ObjectId parentId;
    public String name;
    public String displayName;
    public Integer displayOrder;
    public String requestBy;
}
