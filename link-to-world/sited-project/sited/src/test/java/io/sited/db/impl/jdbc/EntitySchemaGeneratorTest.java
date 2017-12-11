package io.sited.db.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author chi
 */
@Ignore
public class EntitySchemaGeneratorTest {
	Connection connection;

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
	}

	@Test
	public void generate() throws SQLException {
		EntitySchemaGenerator entitySchemaGenerator = new EntitySchemaGenerator(connection, TestEntity.class);
		entitySchemaGenerator.createIfNoneExists();
	}
	
	@Test
	public void generate2() throws SQLException {
		EntitySchemaGenerator entitySchemaGenerator = new EntitySchemaGenerator(connection, TestEntity2.class);
		entitySchemaGenerator.createIfNoneExists();
	}
	
}