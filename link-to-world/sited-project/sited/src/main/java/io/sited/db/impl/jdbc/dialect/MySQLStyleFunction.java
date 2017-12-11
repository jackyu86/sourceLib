package io.sited.db.impl.jdbc.dialect;

import java.sql.Connection;
import java.util.List;

/**
 * Mysql特有函数适配类
 * 
 * @author Gavin Lee(lixb@hsit.com.cn) at Create on Jan 11, 2012 9:12:04 AM
 */
public class MySQLStyleFunction extends DialectAdapter {

	public MySQLStyleFunction(Connection conn) {
		super(conn);
	}

	protected void initAdapter() {
		dialect = new MySQLDialectEx();
	}

	public int getCurrentDBType() {
		return MYSQL;
	}

	@Override
	public String concat(List params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String concat(String str1, String str2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String substr(String str, int start, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String trim(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String num2String(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String string2Num(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String castString2Num(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String string2Date(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String string2ShortDate(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String string2Date(String str, String format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String date2String(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String date2String(String field, String format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String isNull(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String isNotNull(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgChild(String orgUniqueCd, int year, int level, String orgTpye) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgParent(String orgUniqueCd, int year, int level, String orgTpye) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivChild(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivParent(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgChildId(String orgUniqueCd, int year, int level, String orgTpye) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgParentId(String orgUniqueCd, int year, int level, String orgTpye) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgChildCd(String orgUniqueCd, int year, int level, String orgTpye) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgParentCd(String orgUniqueCd, int year, int level, String orgTpye) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgChildTb(String orgUniqueCd, int year, int level, String orgTpye) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivChildId(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivParentId(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivChildCd(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivParentCd(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDivChildTb(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDiv(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDiv(String divUniqueCd, int year, int level, String bizTypeEk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDivNotOrder(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDivNotOrder(String divUniqueCd, int year, int level, String bizTypeEk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDivCdNotOrder(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDivCdNotOrder(String divUniqueCd, int year, int level, String bizTypeEk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDivTb(String divUniqueCd, int year, int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrgManagerDivTb(String divUniqueCd, int year, int level, String bizTypeEk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String replaceNull(String field, String val) {
		// TODO Auto-generated method stub
		return null;
	}

}
