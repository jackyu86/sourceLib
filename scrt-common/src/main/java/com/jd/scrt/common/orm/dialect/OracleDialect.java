package com.jd.scrt.common.orm.dialect;

/**
 * Oracle数据库方言
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.0
 */
public class OracleDialect extends Dialect {

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
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.trim().toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }

        StringBuilder limit_sql = new StringBuilder();
        if (offset > 0) {
            limit_sql.append("SELECT * FROM ( SELECT t_row.*, ROWNUM rownum_ FROM ( ");
        } else {
            limit_sql.append("SELECT * FROM ( ");
        }
        limit_sql.append(sql);
        if (offset > 0) {
            limit_sql.append(" ) t_row WHERE ROWNUM <= " + (offset + limit) + " ) WHERE rownum_ > " + offset);
        } else {
            limit_sql.append(" ) WHERE ROWNUM <= " + limit);
        }

        if (isForUpdate) {
            limit_sql.append(" for update");
        }
        return limit_sql.toString();
    }

}
