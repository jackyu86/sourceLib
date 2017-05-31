package io.sited.db.impl.jdbc;

import io.sited.StandardException;
import io.sited.db.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chi
 */
public class TransactionImpl implements Transaction {
    private final ConnectionWrapper connection;
    private final TransactionManager manager;

    public TransactionImpl(Connection connection, TransactionManager manager) {
        this.manager = manager;
        try {
            this.connection = new ConnectionWrapper(connection);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    public void commit() throws SQLException {
        try {
            connection.commit();
        } finally {
            connection.connection.close();
            manager.clear();
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new StandardException(e);
        } finally {
            close(connection);
            manager.clear();
        }
    }

    public Connection connection() {
        return connection;
    }

    private void close(ConnectionWrapper connection) {
        try {
            connection.connection.close();
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }
}
