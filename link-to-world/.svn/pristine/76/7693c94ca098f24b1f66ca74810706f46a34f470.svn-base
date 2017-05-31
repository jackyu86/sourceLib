package io.sited.db.impl.jdbc.dialect;

import java.sql.Connection;

/**
 * 数据库适配器抽象工厂类,该工厂不允许创建实例,但可以被子工厂继承，以产生新的适配器实例来提供适配服务。
 * 
 * @author Gavin Lee(lixb@hsit.com.cn) at Create on 2012-1-14 上午06:41:23
 * 
 */
public abstract class DBFuctionFactroy {
	protected static FuncAdapter instance; // 子类工厂创建的适配器实例必须实现FuncAdapter接口,保证应用的向下兼容性

	/**
	 * 通过该静态方法可以获得对应的数据库方言适配器，<b>如果在同一个应用中存在连接多种数据库，
	 * 需要传入不同的SessionFactory来获取不同的数据库方言函数适配器实例。然后分别处理不同的SQL脚本。</b>
	 * 
	 * @param sessionFactory
	 *            数据库会话连接工厂，通过它来分析连接那种数据库，然后产生对应方言的函数适配器
	 * @return 对应的数据库方言函数适配器实例
	 */
	public static FuncAdapter getFuncAdapter(Connection conn) {
		if (instance == null) {
			instance = new AdapterResolver().resolveAdapter(conn);
		}
		return instance;
	}

}
