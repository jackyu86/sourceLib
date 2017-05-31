package io.sited.web;

import io.sited.util.source.CompositeSourceRepository;
import io.sited.util.source.Source;
import io.sited.util.source.SourceRepository;

import java.util.Optional;

/**
 * @author chi
 */
public class AssetsConfig {
    private final CompositeSourceRepository sourceRepository = new CompositeSourceRepository();
    private boolean cacheEnabled = true;

    public AssetsConfig setCacheEnabled(Boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
        return this;
    }

    public Boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public Optional<Source> get(String path) {
        return sourceRepository.get(path);
    }

    public AssetsConfig addRepository(SourceRepository repository) {
        sourceRepository.add(repository);
        return this;
    }
}
