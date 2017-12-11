package com.jd.scrt.common.orm.dialect;

/**
 * MySQL数据库方言
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.0
 */
public class MySQLDialect extends Dialect {

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitSQL(String sql, int offset, int limit) throws Exception {
        if (offset > 0) {
            return sql + " limit " + offset + "," + limit;
        } else {
            return sql + " limit " + limit;
        }
    }

}
