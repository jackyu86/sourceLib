package io.sited.util.source;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author chi
 */
public interface Source {
    String path();

    InputStream openStream() throws IOException;

    String text();

    byte[] bytes();

    String hash();
}
