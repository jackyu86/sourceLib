package io.sited.file.api.file;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chi
 */
public class FileRequest {
    public ObjectId directoryId;
    public String path;
    public String title;
    public String description;
    public List<String> tags;
    public Long length;
    public String requestBy;
}
