package io.sited.db;

import io.sited.db.impl.jdbc.JDBCOptions;
import io.sited.db.impl.mongo.MongoOptions;

/**
 * @author chi
 */
public class DBOptions {
    public MongoOptions mongo;
    public JDBCOptions jdbc;
}
