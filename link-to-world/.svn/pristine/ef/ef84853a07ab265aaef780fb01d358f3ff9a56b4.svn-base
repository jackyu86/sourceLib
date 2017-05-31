package io.sited.db.impl.mongo;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import io.sited.StandardException;
import io.sited.db.FindView;
import io.sited.db.Query;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class MongoQueryImpl<T> implements Query<T> {
    private final MongoCollection<T> collection;
    private final Bson filter;
    private Integer page;
    private Integer limit;
    private Document sort;

    public MongoQueryImpl(MongoCollection<T> collection, Bson filter) {
        this.collection = collection;
        this.filter = filter;
    }

    @Override
    public long execute() {
        throw new StandardException("not support yet");
    }

    @Override
    public long delete() {
        return collection.deleteMany(filter).getDeletedCount();
    }

    @Override
    public FindView<T> find() {
        FindIterable<T> find = collection.find(filter);
        if (page != null) {
            find.skip((page - 1) * limit);
        }
        if (limit != null) {
            find.limit(limit);
        }
        if (sort != null) {
            find.sort(sort);
        }
        FindView<T> findView = new FindView<>();
        findView.page = page == null ? 1 : page;
        long count = count();
        findView.limit = limit == null ? (int) count : limit;
        findView.total = count;
        for (T t : find) {
            findView.items.add(t);
        }
        return findView;
    }

    @Override
    public List<T> findMany() {
        FindIterable<T> find = collection.find(filter);
        if (page != null) {
            find.skip((page - 1) * limit);
        }
        if (limit != null) {
            find.limit(limit);
        }
        if (sort != null) {
            find.sort(sort);
        }
        return find.into(Lists.newArrayList());
    }

    @Override
    public Optional<T> findOne() {
        FindIterable<T> find = collection.find(filter);
        if (page != null) {
            find.skip((page - 1) * limit);
        }
        find.limit(1);
        if (sort != null) {
            find.sort(sort);
        }
        return Optional.ofNullable(find.first());
    }

    @Override
    public long count() {
        return collection.count(filter);
    }

    @Override
    public Query<T> page(int page) {
        this.page = page;
        return this;
    }

    @Override
    public Query<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Query<T> sort(String field, boolean desc) {
        if (sort == null) {
            sort = new Document();
        }
        sort.put(field, desc ? -1 : 1);
        return this;
    }
}
