package com.jd.scrt.common.orm.mybatis;

import com.jd.scrt.common.annotation.Statement;
import com.jd.scrt.common.proxy.SelectableDynamicProxy;

import java.lang.reflect.Method;


/**
 * SqlSession动态代理(有选择的JDK动态代理)
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * 
 * @param <T>
 * @since 1.0.3
 */
public class SqlSessionDynamicProxy<T> extends SelectableDynamicProxy<T> {

	private static final long serialVersionUID = 3065367307556563483L;

	protected static final Class<String> STRING_CLASS = String.class;
	protected static final Class<Statement> STATEMENT_CLASS = Statement.class;
	
	private static final String SQLID_SEPARATOR = ".";// namespace中连接类全名和sqlId的分隔符

	protected Object invokeProxyMethod(Object proxy, Method method, Object[] args, Method proxyMethod) throws Throwable {
		Statement statement = method.getAnnotation(STATEMENT_CLASS);
		if (statement == null) { // 没有定义Statement注解
			logger.info("invokeProxyMethod: statement is null, use the input args.");
			return proxyMethod.invoke(this.getProxy(), args);
		} else { // 已定义Statement注解，需要重新构建args、ParameterTypes
			int argsIndex = statement.argsIndex();
			String argsValue = this.getStatementId(statement);
			Object[] args_new = this.buildArgs(args, argsIndex, argsValue);
			return proxyMethod.invoke(this.getProxy(), args_new);
		}
	}

	/**
	 * 获取statementId信息
	 *
	 * Created by wangjunlei on 2016-01-24 17:19:50.
	 * @param stat
	 * @return
	 * @throws Exception
	 */
	protected String getStatementId(Statement stat) throws Exception {
		if (stat == null) {
			return null;
		}
		if (stat.value() != null && stat.value().length() > 0) {
			return stat.value();
		}
		StringBuilder s = new StringBuilder();
		if (stat.namespace() != null && stat.namespace().length() > 0) {
			s.append(stat.namespace()).append(SQLID_SEPARATOR);
		}
		s.append(stat.sqlId());
		return s.toString();
	}
	
	/**
	 * 构建参数
	 *
	 * Created by wangjunlei on 2016-01-24 17:19:50.
	 * @param args
	 * @param argsIndex
	 * @param argsValue
	 * @return
	 * @throws Exception
	 */
	protected Object[] buildArgs(Object[] args, int argsIndex, Object argsValue) throws Exception {
		Object[] args_new = null;
		if (args == null || args.length == 0) {
			args_new = new Object[] { argsValue };
		} else {
			if (argsIndex < 0 || argsIndex > args.length) {
				throw new RuntimeException("Statement argsIndex out of args.length!");
			}
			args_new = new Object[args.length + 1];
			args_new[argsIndex] = argsValue;
			int new_index = 0;
			for (int i = 0; i < args.length; i++) {
				if (i == argsIndex) {
					new_index++;
				}
				args_new[new_index] = args[i];
				new_index++;
			}
		}
		return args_new;
	}

}
