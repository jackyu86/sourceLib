package io.sited.file.api.impl;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import io.sited.file.api.FileRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author chi
 */
public class FolderepositoryImpl implements FileRepository {
    private final File dir;

    public FolderepositoryImpl(File dir) {
        this.dir = dir;
    }

    @Override
    public void put(String path, InputStream inputStream) throws IOException {
        File file = new File(dir, path);

        if (!file.getParentFile().exists()) {
            Files.createParentDirs(file);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            ByteStreams.copy(inputStream, fileOutputStream);
        }
    }

    @Override
    public Optional<InputStream> get(String path) throws IOException {
        File file = new File(dir, path);
        if (!file.exists()) {
            return Optional.empty();
        }
        return Optional.of(new FileInputStream(file));
    }

    @Override
    public boolean delete(String path) {
        File file = new File(dir, path);
        return file.exists() && file.delete();
    }
}
