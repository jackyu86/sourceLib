package io.sited.db.impl.mongo;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOptions;
import io.sited.db.MongoRepository;
import io.sited.db.Query;
import io.sited.http.exception.NotFoundException;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class MongoRepositoryImpl<T> implements MongoRepository<T> {
    private final Class<T> type;
    private final MongoCollection<T> collection;

    public MongoRepositoryImpl(Class<T> type, MongoCollection<T> collection) {
        this.type = type;
        this.collection = collection;
    }

    @Override
    public Query<T> query(String field, Object value) {
        return new MongoQueryImpl<>(collection(), new Document(field, value));
    }

    @Override
    public Query<T> query() {
        return query(new Document());
    }

    @Override
    public Query<T> query(Bson document) {
        return new MongoQueryImpl<>(collection(), document);
    }

    @Override
    public T get(Object id) {
        T entity = collection().find(new Document("_id", id)).first();
        if (entity == null) {
            throw new NotFoundException("missing entity, type={}, id={}", type.getCanonicalName(), id);
        }
        return entity;
    }

    @Override
    public List<T> batchGet(List ids) {
        return collection().find(new Document("_id", new Document("$in", ids))).into(Lists.newArrayList());
    }

    @Override
    public T insert(T entity) {
        collection().insertOne(entity);
        return entity;
    }

    @Override
    public List<T> batchInsert(List<T> entities) {
        collection().bulkWrite(entities.stream().map(InsertOneModel::new).collect(Collectors.toList()));
        return entities;
    }

    @Override
    public T update(Object id, T entity) {
        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);
        collection().replaceOne(new Document("_id", id), entity, updateOptions);
        return entity;
    }

    @Override
    public boolean delete(Object id) {
        return collection().deleteOne(new Document("_id", id)).getDeletedCount() > 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void batchDelete(List ids) {
        List<DeleteOneModel<T>> models = Lists.newArrayList();
        ids.forEach(o -> models.add(new DeleteOneModel<>(new Document("_id", o))));
        collection().bulkWrite(models);
    }

    @Override
    public MongoCollection<T> collection() {
        return collection;
    }
}
