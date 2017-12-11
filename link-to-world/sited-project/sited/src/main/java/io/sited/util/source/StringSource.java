package io.sited.util.source;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author chi
 */
public class StringSource implements Source {
    private final String path;
    private final String content;
    private String hash;

    public StringSource(String path, String content) {
        this.path = path;
        this.content = content;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public InputStream openStream() throws IOException {
        return new ByteArrayInputStream(content.getBytes(Charsets.UTF_8));
    }

    @Override
    public String text() {
        return content;
    }

    @Override
    public byte[] bytes() {
        return content.getBytes(Charsets.UTF_8);
    }


    @Override
    public String hash() {
        if (hash == null) {
            hash = Hashing.md5().hashBytes(bytes()).toString().toLowerCase();
        }
        return hash;
    }
}
