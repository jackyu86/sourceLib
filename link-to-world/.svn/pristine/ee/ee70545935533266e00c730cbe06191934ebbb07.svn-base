package io.sited.db;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * @author chi
 */
public interface MongoRepository<T> {
    Query<T> query(String field, Object value);

    Query<T> query();

    Query<T> query(Bson query);

    T get(Object id);

    List<T> batchGet(List ids);

    T insert(T entity);

    List<T> batchInsert(List<T> entities);

    T update(Object id, T entity);

    boolean delete(Object id);

    void batchDelete(List ids);

    MongoCollection<T> collection();
}
