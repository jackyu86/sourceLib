package io.sited.test;

import com.google.common.io.Files;
import io.sited.Site;
import io.sited.StandardException;
import io.sited.http.HttpMethod;
import io.sited.test.impl.MockRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author chi
 */
public class MockSite extends Site {
    private final Logger logger = LoggerFactory.getLogger(MockSite.class);

    public MockSite() {
        super("localhost", Files.createTempDir(), profile("site.yml"));
    }

    public MockRequestBuilder get(String path) {
        return new MockRequestBuilder(HttpMethod.GET, "http://" + host(), path, this);
    }

    public MockRequestBuilder post(String path) {
        return new MockRequestBuilder(HttpMethod.POST, "http://" + host(), path, this);
    }

    public MockRequestBuilder put(String path) {
        return new MockRequestBuilder(HttpMethod.PUT, "http://" + host(), path, this);
    }

    public MockRequestBuilder delete(String path) {
        return new MockRequestBuilder(HttpMethod.DELETE, "http://" + host(), path, this);
    }

    @Override
    public void stop() {
        try {
            File dir = dir();
            if (dir.exists()) {
                logger.debug("delete dir {}", dir);
                java.nio.file.Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                        throws IOException {
                        dir.toFile().delete();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        file.toFile().delete();
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        } catch (IOException e) {
            throw new StandardException(e);
        }
        super.stop();
    }
}
