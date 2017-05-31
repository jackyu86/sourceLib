package io.sited.file;


import com.google.common.base.Strings;
import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.FileAPIOptions;
import io.sited.file.api.FileConfig;
import io.sited.file.api.FileModule;
import io.sited.file.api.FileRepository;
import io.sited.file.api.FileWebService;
import io.sited.file.domain.Directory;
import io.sited.file.domain.File;
import io.sited.file.service.DirectoryService;
import io.sited.file.service.FileService;
import io.sited.file.web.DirectoryWebServiceImpl;
import io.sited.file.web.FileWebServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chi
 */
@ModuleInfo(name = "file", require = {DBModule.class, APIModule.class})
public class FileModuleImpl extends FileModule {
    private final Logger logger = LoggerFactory.getLogger(FileModuleImpl.class);
    private FileRepository repository;

    @Override
    protected void configure() throws Exception {
        FileAPIOptions fileOptions = options(FileAPIOptions.class);

        require(MongoConfig.class)
            .entity(File.class)
            .entity(Directory.class);

        bind(DirectoryService.class);
        bind(FileService.class);

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.service(FileWebService.class, FileWebServiceImpl.class);
        apiConfig.service(DirectoryWebService.class, DirectoryWebServiceImpl.class);

        if (Strings.isNullOrEmpty(fileOptions.dir)) {
            throw new StandardException("invalid file options, dir={}", fileOptions.dir);
        }
        logger.info("file repository, dir={}", fileOptions.dir);
        repository = create(fileOptions);

        bind(FileConfig.class, this);
        export(FileConfig.class);
    }

    @Override
    public FileRepository repository() {
        return repository;
    }
}
