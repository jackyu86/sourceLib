package io.sited.util.source;

import java.util.Optional;

/**
 * @author chi
 */
public interface SourceRepository extends Iterable<Source> {
    default int priority() {
        return 0;
    }

    Optional<Source> get(String path);
}
