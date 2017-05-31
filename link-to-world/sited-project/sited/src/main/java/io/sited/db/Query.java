package io.sited.db;

import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public interface Query<T> {
    long execute();

    long delete();

    FindView<T> find();

    List<T> findMany();

    Optional<T> findOne();

    long count();

    Query<T> page(int page);

    Query<T> limit(int limit);

    Query<T> sort(String field, boolean desc);

    default Query<T> sort(String field) {
        return sort(field, false);
    }
}
