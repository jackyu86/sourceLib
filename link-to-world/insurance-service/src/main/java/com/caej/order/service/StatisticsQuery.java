package com.caej.order.service;

import io.sited.StandardException;
import io.sited.db.FindView;
import io.sited.db.JDBCConfig;
import io.sited.db.impl.jdbc.ResultSetWrapper;
import io.sited.db.impl.jdbc.TableMapper;
import io.sited.db.impl.jdbc.TableMapperBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hubery.chen on 2016/12/30.
 */
public class StatisticsQuery<T> {
    private final TableMapper<T> mapper;
    private final JDBCConfig jdbcConfig;

    public StatisticsQuery(Class<T> entityClass, JDBCConfig jdbcConfig) {
        this.mapper = new TableMapperBuilder<>(entityClass).build();
        this.jdbcConfig = jdbcConfig;
    }

    public FindView<T> query(StatisticsSqlBuilder sql) {
        try (Connection connection = jdbcConfig.connection(); PreparedStatement statement = connection.prepareStatement(sql.selectSQL())) {
            FindView<T> results = new FindView<>();
            if (sql.page != null) {
                results.limit = sql.limit;
                results.page = sql.page;
                results.total = count(sql);
            } else {
                results.limit = 10;
                results.page = 1;
                results.total = 0L;
            }
            for (int i = 0; i < sql.params.size(); i++) {
                statement.setObject(i + 1, sql.params.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.items.add(mapper.map(new ResultSetWrapper(resultSet)));
            }
            return results;
        } catch (SQLException e) {
            throw new StandardException(e);
        }
    }

    private long count(StatisticsSqlBuilder sql) {
        try (Connection connection = jdbcConfig.connection(); PreparedStatement statement = connection.prepareStatement("SELECT count(1)" + sql.conditions.toString())) {
            for (int i = 0; i < sql.params.size(); i++) {
                statement.setObject(i + 1, sql.params.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new StandardException(e);
        }
        return 0;
    }
}
