package com.caej.api.util;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * @author miller
 */
public class CxfClient {
    public final static Long CONNECT_TIMEOUT = 30000L;
    public final static Long RECEIVE_TIMEOUT = 30000L;

    public static Client getCxfWebserviceClient(String wsdl) {
        Client client = null;
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory
                .newInstance();
            client = dcf.createClient(wsdl);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(CONNECT_TIMEOUT);
            httpClientPolicy.setAllowChunking(false);
            httpClientPolicy.setReceiveTimeout(RECEIVE_TIMEOUT);
            http.setClient(httpClientPolicy);
        } catch (Exception e) {
            e.printStackTrace();
            if (client != null)
                try {
                    client.destroy();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
        }
        return client;
    }
}
