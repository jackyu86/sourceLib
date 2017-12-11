package io.sited.db.impl.jdbc;

import java.sql.SQLException;

@FunctionalInterface
public interface TableMapper<T> {
    T map(ResultSetWrapper resultSet) throws SQLException;
}