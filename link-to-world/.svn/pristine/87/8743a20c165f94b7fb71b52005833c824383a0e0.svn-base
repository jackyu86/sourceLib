package io.sited.db.impl.jdbc.dialect;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据数据库连接判断当前的数据库方言
 * 
 * @author Gavin Lee(lixb@hsit.com.cn) at Create on Jan 9, 2012 7:43:08 PM
 * @author Su
 */
public class HSDialectResolver implements DialectResolver {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public Dialect resolveDialect(DatabaseMetaData metaData) {
		try {
			String databaseName = metaData.getDatabaseProductName();
			if (databaseName.startsWith("DB2/")) {
				return new DB2DialectEx();
			}
			if ("Oracle".equals(databaseName)) {
				return new OracleDialectEx();
			}
			if (databaseName.startsWith("Microsoft SQL Server")) {
				return new SQLServerDialectEx();
			}
			if ("MySQL".equals(databaseName)) {
				return new MySQLDialectEx();
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			return null;
		}
		return null;
	}

}
