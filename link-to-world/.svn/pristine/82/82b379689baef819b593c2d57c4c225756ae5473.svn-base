package io.sited.db.impl.mongo;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.sited.StandardException;
import io.sited.db.Entity;
import io.sited.util.JSON;
import io.sited.util.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * @author chi
 */
public class MongoInstaller<T> {
    private final Logger logger = LoggerFactory.getLogger(MongoInstaller.class);

    private final Class<T> entityClass;
    private final MongoDatabase db;

    public MongoInstaller(Class<T> entityClass, MongoDatabase db) {
        this.entityClass = entityClass;
        this.db = db;
    }

    public void installIfEmpty(String path) {
        String collectionName = collectionName();
        MongoCollection<T> collection = collection();

        if (collection.count() == 0) {
            String json = resource(path);
            List<T> list = JSON.fromJSON(json, Types.list(entityClass));
            logger.info("initialize collection, name={}, count={}", collectionName, list.size());
            list.forEach(collection::insertOne);
        }
    }

    public void install(String json) {
        String collectionName = collectionName();
        MongoCollection<T> collection = collection();

        List<T> list = JSON.fromJSON(json, Types.list(entityClass));
        logger.info("initialize collection, name={}, count={}", collectionName, list.size());
        list.forEach(collection::insertOne);
    }

    private MongoCollection<T> collection() {
        MongoCodecRegistry mongoCodecRegistry = new MongoCodecRegistry();
        mongoCodecRegistry.register(entityClass);
        return db.withCodecRegistry(mongoCodecRegistry).getCollection(collectionName(), entityClass);
    }

    private String collectionName() {
        return entityClass.getDeclaredAnnotation(Entity.class).name();
    }

    private String resource(String path) {
        try (Reader reader = new InputStreamReader(Resources.getResource(path).openStream(), Charsets.UTF_8)) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }
}
