package com.jd.scrt.common.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

/**
 * HttpSolrServer创建工厂
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public class HttpSolrServerFactoryBean extends SolrServerFactoryBean {

    private String serverURL;

    private int maxRetries = 0;

    private int connectionTimeout = 10000;

    private int soTimeout = 10000;

    private int maxConnectionsPerHost = 100;

    private int maxTotalConnections = 100;

    private boolean followRedirects = false;

    private boolean allowCompression = false;

    @Override
    public SolrServer getSolrServerObject() throws Exception {
        HttpSolrServer server = new HttpSolrServer(serverURL);
        server.setMaxRetries(maxRetries); // defaults to 0. > 1 not recommended.
        server.setConnectionTimeout(connectionTimeout); // 5 seconds to
        // establish TCP
        // Setting the XML response parser is only required for cross
        // version compatibility and only when one side is 1.4.1 or
        // earlier and the other side is 3.1 or later.
        server.setParser(new XMLResponseParser()); // binary parser is used by
        // default
        // The following settings are provided here for completeness.
        // They will not normally be required, and should only be used
        // after consulting javadocs to know whether they are truly required.
        server.setSoTimeout(soTimeout); // socket read timeout
        server.setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
        server.setMaxTotalConnections(maxTotalConnections);
        server.setFollowRedirects(followRedirects); // defaults to false
        // allowCompression defaults to false.
        // Server side must support gzip or deflate for this to have any effect.
        server.setAllowCompression(allowCompression);
        return server;
    }

    // ---------- getter and setter ----------//

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public boolean isAllowCompression() {
        return allowCompression;
    }

    public void setAllowCompression(boolean allowCompression) {
        this.allowCompression = allowCompression;
    }

}
