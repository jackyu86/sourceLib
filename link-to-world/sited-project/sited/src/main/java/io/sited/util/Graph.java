package io.sited.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.sited.StandardException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author chi
 */
public class Graph<T> implements Iterable<T> {
    private final Map<T, List<T>> dependencies = Maps.newLinkedHashMap();

    public Graph<T> add(T target, List<T> dependencies) {
        this.dependencies.put(target, dependencies);

        dependencies.stream().filter(dependency -> !this.dependencies.containsKey(dependency)).forEach(dependency -> {
            this.dependencies.put(dependency, Lists.newArrayList());
        });
        return this;
    }

    public List<T> dependencies(T key) {
        Set<T> dependencies = Sets.newHashSet(this.dependencies.get(key));
        this.dependencies.get(key).forEach(t -> dependencies.addAll(dependencies(t)));
        return Lists.newArrayList(dependencies);
    }

    @Override
    public Iterator<T> iterator() {
        validate();

        final Set<T> visited = Sets.newHashSet();
        final Queue<T> queue = new LinkedList<>(dependencies.keySet());

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                while (true) {
                    T target = queue.poll();

                    boolean satisfied = true;

                    for (T dependency : dependencies.get(target)) {
                        if (!visited.contains(dependency)) {
                            satisfied = false;
                            break;
                        }
                    }

                    if (satisfied) {
                        visited.add(target);
                        return target;
                    } else {
                        queue.add(target);
                    }
                }
            }

            @Override
            public void remove() {
                throw new RuntimeException("not implemented");
            }
        };
    }

    void validate() {
        Map<T, Set<T>> overall = Maps.newHashMap();

        for (Map.Entry<T, List<T>> entry : dependencies.entrySet()) {
            if (!overall.containsKey(entry.getKey())) {
                overall.put(entry.getKey(), Sets.<T>newHashSet());
            }

            for (T dependency : entry.getValue()) {
                overall.get(entry.getKey()).add(dependency);

                if (overall.containsKey(dependency)) {
                    overall.get(entry.getKey()).addAll(overall.get(dependency));
                }

                if (!dependencies.containsKey(dependency)) {
                    throw new StandardException("missing dependency, {} -> {}", entry.getKey(), dependency);
                }
            }

            if (overall.get(entry.getKey()).contains(entry.getKey())) {
                throw new StandardException("cycle dependency, {}", entry.getKey());
            }
        }
    }
}
