package io.sited.db.impl.jdbc.dialect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>根据数据库连接类型创建不同的函数适配器</h1>
 * <p>
 * 通过分析数据库会话属性来判断数据库类型。 经过比较IBM DB2/ORACLE/MSSQLSERVER目前各种版本中的函数库没有存在差异，
 * 因此不再区分版本了，特别编写了一个测试用例来简单判断这些差异。
 * 
 * @author Gavin Lee(lixb@hsit.com.cn) at Create on Jan 10, 2012 5:15:07 PM
 * @author Su
 * @version 1.0.0
 */
public class AdapterResolver {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 返回会话对应的数据库方言函数适配器实例
	 * 
	 * @param conn
	 * @return
	 */
	public FuncAdapter resolveAdapter(Connection conn) {
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			String databaseName = metaData.getDatabaseProductName();
			if ("Oracle".equals(databaseName)) {
				return new OracleStyleFunction(conn);
			}
			if (databaseName.startsWith("DB2/")) {
				return new DB2StyleFunction(conn);
			}
			if (databaseName.startsWith("Microsoft SQL Server")) {
				return new SQLServerStyleFunction(conn);
			}
			if (databaseName.startsWith("MySQL")) {
				return new MySQLStyleFunction(conn);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		return new MySQLStyleFunction(conn);
	}
}
