package com.jd.scrt.common.orm.mybatis.plugin;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据库结果集拦截器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.0
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class DiclectResultSetHandlerInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        FastResultSetHandler resultSetHandler = (FastResultSetHandler) invocation.getTarget();
        MetaObject metaResultSetHandler = MetaObject.forObject(resultSetHandler);
        RowBounds rowBounds = (RowBounds) metaResultSetHandler.getValue("rowBounds");
        if (rowBounds != null && rowBounds.getLimit() > 0 && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {
            metaResultSetHandler.setValue("rowBounds", RowBounds.DEFAULT);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
