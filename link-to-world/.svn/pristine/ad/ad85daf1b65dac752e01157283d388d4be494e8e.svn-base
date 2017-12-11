package io.sited.db.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import io.sited.db.impl.jdbc.dialect.DBFuctionFactroy;
import io.sited.db.impl.jdbc.dialect.FuncAdapter;

/**
 * @author chi
 */
@Ignore
public class SQLBuilderTest {
    private final Table<TestEntity> table = new Table<>(TestEntity.class);
    private final Table<TestEntity2> table2 = new Table<>(TestEntity2.class);
    Connection connection;
    private FuncAdapter sqlUtil;

	@Before
	public void setup() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/kdlins?nullNamePatternMatchesAll=true&useUnicode=true&characterEncoding=utf8",
				"root", "123");
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		connection = DriverManager.getConnection(
//				"jdbc:oracle:thin:@localhost:1521:orcl",
//				"root", "123");
		this.sqlUtil = DBFuctionFactroy.getFuncAdapter(connection);
	}

    @Test
    public void selectSQL() {
        String sql = table.sql("id=?", connection).page(1).limit(1).sort("name", true).selectSQL();
        if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) {
        	Assert.assertEquals("SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=? ORDER BY name DESC LIMIT 0,1", sql);
        } else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) {
        	Assert.assertEquals("SELECT tmp1_.* FROM (SELECT tmp0_.*,rownum row0_ FROM (SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=? ORDER BY name DESC) tmp0_ WHERE rownum<=1) tmp1_ WHERE tmp1_.row0_>0", sql);
        }
    }
    
    @Test
    public void selectSQLNullSort() {
        String sql = table.sql("id=?", connection).page(1).limit(1).selectSQL();
        if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) {
        	Assert.assertEquals("SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=? LIMIT 0,1", sql);
        } else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) {
        	Assert.assertEquals("SELECT tmp1_.* FROM (SELECT tmp0_.*,rownum row0_ FROM (SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=?) tmp0_ WHERE rownum<=1) tmp1_ WHERE tmp1_.row0_>0", sql);
        }
    }
    
    @Test
    public void selectSQLNullPage() {
        String sql = table.sql("id=?", connection).sort("name", true).selectSQL();
        if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) {
        	Assert.assertEquals("SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=? ORDER BY name DESC", sql);
        } else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) {
        	Assert.assertEquals("SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=? ORDER BY name DESC", sql);
        }
    }

    @Test
    public void selectSQLRaw() {
        String sql = table.sql("select * from test_entity", connection).selectSQL();
        Assert.assertEquals("select * from test_entity", sql);
    }

    @Test
    public void selectSQLNullCondition() {
        String sql = table.sql(null, connection).selectSQL();
        Assert.assertEquals("SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity", sql);
    }
    
    @Test
    public void selectOneSQL() {
        String sql = table.sql("id=?", connection).selectOneSQL();
        if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) {
        	Assert.assertEquals("SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=? LIMIT 0,1", sql);
        } else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) {
        	Assert.assertEquals("SELECT tmp1_.* FROM (SELECT tmp0_.*,rownum row0_ FROM (SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity WHERE id=?) tmp0_ WHERE rownum<=1) tmp1_ WHERE tmp1_.row0_>0", sql);
        }
    }
    
    @Test
    public void selectOneSQLRaw() {
        String sql = table.sql("SELECT * FROM test_entity", connection).selectOneSQL();
        if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) {
        	Assert.assertEquals("SELECT * FROM test_entity LIMIT 0,1", sql);
        } else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) {
        	Assert.assertEquals("SELECT tmp1_.* FROM (SELECT tmp0_.*,rownum row0_ FROM (SELECT * FROM test_entity) tmp0_ WHERE rownum<=1) tmp1_ WHERE tmp1_.row0_>0", sql);
        }
    }
    
    @Test
    public void selectOneSQLNullCondition() {
        String sql = table.sql(null, connection).selectOneSQL();
        if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) {
        	Assert.assertEquals("SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity LIMIT 0,1", sql);
        } else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) {
        	Assert.assertEquals("SELECT tmp1_.* FROM (SELECT tmp0_.*,rownum row0_ FROM (SELECT id,name,created_time,period_value,payment_method,flag,total,value,update_time FROM test_entity) tmp0_ WHERE rownum<=1) tmp1_ WHERE tmp1_.row0_>0", sql);
        }
    }
    
    @Test
    public void countSQL() {
    	String sql = table.sql("id=?", connection).countSQL();
        Assert.assertEquals("SELECT count(0) FROM test_entity WHERE id=?", sql);
    }
    
    @Test
    public void countSQLNullCondition() {
    	String sql = table.sql(null, connection).countSQL();
        Assert.assertEquals("SELECT count(0) FROM test_entity", sql);
    }

    @Test
    public void insertSQL() {
    	String sql = table.sql("id=?", connection).insertSQL();
        Assert.assertEquals("INSERT INTO test_entity(name,created_time,period_value,payment_method,flag,total,value,update_time) VALUES (?,?,?,?,?,?,?,?)", sql);
    }
    
    @Test
    public void insertSQLRaw() {
        Assert.assertEquals("INSERT INTO test_entity(name,created_time,period_value,payment_method,flag,total,value,update_time) VALUES (?,?,?,?,?,?,?,?)", table.insertSQL());
    }
    
    @Test
    public void insertSQLNullCondition() {
    	String sql = table.sql(null, connection).insertSQL();
        Assert.assertEquals("INSERT INTO test_entity(name,created_time,period_value,payment_method,flag,total,value,update_time) VALUES (?,?,?,?,?,?,?,?)", sql);
    }

    @Test
    public void insertSQLWithoutAutoGeneratedId() {
        Assert.assertEquals("INSERT INTO test_entity2(id,name) VALUES (?,?)", table2.insertSQL());
    }

    @Test
    public void updateSQL() {
        Assert.assertEquals("UPDATE test_entity SET name=?,created_time=?,period_value=?,payment_method=?,flag=?,total=?,value=?,update_time=? WHERE id=?", table.updateSQL(connection));
    }
    
    @Test
    public void deleteSQL() {
        Assert.assertEquals("DELETE FROM test_entity WHERE id=?", table.deleteSQL(connection));
    }
}