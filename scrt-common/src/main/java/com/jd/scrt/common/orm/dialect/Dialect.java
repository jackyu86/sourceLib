package com.jd.scrt.common.orm.dialect;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 数据库方言
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.0
 */
public abstract class Dialect {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private static Map<String, Dialect> instances = new HashMap<String, Dialect>();

    public boolean supportsLimit() {
        return false;
    }

    public boolean supportsLimitOffset() {
        return false;
    }

    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符(placeholder)替换.
     * <p/>
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit")
     * 将返回:
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @return 包含占位符的分页sql
     */
    public abstract String getLimitSQL(String sql, int offset, int limit) throws Exception;

    public static Dialect getInstance(String clazzName) throws Exception {
        Dialect dialect = instances.get(clazzName);
        if (dialect == null) {
            dialect = newInstance(clazzName);
            instances.put(clazzName, dialect);
        }
        return dialect;
    }

    public static Dialect newInstance(String clazzName) throws Exception {
        if (clazzName == null || clazzName.length() < 1) {
            return new OracleDialect();// 默认为oracle数据库
        }

        if (clazzName.equals("com.jd.scrt.common.orm.dialect.OracleDialect")) {
            return new OracleDialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.MySQLDialect")) {
            return new MySQLDialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.DB2Dialect")) {
            return new DB2Dialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.DerbyDialect")) {
            return new DerbyDialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.H2Dialect")) {
            return new H2Dialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.HSQLDialect")) {
            return new HSQLDialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.PostgreSQLDialect")) {
            return new PostgreSQLDialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.SQLServer2005Dialect")) {
            return new SQLServer2005Dialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.SQLServerDialect")) {
            return new SQLServerDialect();
        }
        if (clazzName.equals("com.jd.scrt.common.orm.dialect.SybaseDialect")) {
            return new SybaseDialect();
        }
        return (Dialect) Class.forName(clazzName).newInstance();
    }

}
