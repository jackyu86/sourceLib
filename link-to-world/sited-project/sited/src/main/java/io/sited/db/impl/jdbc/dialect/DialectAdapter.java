package io.sited.db.impl.jdbc.dialect;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库方言适配器抽象类，实现了DB2/Oracle/Mssqlserver三种数据库的公共函数，存在差别的函数在对应的子类中实现
 * 
 * @author Gavin Lee(lixb@hsit.com.cn) at Create on Jan 9, 2012 10:49:09 AM
 */
public abstract class DialectAdapter implements FuncAdapter {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected Connection conn; // 数据库连接会话工厂
	protected Dialect dialect; // 当前数据库方言

	public DialectAdapter(Connection conn) {
		this.conn = conn;
		initAdapter();
	}

	/**
	 * 初始化适配器，根据数据库类型建立对应的方言解析器，子类覆盖这个方法可以直接实例化对应的方言
	 */
	protected void initAdapter() {
		try {
			dialect = new HSDialectResolver().resolveDialect(conn.getMetaData());
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tso.db.dialect.FuncAdapter#getCurrentDBType()
	 */
	public int getCurrentDBType() {
		if (dialect == null) {
			return -1; // 如果对应数据库方言没有生成，返回-1
		}
		if (dialect instanceof OracleDialect) {
			return ORACLE;
		} else if (dialect instanceof SQLServerDialect) {
			return MSSQL;
		} else if (dialect instanceof DB2Dialect) {
			return IBM_DB2;
		} else if (dialect instanceof MySQLDialect) {
			return MYSQL;
		} else {
			return -1;
		}
	}

}
