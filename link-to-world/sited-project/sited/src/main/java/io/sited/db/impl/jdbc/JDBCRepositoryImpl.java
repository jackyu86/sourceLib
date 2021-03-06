package io.sited.db.impl.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.google.common.collect.Lists;

import io.sited.StandardException;
import io.sited.db.JDBCRepository;
import io.sited.db.Query;
import io.sited.db.impl.jdbc.dialect.DBFuctionFactroy;
import io.sited.db.impl.jdbc.dialect.FuncAdapter;

/**
 * @author chi
 */
public class JDBCRepositoryImpl<T> implements JDBCRepository<T> {
    private final Table<T> table;
    private final TransactionManager transactionManager;
    protected FuncAdapter sqlUtil;

    public JDBCRepositoryImpl(Table<T> table, TransactionManager transactionManager) {
        this.table = table;
        this.transactionManager = transactionManager;
        this.sqlUtil = DBFuctionFactroy.getFuncAdapter(this.connection());
    }

    @Override
    public Query<T> query() {
        return new JDBCQueryImpl<>(table, transactionManager, null);
    }

    public Query<T> query(String query, Object... args) {
        return new JDBCQueryImpl<>(table, transactionManager, query, args);
    }

    public T get(Object id) {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.getSQL(connection))) {
        	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (id == null)) {
				statement.setNull(1, java.sql.Types.VARCHAR);
			}else{
				statement.setObject(1, id);
			}
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return table.mapper.map(new ResultSetWrapper(resultSet));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    public List<T> batchGet(List ids) {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.batchGetSQL(ids.size(), connection))) {
            for (int i = 0; i < ids.size(); i++) {
            	Object obj = ids.get(i);
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (obj == null)) {
    				statement.setNull(i + 1, java.sql.Types.VARCHAR);
    				continue;
    			}
                statement.setObject(i + 1, obj);
            }
            ResultSet resultSet = statement.executeQuery();
            List<T> results = Lists.newArrayList();
            while (resultSet.next()) {
                results.add(table.mapper.map(new ResultSetWrapper(resultSet)));
            }
            return results;
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }


    public T insert(T entity) {
        if (table.id.isAutoGenerated()) {
            try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.insertSQL(), Statement.RETURN_GENERATED_KEYS)) {
                setParams(statement, table.insertParams.apply(entity));
                statement.executeUpdate();
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    table.id.set(entity, keys.getLong(1));
                }
                return entity;
            } catch (SQLException e) {
                throw new StandardException(e);
            }
        } else {
            try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.insertSQL())) {
                setParams(statement, table.insertParams.apply(entity));
                statement.executeUpdate();
                return entity;
            } catch (SQLException e) {
                throw new StandardException(e);
            }
        }
    }


    public List<T> batchInsert(List<T> entities) {
        if (table.id.isAutoGenerated()) {
            try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.insertSQL(), Statement.RETURN_GENERATED_KEYS)) {
                connection.setAutoCommit(false);
                for (T entity : entities) {
                    setParams(statement, table.insertParams.apply(entity));
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();

                ResultSet keys = statement.getGeneratedKeys();
                int i = 0;
                while (keys.next()) {
                    table.id.set(entities.get(i++), keys.getLong(1));
                }
                return entities;
            } catch (SQLException e) {
                throw new StandardException(e);
            }
        } else {
            try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.insertSQL())) {
                connection.setAutoCommit(false);
                for (T entity : entities) {
                    setParams(statement, table.insertParams.apply(entity));
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
                return entities;
            } catch (SQLException e) {
                throw new StandardException(e);
            }
        }
    }


    public T update(Object id, T entity) {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.updateSQL(connection))) {
            setParams(statement, table.updateParams.apply(entity));
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }


    public boolean delete(Object id) {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.deleteSQL(connection))) {
        	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (id == null)) {
				statement.setNull(1, java.sql.Types.VARCHAR);
			} else {
				statement.setObject(1, id);
			}
            return statement.executeUpdate() > 1;
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    public void batchDelete(List ids) {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(table.deleteSQL(connection))) {
            connection.setAutoCommit(false);
            for (Object id : ids) {
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (id == null)) {
    				statement.setNull(1, java.sql.Types.VARCHAR);
                    statement.addBatch();
                    continue;
    			}
                statement.setObject(1, id);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public Connection connection() {
        return transactionManager.getConnection();
    }

    private void setParams(PreparedStatement statement, Object[] params) throws SQLException {
        int index = 1;
        if (params != null) {
            for (Object param : params) {
                setParam(statement, index, param);
                index++;
            }
        }
    }

    private void setParam(PreparedStatement statement, int index, Object param) throws SQLException {
        if (param instanceof String) {
            statement.setString(index, (String) param);
        } else if (param instanceof Integer) {
            statement.setInt(index, (Integer) param);
        } else if (param instanceof Enum) {
            statement.setString(index, ((Enum) param).name());
        } else if (param instanceof LocalDateTime) {
            statement.setTimestamp(index, Timestamp.valueOf((LocalDateTime) param));
        } else if (param instanceof Boolean) {
            statement.setBoolean(index, (Boolean) param);
        } else if (param instanceof Long) {
            statement.setLong(index, (Long) param);
        } else if (param instanceof Double) {
            statement.setDouble(index, (Double) param);
        } else if (param instanceof BigDecimal) {
            statement.setBigDecimal(index, (BigDecimal) param);
        } else if (param instanceof LocalDate) {
            statement.setDate(index, Date.valueOf((LocalDate) param));
        } else if (param == null) {
            statement.setObject(index, null);
        } else {
            throw new StandardException("unsupported param type, please contact arch team, type={}, value={}", param.getClass().getCanonicalName(), param);
        }
    }
}
