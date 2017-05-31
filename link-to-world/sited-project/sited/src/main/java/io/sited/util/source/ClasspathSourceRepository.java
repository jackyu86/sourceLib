package io.sited.util.source;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import io.sited.StandardException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author chi
 */
public class ClasspathSourceRepository implements SourceRepository {
    private final String classpath;

    public ClasspathSourceRepository(String classpath) {
        int start = classpath.startsWith("/") ? 1 : 0;
        int end = classpath.endsWith("/") ? classpath.length() - 1 : classpath.length();
        this.classpath = "/" + classpath.substring(start, end);
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public Optional<Source> get(String path) {
        String classpath = this.classpath + (path.startsWith("/") ? path : "/" + path);
        try (InputStream inputStream = ClasspathSourceRepository.class.getResourceAsStream(classpath)) {
            if (inputStream == null) {
                return Optional.empty();
            } else {
                return Optional.of(new ClasspathSource(path, classpath));
            }
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public Iterator<Source> iterator() {
        return Collections.<Source>emptyList().iterator();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("classpath", classpath)
            .add("priority", priority())
            .toString();
    }

    static class ClasspathSource implements Source {
        private final String path;
        private final String classpath;
        private String hash;

        ClasspathSource(String path, String classpath) {
            this.path = path;
            this.classpath = classpath;
        }

        @Override
        public String path() {
            return path;
        }

        @Override
        public InputStream openStream() throws IOException {
            return Resources.getResource(ClasspathSourceRepository.class, classpath).openStream();
        }

        @Override
        public String text() {
            try (InputStream inputStream = openStream()) {
                return CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            } catch (IOException e) {
                throw new StandardException(e);
            }
        }

        @Override
        public byte[] bytes() {
            try (InputStream inputStream = openStream()) {
                return ByteStreams.toByteArray(inputStream);
            } catch (IOException e) {
                throw new StandardException(e);
            }
        }

        @Override
        public String hash() {
            if (hash == null) {
                hash = Hashing.md5().hashBytes(bytes()).toString().toLowerCase();
            }
            return hash;
        }
    }
}
