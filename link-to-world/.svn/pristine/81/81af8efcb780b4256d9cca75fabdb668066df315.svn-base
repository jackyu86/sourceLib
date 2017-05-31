package io.sited.db.impl.jdbc;

import java.sql.Connection;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import io.sited.StandardException;
import io.sited.db.impl.jdbc.dialect.DBFuctionFactroy;
import io.sited.db.impl.jdbc.dialect.FuncAdapter;

/**
 * @author chi
 */
public class SQLBuilder {
	private final Table table;
	private final String conditions;
	public Integer page;
	public Integer limit;
	public List<SortingField> sortingFields;
	protected FuncAdapter sqlUtil;

	public SQLBuilder(Table table, String conditions) {
		this.table = table;
		this.conditions = conditions;
	}

	public SQLBuilder(Table table, String conditions, Connection connection) {
		this.table = table;
		this.conditions = conditions;
		this.sqlUtil = DBFuctionFactroy.getFuncAdapter(connection);
	}

	public SQLBuilder sort(String field, boolean desc) {
		if (sortingFields == null) {
			sortingFields = Lists.newArrayList();
		}
		sortingFields.add(new SortingField(field, desc));
		return this;
	}

	public SQLBuilder page(int page) {
		this.page = page;
		return this;
	}

	public SQLBuilder limit(int limit) {
		this.limit = limit;
		return this;
	}

