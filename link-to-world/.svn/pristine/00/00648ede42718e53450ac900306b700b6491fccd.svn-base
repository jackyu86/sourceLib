package io.sited.db;

import com.mongodb.client.MongoDatabase;
import org.bson.codecs.Codec;

/**
 * @author chi
 */
public interface MongoConfig {
    MongoDatabase db();

    <T> MongoConfig entity(Class<T> entityClass);

    <T> MongoConfig add(Codec<T> codec);
}
