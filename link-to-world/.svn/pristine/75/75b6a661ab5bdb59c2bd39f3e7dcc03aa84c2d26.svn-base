package io.sited.file.admin;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;
import io.sited.file.admin.service.DefaultPathSuggesterImpl;
import io.sited.file.admin.service.PinYinPathSuggesterImpl;
import io.sited.file.admin.web.DirectoryAdminController;
import io.sited.file.admin.web.DirectoryPathAdminController;
import io.sited.file.admin.web.FileAdminController;
import io.sited.file.admin.web.FileAdminStatisticsController;
import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.FileModule;
import io.sited.file.api.directory.DirectoryNodeResponse;
import io.sited.file.api.directory.DirectoryRequest;
import io.sited.file.api.directory.DirectoryResponse;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.I18nModule;
import io.sited.i18n.Message;
import io.sited.i18n.impl.service.PropertiesMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author chi
 */
@ModuleInfo(name = "file.admin", require = {AdminModule.class, FileModule.class, I18nModule.class})
public class FileAdminModule extends Module implements FileAdminConfig {
    private final Logger logger = LoggerFactory.getLogger(FileAdminModule.class);
    private Function<String, String> pathConverter;

    @Override
    protected void configure() throws Exception {
        AdminFileOptions adminFileOptions = options(AdminFileOptions.class);

        if ("pinyin".equals(adminFileOptions.pathConverter)) {
            logger.info("file admin use pinyin path converter");
            pathConverter = new PinYinPathSuggesterImpl();
        } else {
            pathConverter = new DefaultPathSuggesterImpl();
        }
        bind(PathSuggester.class, pathConverter);
        bind(FileAdminConfig.class, this);
        export(FileAdminConfig.class);

        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig.controller(FileAdminController.class);
        adminConfig.controller(FileAdminStatisticsController.class);
        adminConfig.controller(DirectoryAdminController.class);
        adminConfig.controller(DirectoryPathAdminController.class);
        adminConfig.console().install("file");

        require(I18nConfig.class)
            .add(new PropertiesMessageRepository("messages/file_en-US.properties"))
            .add(new PropertiesMessageRepository("messages/file_zh-CN.properties"));

        DirectoryWebService directoryWebService = require(DirectoryWebService.class);
        List<DirectoryNodeResponse> tree = directoryWebService.tree();
        if (tree.isEmpty()) {
            logger.info("create directory root");
            DirectoryRequest rootRequest = new DirectoryRequest();
            rootRequest.name = "/";
            Optional<Message> message = require(I18nConfig.class).message("file.root", site().locale());
            rootRequest.displayName = message.map(msg -> msg.get()).orElse("Root");
            rootRequest.requestBy = "init";
            DirectoryResponse directoryResponse = directoryWebService.create(rootRequest);

            DirectoryRequest uploadRequest = new DirectoryRequest();
            uploadRequest.parentId = directoryResponse.id;
            uploadRequest.name = "upload";
            uploadRequest.displayName = "upload";
            uploadRequest.requestBy = "init";
            directoryWebService.create(uploadRequest);
        }
    }


    @Override
    public Function<String, String> pathConverter() {
        return pathConverter;
    }
}
