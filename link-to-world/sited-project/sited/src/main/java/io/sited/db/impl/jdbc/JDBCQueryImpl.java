package io.sited.db.impl.jdbc;

import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.db.FindView;
import io.sited.db.Query;
import io.sited.db.impl.jdbc.dialect.DBFuctionFactroy;
import io.sited.db.impl.jdbc.dialect.FuncAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class JDBCQueryImpl<T> implements Query<T> {
    private final Table<T> table;
    private final SQLBuilder sql;
    private final Object[] args;
    private final TransactionManager transactionManager;
    protected FuncAdapter sqlUtil;

    public JDBCQueryImpl(Table<T> table, TransactionManager transactionManager, String conditions, Object... args) {
        this.table = table;
        sql = table.sql(conditions, this.connection());
        this.transactionManager = transactionManager;
        this.args = args;
        this.sqlUtil = DBFuctionFactroy.getFuncAdapter(this.connection());
    }

    @Override
    public long execute() {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(sql.updateSQL())) {
            for (int i = 0; i < args.length; i++) {
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (args[i] == null)) {
    				statement.setNull(i + 1, java.sql.Types.VARCHAR);
    			} else {
    				statement.setObject(i + 1, args[i]);
    			}
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public long delete() {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(sql.deleteSQL())) {
            for (int i = 0; i < args.length; i++) {
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (args[i] == null)) {
    				statement.setNull(i + 1, java.sql.Types.VARCHAR);
    			} else {
    				statement.setObject(i + 1, args[i]);
    			}
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public FindView<T> find() {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(sql.selectSQL())) {
            for (int i = 0; i < args.length; i++) {
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (args[i] == null)) {
    				statement.setNull(i + 1, java.sql.Types.VARCHAR);
    			} else {
    				statement.setObject(i + 1, args[i]);
    			}
            }
            ResultSet resultSet = statement.executeQuery();
            long count = count();
            FindView<T> results = new FindView<>();
            results.page = sql.page == null ? 1 : sql.page;
            results.limit = sql.limit == null ? (int) count : sql.limit;
            results.total = count;
            while (resultSet.next()) {
                results.items.add(table.mapper.map(new ResultSetWrapper(resultSet)));
            }
            return results;
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public List<T> findMany() {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(sql.selectSQL())) {
            for (int i = 0; i < args.length; i++) {
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (args[i] == null)) {
    				statement.setNull(i + 1, java.sql.Types.VARCHAR);
    			} else {
    				statement.setObject(i + 1, args[i]);
    			}
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

    @Override
    public Optional<T> findOne() {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(sql.selectOneSQL())) {
            for (int i = 0; i < args.length; i++) {
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (args[i] == null)) {
    				statement.setNull(i + 1, java.sql.Types.VARCHAR);
    			} else {
    				statement.setObject(i + 1, args[i]);
    			}
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(table.mapper.map(new ResultSetWrapper(resultSet)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public long count() {
        try (Connection connection = connection(); PreparedStatement statement = connection.prepareStatement(sql.countSQL())) {
            for (int i = 0; i < args.length; i++) {
            	// DB2环境下参数为null时，不支持ps.setObject，应该用ps.setNull
    			if ((sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) && (args[i] == null)) {
    				statement.setNull(i + 1, java.sql.Types.VARCHAR);
    			} else {
    				statement.setObject(i + 1, args[i]);
    			}
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public Query<T> page(int page) {
        sql.page(page);
        return this;
    }

    @Override
    public Query<T> limit(int limit) {
        sql.limit(limit);
        return this;
    }

    @Override
    public Query<T> sort(String field, boolean desc) {
        sql.sort(field, desc);
        return this;
    }

    private Connection connection() {
        return transactionManager.getConnection();
    }
}
