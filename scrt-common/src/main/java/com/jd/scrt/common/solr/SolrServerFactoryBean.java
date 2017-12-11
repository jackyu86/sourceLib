package com.jd.scrt.common.solr;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.FactoryBean;

/**
 * 抽象SolrServer构建工厂
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * @since 1.0.7
 * 
 */
public abstract class SolrServerFactoryBean implements FactoryBean<SolrServer> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	private boolean singleton = true;// 默认为单例

	@Override
	public SolrServer getObject() throws Exception {
		return this.getSolrServerObject();
	}

	/**
	 * 构建SolrServer对象
	 *
	 * Created by wangjunlei on 2016-01-24 17:19:50.
	 * @return
	 * @throws Exception
	 */
	public abstract SolrServer getSolrServerObject() throws Exception;

	@Override
	public Class<?> getObjectType() {
		return SolrServer.class;
	}

	@Override
	public boolean isSingleton() {
		return this.singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
}
