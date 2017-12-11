package io.sited.db.impl.mongo;

import com.google.common.collect.Maps;
import com.mongodb.MongoClient;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Map;

/**
 * @author chi
 */
public class MongoCodecRegistry implements CodecRegistry {
    private final CodecRegistry registry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), this);
    private final CodecProvider provider = (CodecProvider) registry;
    private final Map<Class<?>, Codec<?>> cache = Maps.newHashMap();

    public MongoCodecRegistry() {
        add(new LocalDateTimeCodec());
        add(new DurationCodec());
        add(new LocalTimeCodec());
        add(new LocalDateCodec());
        add(new PeriodCodec());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> entityClass) {
        if (cache.containsKey(entityClass)) {
            return (Codec<T>) cache.get(entityClass);
        }

        Codec<T> codec;
        if (entityClass.getPackage().getName().startsWith("org.bson") || entityClass.getPackage().getName().startsWith("java.lang")) {
            codec = provider.get(entityClass, registry);
            if (codec != null) {
                cache.put(entityClass, codec);
                return codec;
            }
        }

        if (Enum.class.isAssignableFrom(entityClass)) {
            EnumCodec enumCodec = new EnumCodec(entityClass);
            cache.put(entityClass, enumCodec);
            return enumCodec;
        }

        if (Map.class.isAssignableFrom(entityClass)) {
            codec = (Codec<T>) new MapCodec(get(Document.class));
            cache.put(entityClass, codec);
            return codec;
        }

        codec = new CodecBuilder<>(entityClass, this).build();
        cache.put(entityClass, codec);
        return codec;
    }

    public <T> MongoCodecRegistry add(Codec<T> codec) {
        cache.put(codec.getEncoderClass(), codec);
        return this;
    }

    public <T> Codec<T> register(Class<T> entityClass) {
        Codec<T> codec = new CollectibleCodecBuilder<>(entityClass, this).build();
        cache.put(entityClass, codec);
        return codec;
    }
}
