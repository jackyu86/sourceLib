package com.jd.scrt.common.orm.dialect;

/**
 * DB2数据库方言
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.0
 */
public class DB2Dialect extends Dialect {

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public boolean supportsLimitOffset() {
        return true;
    }

    @Override
    public String getLimitSQL(String sql, int offset, int limit) throws Exception {
        int startOfSelect = sql.toLowerCase().indexOf("select");

        StringBuffer limit_sql = new StringBuffer(sql.length() + 100).append(sql.substring(0, startOfSelect)) // add the comment
                .append("select * from ( select ") // nest the main query in an outer select
                .append(getRowNumber(sql)); // add the rownnumber bit into the outer query select list
        if (hasDistinct(sql)) {
            limit_sql.append(" row_.* from ( ") // add another (inner) nested select
                    .append(sql.substring(startOfSelect)) // add the main query
                    .append(" ) as row_"); // close off the inner nested select
        } else {
            limit_sql.append(sql.substring(startOfSelect + 6)); // add the main query
        }

        limit_sql.append(" ) as temp_ where rownumber_ ");

        // add the restriction to the outer select
        if (offset > 0) {
            // int end = offset + limit;
            String endString = offset + "+" + limit;
            limit_sql.append("between " + offset + "+1 and " + endString);
        } else {
            limit_sql.append("<= " + limit);
        }

        return limit_sql.toString();
    }

    private static String getRowNumber(String sql) {
        StringBuffer rownumber = new StringBuffer(50).append("rownumber() over(");

        int orderByIndex = sql.toLowerCase().indexOf("order by");

        if (orderByIndex > 0 && !hasDistinct(sql)) {
            rownumber.append(sql.substring(orderByIndex));
        }

        rownumber.append(") as rownumber_,");

        return rownumber.toString();
    }

    private static boolean hasDistinct(String sql) {
        return sql.toLowerCase().indexOf("select distinct") >= 0;
    }
}
