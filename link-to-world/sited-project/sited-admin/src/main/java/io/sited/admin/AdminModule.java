package io.sited.admin;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.Provider;
import io.sited.StandardException;
import io.sited.admin.impl.web.AdminController;
import io.sited.admin.impl.web.ajax.AdminUserAJAXController;
import io.sited.admin.impl.web.ajax.MessageAJAXController;
import io.sited.admin.impl.web.ajax.PermissionAJAXController;
import io.sited.admin.impl.web.ajax.SiteAJAXController;
import io.sited.admin.impl.web.ajax.TemplateAJAXController;
import io.sited.http.HttpConfig;
import io.sited.http.HttpModule;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.I18nModule;
import io.sited.i18n.impl.service.PropertiesMessageRepository;
import io.sited.template.TemplateConfig;
import io.sited.template.TemplateModule;
import io.sited.util.source.ClasspathSourceRepository;
import io.sited.util.source.FolderSourceRepository;
import io.sited.web.AssetsConfig;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author chi
 */
@ModuleInfo(name = "admin", require = {HttpModule.class, TemplateModule.class, I18nModule.class})
public class AdminModule extends Module {
    private final Console console = new Console();
    private AdminOptions options;

    @Override
    protected void configure() throws Exception {
        options = options(AdminOptions.class);
        bind(AdminOptions.class, options);

        AssetsConfig assetsConfig = new AssetsConfig();
        ClasspathSourceRepository repository = new ClasspathSourceRepository("/META-INF/web");
        TemplateConfig templateConfig = require(TemplateConfig.class);
        templateConfig.addRepository(repository);
        assetsConfig.addRepository(repository);
        assetsConfig.addRepository(new FolderSourceRepository(dir("web")));

        options.dirs.forEach(dir -> {
            FolderSourceRepository sourceRepository = new FolderSourceRepository(dir);
            templateConfig.addRepository(sourceRepository);
            assetsConfig.addRepository(sourceRepository);
        });

        assetsConfig.setCacheEnabled(options.cacheEnabled);
        bind(AssetsConfig.class, assetsConfig);

        bind(Console.class, console);
        bind(AdminConfig.class, adminConfig());

        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(AdminController.class);
        adminConfig.controller(SiteAJAXController.class);
        adminConfig.controller(PermissionAJAXController.class);
        adminConfig.controller(TemplateAJAXController.class);
        adminConfig.controller(AdminUserAJAXController.class);


        require(I18nConfig.class).add(new PropertiesMessageRepository("messages/console_zh-CN.properties"));
        require(I18nConfig.class).add(new PropertiesMessageRepository("messages/console_en-US.properties"));

        bind(I18nConfig.class, require(I18nConfig.class));
        adminConfig.controller(MessageAJAXController.class);
        console.install("console");
        export(AdminConfig.class);
    }

    private Provider<AdminConfig, Module> adminConfig() {
        return module -> new AdminConfig() {
            @Override
            public <T> AdminConfig controller(Class<T> controllerClass) {
                if (!options.enabled) {
                    throw new StandardException("admin is not enabled");
                }
                T controller = module.bind(controllerClass);
                require(HttpConfig.class).controller(controllerClass, controller, module);
                return this;
            }

            @Override
            public Console console() {
                if (!options.enabled) {
                    throw new StandardException("admin is not enabled");
                }
                return console;
            }

            @Override
            public AdminConfig excludeURL(String... urls) {
                options.excludeURLs.addAll(Arrays.stream(urls).map(Pattern::compile).collect(Collectors.toList()));
                return this;
            }
        };
    }
}
