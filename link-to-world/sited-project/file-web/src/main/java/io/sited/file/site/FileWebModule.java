package io.sited.file.site;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.file.api.FileModule;
import io.sited.file.site.service.ImageCacheRepository;
import io.sited.file.site.service.ImageScalar;
import io.sited.file.site.service.Java2DImageScalar;
import io.sited.file.site.web.FileAJAXController;
import io.sited.file.site.web.FileController;
import io.sited.file.site.web.ImageController;
import io.sited.web.WebConfig;
import io.sited.web.WebModule;

/**
 * @author chi
 */
@ModuleInfo(name = "file.web", require = {FileModule.class, WebModule.class})
public class FileWebModule extends Module {
    @Override
    protected void configure() throws Exception {
        FileWebOptions options = options(FileWebOptions.class);

        if ("java-2d".equals(options.imageScalar)) {
            bind(ImageScalar.class, new Java2DImageScalar());
        } else {
            throw new StandardException("Not implement yet");
        }

        bind(ImageCacheRepository.class, new ImageCacheRepository(dir("/cache/image")));

        WebConfig webConfig = require(WebConfig.class);
        webConfig.controller(ImageController.class);
        webConfig.controller(FileController.class);
        webConfig.controller(FileAJAXController.class);
    }
}
