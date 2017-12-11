package io.sited.file.api.directory;

import java.util.List;

/**
 * @author chi
 */
public class DirectoryNodeResponse {
    public DirectoryResponse directory;
    public List<DirectoryNodeResponse> children;
}
