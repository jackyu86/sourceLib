package io.sited.web;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.Provider;
import io.sited.http.HttpConfig;
import io.sited.http.HttpModule;
import io.sited.http.impl.TemplateBody;
import io.sited.i18n.I18nModule;
import io.sited.template.TemplateConfig;
import io.sited.template.TemplateModule;
import io.sited.util.source.FolderSourceRepository;
import io.sited.web.impl.service.MessageElementWriterImpl;
import io.sited.web.impl.service.MessageNodeProcessorImpl;
import io.sited.web.impl.web.AssetsController;
import io.sited.web.impl.web.TemplateBodyResponseWriter;
import io.sited.web.impl.web.TemplatePathResponseWriter;
import io.sited.web.impl.web.WebController;

import java.util.List;

/**
 * @author chi
 */
@ModuleInfo(name = "web", require = {HttpModule.class, TemplateModule.class, I18nModule.class})
public class WebModule extends Module {
    private AssetsConfig assetsConfig;
    private WebOptions options;

    @Override
    protected void configure() throws Exception {
        options = options(WebOptions.class);
        assetsConfig = new AssetsConfig();

        TemplateConfig templateConfig = require(TemplateConfig.class);
        FolderSourceRepository repository = new FolderSourceRepository(dir("web"));
        templateConfig.addRepository(repository);
        assetsConfig.addRepository(repository);

        templateConfig.addNodeProcessor(new MessageNodeProcessorImpl());
        templateConfig.setElementWriter("j:msg", bind(MessageElementWriterImpl.class));

        options.dirs.forEach(dir -> {
            FolderSourceRepository sourceRepository = new FolderSourceRepository(dir);
            templateConfig.addRepository(sourceRepository);
            assetsConfig.addRepository(sourceRepository);
        });

        bind(AssetsConfig.class, assetsConfig);

        assetsConfig.setCacheEnabled(options.cacheEnabled);

        bind(WebConfig.class, siteConfig());
        export(WebConfig.class);

        WebConfig webConfig = require(WebConfig.class);
        webConfig.controller(AssetsController.class);
        webConfig.controller(WebController.class);

        require(HttpConfig.class)
            .writer(TemplateBody.class, new TemplateBodyResponseWriter(templateConfig, site()))
            .writer(String.class, new TemplatePathResponseWriter(templateConfig, site()));
    }

    private Provider<WebConfig, Module> siteConfig() {
        return module -> new WebConfig() {
            @Override
            public String baseURL() {
                return options.baseURL;
            }

            @Override
            public List<String> baseCdnURLs() {
                return options.baseCdnURLs;
            }

            @Override
            public <T> WebConfig controller(Class<T> controllerClass) {
                T controller = module.bind(controllerClass);
                require(HttpConfig.class).controller(controllerClass, controller, module);
                return this;
            }

            @Override
            public Boolean isCacheEnabled() {
                return options.cacheEnabled;
            }

            @Override
            public AssetsConfig assets() {
                return assetsConfig;
            }
        };
    }
}
