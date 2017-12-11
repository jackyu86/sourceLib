package io.sited.http.impl;

import io.sited.StandardException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

public final class HttpClientBuilder {
    private Duration timeout = Duration.ofSeconds(60);
    private int maxConnections = 100;
    private Duration keepAliveTimeout = Duration.ofSeconds(60);
    private boolean enableCookie = false;

    public CloseableHttpClient build() {
        try {
            org.apache.http.impl.client.HttpClientBuilder builder = HttpClients.custom();
            builder.setUserAgent("HttpClient");

            builder.setKeepAliveStrategy((response, context) -> keepAliveTimeout.toMillis());

            builder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(TrustSelfSignedStrategy.INSTANCE).build());

            builder.setDefaultSocketConfig(SocketConfig.custom().setSoKeepAlive(true).build());

            builder.setDefaultRequestConfig(RequestConfig.custom()
                .setSocketTimeout((int) timeout.toMillis())
                .setConnectionRequestTimeout((int) timeout.toMillis())
                .setConnectTimeout((int) timeout.toMillis()).build());

            builder.setMaxConnPerRoute(maxConnections)
                .setMaxConnTotal(maxConnections);

            builder.disableAuthCaching();
            builder.disableConnectionState();
            builder.disableAutomaticRetries();  // retry should be handled in framework level with better trace log

            if (!enableCookie) builder.disableCookieManagement();

            return builder.build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new StandardException(e);
        }
    }

    public HttpClientBuilder maxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    public HttpClientBuilder timeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public HttpClientBuilder keepAliveTimeout(Duration keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
        return this;
    }

    public HttpClientBuilder enableCookie() {
        enableCookie = true;
        return this;
    }
}
