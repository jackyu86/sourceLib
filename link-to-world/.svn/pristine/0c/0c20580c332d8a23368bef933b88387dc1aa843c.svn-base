package io.sited.util.source;

import com.google.common.collect.Lists;
import io.sited.util.PriorityList;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class CompositeSourceRepository implements SourceRepository {
    private final PriorityList<SourceRepository> sourceRepositories = new PriorityList<>((o1, o2) -> o1.priority() - o2.priority());

    public CompositeSourceRepository add(SourceRepository sourceRepository) {
        sourceRepositories.add(sourceRepository);
        return this;
    }

    @Override
    public Optional<Source> get(String templatePath) {
        for (SourceRepository sourceRepository : sourceRepositories) {
            Optional<Source> source = sourceRepository.get(templatePath);
            if (source.isPresent()) {
                return source;
            }
        }
        return Optional.empty();
    }

    @Override
    public Iterator<Source> iterator() {
        List<Source> sources = Lists.newArrayList();
        sourceRepositories.forEach(sourceRepository -> sources.addAll(Lists.newArrayList(sourceRepository)));
        return sources.iterator();
    }
}
