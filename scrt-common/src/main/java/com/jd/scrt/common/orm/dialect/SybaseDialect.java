package com.jd.scrt.common.orm.dialect;

/**
 * Sybase数据库方言
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.0
 */
public class SybaseDialect extends Dialect {

    @Override
    public boolean supportsLimit() {
        return false;
    }

    @Override
    public boolean supportsLimitOffset() {
        return false;
    }

    @Override
    public String getLimitSQL(String sql, int offset, int limit) throws Exception {
        throw new UnsupportedOperationException("paged getLimitSQL not supported");
    }

}
