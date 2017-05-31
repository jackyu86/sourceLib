package io.sited.db;

import java.sql.Connection;
import java.util.List;

/**
 * @author chi
 */
public interface JDBCRepository<T> {
    Query<T> query();

    Query<T> query(String query, Object... args);

    T get(Object id);

    List<T> batchGet(List ids);

    T insert(T entity);

    List<T> batchInsert(List<T> entities);

    T update(Object id, T entity);

    boolean delete(Object id);

    void batchDelete(List ids);

    Connection connection();
}
