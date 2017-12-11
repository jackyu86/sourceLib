package io.sited.util.source;


import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import io.sited.StandardException;
import io.sited.util.Folder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author chi
 */
public class FolderSourceRepository implements SourceRepository {
    private final File dir;

    public FolderSourceRepository(File dir) {
        this.dir = dir;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public Iterator<Source> iterator() {
        if (!dir.exists()) {
            return Collections.<Source>emptyList().iterator();
        }

        Iterator<File> iterator = new Folder(dir).iterator(file -> file.isFile() && Files.getFileExtension(file.getName()).equals("html"));
        return new Iterator<Source>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Source next() {
                File file = iterator.next();
                return new FileSource(file, dir.toPath().relativize(file.toPath()).toString());
            }
        };
    }

    @Override
    public Optional<Source> get(String path) {
        File file = new File(dir, path);
        if (file.exists() && file.isFile()) {
            return Optional.of(new FileSource(file, "/" + dir.toPath().relativize(file.toPath()).toString()));
        }
        return Optional.empty();
    }

    public static class FileSource implements Source {
        public final File file;
        public final String path;
        private String hash;

        FileSource(File file, String path) {
            this.file = file;
            this.path = path;
        }

        @Override
        public String path() {
            return path;
        }

        @Override
        public InputStream openStream() throws IOException {
            return new FileInputStream(file);
        }

        @Override
        public String text() {
            try {
                return Files.toString(file, Charsets.UTF_8);
            } catch (IOException e) {
                throw new StandardException(e);
            }
        }

        @Override
        public byte[] bytes() {
            try {
                return Files.toByteArray(file);
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
