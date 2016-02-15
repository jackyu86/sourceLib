package com.jd.scrt.common.orm.dialect;

/**
 * SQLServer数据库方言
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * @since 1.0.0
 */
public class SQLServerDialect extends Dialect {

	@Override
	public boolean supportsLimit() {
		return true;
	}
	
	@Override
	public boolean supportsLimitOffset() {
		return false;
	}

	@Override
	public String getLimitSQL(String sql, int offset, int limit) throws Exception {
		if (offset > 0) {
			throw new UnsupportedOperationException("sql server has no offset");
		}

		return new StringBuilder(sql.length() + 8).append(sql).insert(getAfterSelectInsertPoint(sql), " top " + limit).toString();
	}

	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}

}
