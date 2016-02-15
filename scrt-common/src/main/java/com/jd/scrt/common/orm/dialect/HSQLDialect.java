package com.jd.scrt.common.orm.dialect;

/**
 * HSQL数据库方言
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.0
 */
public class HSQLDialect extends Dialect {

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

    @Override
    public String getLimitSQL(String sql, int offset, int limit) throws Exception {
        StringBuilder limit_sql = new StringBuilder();
        limit_sql.append(sql);
        int idx = sql.toLowerCase().indexOf("select") + 6;
        if (offset > 0) {
            limit_sql.insert(idx, " limit " + offset + " " + limit);
        } else {
            limit_sql.insert(idx, " top " + limit);
        }
        return limit_sql.toString();
    }

}
