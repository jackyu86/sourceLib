package io.sited.db;

import java.sql.Connection;

/**
 * @author chi
 */
public interface JDBCConfig {
    <T> JDBCConfig entity(Class<T> entityClass);

    Transaction transaction();

    Connection connection();
}
