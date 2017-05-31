package io.sited.file.site.web;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.FileConfig;
import io.sited.file.api.FileWebService;
import io.sited.file.api.directory.CreateDirectoryRequest;
import io.sited.file.api.directory.DirectoryResponse;
import io.sited.file.api.file.FileRequest;
import io.sited.file.api.file.FileResponse;
import io.sited.http.MultipartFile;
import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.exception.BadRequestException;
import io.sited.user.web.User;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class FileAJAXController {
    @Inject
    FileConfig fileConfig;

    @Inject
    FileWebService fileWebService;

    @Inject
    DirectoryWebService directoryWebService;

    @Path("/ajax/file/upload")
    @POST
    public FileResponse upload(Request request) throws IOException {
        User user = request.require(User.class);
        Optional<MultipartFile> fileOptional = request.file("file");
        if (!fileOptional.isPresent()) {
            throw new BadRequestException("file", "file.error.missingFile");
        }
        MultipartFile file = fileOptional.get();
        String directoryPath = request.queryParam("directory").orElse("/upload");

        CreateDirectoryRequest createDirectoryRequest = new CreateDirectoryRequest();
        createDirectoryRequest.path = directoryPath;
        createDirectoryRequest.requestBy = user.username;
        DirectoryResponse directoryResponse = directoryWebService.createAll(createDirectoryRequest);

        String path = path(file.fileName, directoryPath);

        try (InputStream inputStream = new FileInputStream(file.file)) {
            fileConfig.repository().put(path, inputStream);
        }

        FileRequest fileRequest = new FileRequest();
        fileRequest.directoryId = directoryResponse.id;
        fileRequest.title = request.queryParam("title").orElse(null);
        fileRequest.path = "/file" + path;
        fileRequest.description = request.queryParam("description").orElse(null);
        fileRequest.tags = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(request.queryParam("tags").orElse(""));
        fileRequest.length = file.file.length();
        fileRequest.title = Strings.isNullOrEmpty(fileRequest.title) ? file.fileName : fileRequest.title;
        fileRequest.requestBy = user.username;
        return fileWebService.create(fileRequest);
    }

    private String path(String fileName, String directory) {
        StringBuilder b = new StringBuilder();
        b.append(directory);
        b.append('/');
        b.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        b.append('/');
        b.append(UUID.randomUUID().toString());
        b.append('.');
        b.append(Files.getFileExtension(fileName));
        return b.toString();
    }
}
