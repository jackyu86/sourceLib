package com.jd.scrt.common.solr;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * 云SolrServer创建工厂
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * @since 1.0.7
 * 
 */
public class CloudSolrServerFactoryBean extends SolrServerFactoryBean {

	private String zkHost;

	private String defaultCollection;

	private int maxConnections = 1000;

	private int maxConnectionsPerHost = 500;

	private int zkClientTimeout = 10000;

	private int zkConnectTimeout = 10000;

	@Override
	public SolrServer getSolrServerObject() throws Exception {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, maxConnections);
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, maxConnectionsPerHost);
		HttpClient client = HttpClientUtil.createClient(params);
		LBHttpSolrServer lbServer = new LBHttpSolrServer(client);
		CloudSolrServer solrServer = new CloudSolrServer(zkHost, lbServer);
		solrServer.setDefaultCollection(defaultCollection);
		solrServer.setZkClientTimeout(zkClientTimeout);
		solrServer.setZkConnectTimeout(zkConnectTimeout);
		return solrServer;
	}

	// ---------- getter and setter ----------//
	public String getZkHost() {
		return zkHost;
	}

	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}

	public String getDefaultCollection() {
		return defaultCollection;
	}

	public void setDefaultCollection(String defaultCollection) {
		this.defaultCollection = defaultCollection;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getZkClientTimeout() {
		return zkClientTimeout;
	}

	public void setZkClientTimeout(int zkClientTimeout) {
		this.zkClientTimeout = zkClientTimeout;
	}

	public int getZkConnectTimeout() {
		return zkConnectTimeout;
	}

	public void setZkConnectTimeout(int zkConnectTimeout) {
		this.zkConnectTimeout = zkConnectTimeout;
	}

}
