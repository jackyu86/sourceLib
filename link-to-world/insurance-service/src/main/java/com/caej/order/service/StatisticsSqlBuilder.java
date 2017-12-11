package com.caej.order.service;

import com.google.common.collect.Lists;

import java.util.List;

public class StatisticsSqlBuilder {
        public StringBuilder sql;
        public StringBuilder conditions;
        public List<Object> params;
        public Integer page;
        public Integer limit;

    public StatisticsSqlBuilder(Integer limit, Integer page) {
        this.limit = limit;
        this.page = page;
        this.params = Lists.newArrayList();
        this.conditions = new StringBuilder();
        this.sql = new StringBuilder();
    }

    public StatisticsSqlBuilder() {
        this.params = Lists.newArrayList();
        this.conditions = new StringBuilder();
        this.sql = new StringBuilder();
    }

    public String selectSQL() {
            sql.append(conditions.toString());
            if (page != null) {
                sql.append(" LIMIT ").append((page - 1) * limit).append(',').append(limit);
            }
            return sql.toString();
        }
    }