package io.sited.file.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author chi
 */
public interface FileRepository {
    void put(String path, InputStream inputStream) throws IOException;

    Optional<InputStream> get(String path) throws IOException;

    boolean delete(String path);
}
