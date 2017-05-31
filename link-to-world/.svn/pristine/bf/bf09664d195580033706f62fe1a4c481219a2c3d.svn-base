package io.sited.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zaxxer.hikari.HikariDataSource;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.db.impl.EntityValidator;
import io.sited.db.impl.jdbc.EntitySchemaGenerator;
import io.sited.db.impl.jdbc.JDBCRepositoryImpl;
import io.sited.db.impl.jdbc.Table;
import io.sited.db.impl.jdbc.TransactionManager;
import io.sited.db.impl.mongo.MongoCodecRegistry;
import io.sited.db.impl.mongo.MongoOptions;
import io.sited.db.impl.mongo.MongoRepositoryImpl;
import io.sited.util.Types;
import org.bson.codecs.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author chi
 */
@ModuleInfo(name = "db")
public class DBModule extends Module {
    private final Logger logger = LoggerFactory.getLogger(DBModule.class);

    private TransactionManager transactionManager;

    private MongoDatabase database;
    private MongoCodecRegistry codecRegistry;

    @Override
    protected void configure() throws Exception {
        DBOptions options = options(DBOptions.class);

        if (options.jdbc != null) {
            logger.info("jdbc options, url={}", options.jdbc.url);

            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(options.jdbc.url);
            dataSource.setUsername(options.jdbc.username);
            dataSource.setPassword(options.jdbc.password);
            dataSource.setMaximumPoolSize(options.jdbc.maxPoolSize);
            dataSource.setMinimumIdle(options.jdbc.minPoolSize);
            this.transactionManager = new TransactionManager(dataSource);

            bind(JDBCConfig.class, this::jdbcConfig);
            export(JDBCConfig.class);
        }

        if (options.mongo != null) {
            logger.info("mongo options, url={}", options.mongo.url);
            codecRegistry = new MongoCodecRegistry();
            database = db(options.mongo);

            bind(MongoConfig.class, this::mongoConfig);
            export(MongoConfig.class);
        }
    }

    public JDBCConfig jdbcConfig(Module module) {
        return new JDBCConfig() {
            @Override
            public <T> JDBCConfig entity(Class<T> entityClass) {
                new EntityValidator(entityClass).validate();

                try (Connection connection = transactionManager.getConnection()) {
                    new EntitySchemaGenerator(connection, entityClass).createIfNoneExists();
                } catch (SQLException e) {
                    throw new StandardException(e);
                }

                module.bind(Types.generic(JDBCRepository.class, entityClass), new JDBCRepositoryImpl<>(new Table<>(entityClass), transactionManager));
                return this;
            }

            @Override
            public Transaction transaction() {
                return transactionManager.transaction();
            }

            @Override
            public Connection connection() {
                return transactionManager.getConnection();
            }
        };
    }


    private MongoConfig mongoConfig(Module module) {
        return new MongoConfig() {
            @Override
            public MongoDatabase db() {
                return database;
            }

            @Override
            public <T> MongoConfig entity(Class<T> entityClass) {
                new EntityValidator(entityClass).validate();
                codecRegistry.register(entityClass);
                MongoCollection<T> collection = db().getCollection(collectionName(entityClass), entityClass);
                module.bind(Types.generic(MongoCollection.class, entityClass), collection);
                module.bind(Types.generic(MongoRepository.class, entityClass), new MongoRepositoryImpl<>(entityClass, collection));
                return this;
            }

            @Override
            public <T> MongoConfig add(Codec<T> codec) {
                codecRegistry.add(codec);
                return this;
            }
        };
    }


    private MongoDatabase db(MongoOptions options) {
        if (options.url == null) {
            throw new StandardException("mongo.url not configured");
        } else if (options.url.startsWith("fongo:")) {
            try {
                Class<?> fongoClass = Class.forName("com.github.fakemongo.Fongo");
                Object fongo = fongoClass
                    .getDeclaredConstructor(String.class)
                    .newInstance("mock");
                Method method = fongoClass.getDeclaredMethod("getMongo");
                MongoClient mongoClient = (MongoClient) method.invoke(fongo);
                return mongoClient.getDatabase("mock").withCodecRegistry(codecRegistry);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
                throw new StandardException(e);
            }
        } else {
            MongoClientURI clientURI = new MongoClientURI(options.url);
            MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
                .codecRegistry(codecRegistry)
                .build();
            return new MongoClient(serverAddresses(clientURI), mongoClientOptions).getDatabase(clientURI.getDatabase());
        }

    }

    private List<ServerAddress> serverAddresses(MongoClientURI mongoClientURI) {
        return mongoClientURI.getHosts().stream().map(ServerAddress::new).collect(Collectors.toList());
    }

    private String collectionName(Class<?> entityClass) {
        return entityClass.getDeclaredAnnotation(Entity.class).name();
    }
}