	public String selectSQL() {
		StringBuilder b = new StringBuilder();
		if (isRelativeSQL(conditions)) {
			b.append("SELECT ");
			b.append(table.id.name());
			for (int i = 0; i < table.columns.size(); i++) {
				b.append(',');
				b.append(table.columns.get(i));
			}
			b.append(" FROM ").append(table.name);
			if (!Strings.isNullOrEmpty(conditions)) {
				b.append(" WHERE ").append(conditions);
			}
		} else {
			b.append(conditions);
		}

		if (sortingFields != null) {
			b.append(" ORDER BY ");
			boolean first = true;
			for (SortingField sortingField : sortingFields) {
				if (first) {
					first = false;
				} else {
					b.append(',');
				}
				b.append(sortingField.field);
				if (sortingField.desc) {
					b.append(" DESC");
				}
			}
		}

		if (page != null) {
			if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) { // MySQL
				b.append(" LIMIT ").append((page - 1) * limit).append(',').append(limit);
			} else if (sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) { // DB2
				b = new StringBuilder("SELECT tmp1_.* FROM (SELECT tmp0_.*,ROWNUMBER() OVER() AS rowid0_ FROM  ("
						+ b.toString() + ") as tmp0_) AS tmp1_ WHERE tmp1_.rowid0_>" + (page - 1) * limit
						+ " AND tmp1_.rowid0_<=" + page * limit);
			} else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) { // Oracle
				b = new StringBuilder("SELECT tmp1_.* FROM (SELECT tmp0_.*,rownum row0_ FROM (" + b.toString()
						+ ") tmp0_ WHERE rownum<=" + page * limit + ") tmp1_ WHERE tmp1_.row0_>" + (page - 1) * limit);
			} else if (sqlUtil.getCurrentDBType() == FuncAdapter.MSSQL) { // SQL
																			// Server
				b = new StringBuilder("select top " + page * limit + " * from (" + b.toString() + ") where "
						+ table.id.name() + " not in (select top " + (page - 1) * limit + " " + table.id.name()
						+ " from (" + b.toString() + "))");
			}
		}
		return b.toString();
	}

	public String selectOneSQL() {
		StringBuilder b = new StringBuilder();
		if (isRelativeSQL(conditions)) {
			b.append("SELECT ");
			b.append(table.id.name());
			for (int i = 0; i < table.columns.size(); i++) {
				b.append(',');
				b.append(table.columns.get(i));
			}
			b.append(" FROM ").append(table.name);
			if (!Strings.isNullOrEmpty(conditions)) {
				b.append(" WHERE ").append(conditions);
			}
		} else {
			b.append(conditions);
		}

		if (sortingFields != null) {
			b.append(" ORDER BY ");
			boolean first = true;
			for (SortingField sortingField : sortingFields) {
				if (first) {
					first = false;
				} else {
					b.append(',');
				}
				b.append(sortingField.field);
				if (sortingField.desc) {
					b.append(" DESC");
				}
			}
		}
		// b.append(" LIMIT 0, 1");
		page = 1;
		limit = 1;
		if (sqlUtil.getCurrentDBType() == FuncAdapter.MYSQL) { // MySQL
			b.append(" LIMIT ").append((page - 1) * limit).append(',').append(limit);
		} else if (sqlUtil.getCurrentDBType() == FuncAdapter.IBM_DB2) { // DB2
			b = new StringBuilder("SELECT tmp1_.* FROM (SELECT tmp0_.*,ROWNUMBER() OVER() AS rowid0_ FROM  ("
					+ b.toString() + ") as tmp0_) AS tmp1_ WHERE tmp1_.rowid0_>" + (page - 1) * limit
					+ " AND tmp1_.rowid0_<=" + page * limit);
		} else if (sqlUtil.getCurrentDBType() == FuncAdapter.ORACLE) { // Oracle
			b = new StringBuilder("SELECT tmp1_.* FROM (SELECT tmp0_.*,rownum row0_ FROM (" + b.toString()
					+ ") tmp0_ WHERE rownum<=" + page * limit + ") tmp1_ WHERE tmp1_.row0_>" + (page - 1) * limit);
		} else if (sqlUtil.getCurrentDBType() == FuncAdapter.MSSQL) { // SQL
																		// Server
			b = new StringBuilder("select top " + page * limit + " * from (" + b.toString() + ") where "
					+ table.id.name() + " not in (select top " + (page - 1) * limit + " " + table.id.name() + " from ("
					+ b.toString() + "))");
		}

		return b.toString();
	}

	public String countSQL() {
		if (!isRelativeSQL(conditions)) {
			throw new StandardException("invalid count sql, conditions={}", conditions);
		}
		StringBuilder b = new StringBuilder();
		b.append("SELECT count(0) FROM ").append(table.name);
		if (!Strings.isNullOrEmpty(conditions)) {
			b.append(" WHERE ").append(conditions);
		}
		return b.toString();
	}

	public String insertSQL() {
		StringBuilder b = new StringBuilder();
		if (isRelativeSQL(conditions)) {
			b.append("INSERT INTO ").append(table.name).append('(');

			boolean first = false;
			if (!table.id.isAutoGenerated()) {
				first = true;
				b.append(table.id.name());
			}

			for (int i = 0; i < table.columns.size(); i++) {
				if (first) {
					b.append(',');
				} else {
					first = true;
				}
				b.append(table.columns.get(i));
			}

			b.append(") VALUES (");

			int count = table.id.isAutoGenerated() ? table.columns.size() : table.columns.size() + 1;
			for (int i = 0; i < count; i++) {
				if (i != 0) {
					b.append(',');
				}
				b.append('?');
			}
			b.append(')');
//			if (!Strings.isNullOrEmpty(conditions)) {
//				b.append(" WHERE ").append(conditions);
//			}
		} else {
			b.append(conditions);
		}
		return b.toString();
	}

	public String updateSQL() {
		StringBuilder b = new StringBuilder();
		if (isRelativeSQL(conditions)) {
			b.append("UPDATE ").append(table.name).append(" SET ");
			for (int i = 0; i < table.columns.size(); i++) {
				if (i != 0) {
					b.append(',');
				}
				b.append(table.columns.get(i)).append("=?");
			}
			if (!Strings.isNullOrEmpty(conditions)) {
				b.append(" WHERE ").append(conditions);
			}
		} else {
			b.append(conditions);
		}

		return b.toString();
	}

	public String deleteSQL() {
		StringBuilder b = new StringBuilder();
		if (isRelativeSQL(conditions)) {
			b.append("DELETE FROM ").append(table.name);

			if (!Strings.isNullOrEmpty(conditions)) {
				b.append(" WHERE ").append(conditions);
			}
		} else {
			b.append(conditions);
		}

		return b.toString();
	}

	private boolean isRelativeSQL(String query) {
		if (Strings.isNullOrEmpty(query)) {
			return true;
		}
		StringBuilder b = new StringBuilder();
		char[] chars = query.toCharArray();
		boolean found = false;
		for (char c : chars) {
			if (Character.isWhitespace(c)) {
				if (found) {
					break;
				}
			} else {
				if (!found) {
					found = true;
				}
				b.append(c);
			}
		}
		String command = b.toString().toUpperCase();
		return !"SELECT".equals(command) && !"UPDATE".equals(command) && !"INSERT".equals(command);
	}

	private class SortingField {
		final String field;
		final boolean desc;

		SortingField(String field, boolean desc) {
			this.field = field;
			this.desc = desc;
		}
	}
}
