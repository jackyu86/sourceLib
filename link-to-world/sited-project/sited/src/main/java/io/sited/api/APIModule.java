package io.sited.api;

import com.google.common.base.Strings;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.Provider;
import io.sited.StandardException;
import io.sited.api.impl.WebServiceClientBuilder;
import io.sited.http.HttpConfig;
import io.sited.http.HttpModule;
import org.apache.http.client.HttpClient;

/**
 * @author chi
 */
@ModuleInfo(name = "api", require = HttpModule.class)
public class APIModule extends Module {
    private APIOptions options;

    @Override
    protected void configure() throws Exception {
        options = options(APIOptions.class);

        bind(APIConfig.class, apiConfig());
        export(APIConfig.class);
    }

    private Provider<APIConfig, Module> apiConfig() {
        return module -> new APIConfig() {
            @Override
            public <T> APIConfig service(Class<T> serviceClass, Class<? extends T> serviceImplClass) {
                T instance = module.bind(serviceImplClass);
                module.bind(serviceClass, instance);
                module.export(serviceClass);

                if (options.enabled) {
                    require(HttpConfig.class).controller(serviceClass, instance, module);
                }
                return this;
            }

            @Override
            public <T> T client(Class<T> serviceClass, String serviceURL) {
                return client(serviceClass, serviceURL, false);
            }

            @Override
            public <T> T client(Class<T> serviceClass, String serviceURL, boolean xml) {
                if (Strings.isNullOrEmpty(serviceURL)) {
                    throw new StandardException("invalid serviceURL");
                }
                HttpClient httpClient = require(HttpClient.class);
                WebServiceClientBuilder<T> clientBuilder = new WebServiceClientBuilder<>(serviceClass, serviceURL, httpClient, xml);
                T service = clientBuilder.build();
                module.bind(serviceClass, service);
                module.export(serviceClass);
                return service;
            }
        };
    }
}
