package io.sited.db.impl.jdbc;

import io.sited.StandardException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chi
 */
public class TransactionManager {
    private final ThreadLocal<TransactionImpl> currentConnection = new ThreadLocal<>();
    private final DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        TransactionImpl transaction = currentConnection.get();
        if (transaction == null) {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                throw new StandardException(e);
            }
        } else {
            return transaction.connection();
        }
    }

    public TransactionImpl transaction() {
        TransactionImpl transaction = currentConnection.get();
        if (transaction == null) {
            try {
                Connection connection = dataSource.getConnection();
                transaction = new TransactionImpl(connection, this);
                currentConnection.set(transaction);
            } catch (SQLException e) {
                throw new StandardException(e);
            }
        }
        return transaction;
    }

    public void clear() {
        currentConnection.remove();
    }
}
