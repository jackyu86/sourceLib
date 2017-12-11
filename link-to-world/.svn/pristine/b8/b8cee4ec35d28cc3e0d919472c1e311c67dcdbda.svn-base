package io.sited.file.api;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.file.api.impl.FolderepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author chi
 */
@ModuleInfo(name = "file.api", require = {APIModule.class, DBModule.class})
public class FileModule extends Module implements FileConfig {
    private final Logger logger = LoggerFactory.getLogger(FileModule.class);
    private FileRepository repository;

    @Override
    protected void configure() throws Exception {
        FileAPIOptions fileOptions = options(FileAPIOptions.class);

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.client(FileWebService.class, fileOptions.url);
        apiConfig.client(DirectoryWebService.class, fileOptions.url);

        if (Strings.isNullOrEmpty(fileOptions.dir)) {
            throw new StandardException("invalid file options, dir={}", fileOptions.dir);
        }
        logger.info("file repository, dir={}", fileOptions.dir);
        repository = create(fileOptions);
        bind(FileConfig.class, this);
        export(FileConfig.class);
    }

    protected FileRepository create(FileAPIOptions fileOptions) {
        File dir = fileOptions.dir.startsWith("/") ? new File(fileOptions.dir) : dir(fileOptions.dir);
        if (!dir.exists()) {
            try {
                Files.createParentDirs(dir);
            } catch (IOException e) {
                throw new StandardException(e);
            }
        }
        return new FolderepositoryImpl(dir);
    }

    @Override
    public FileRepository repository() {
        return repository;
    }
}
