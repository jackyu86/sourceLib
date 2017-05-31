package io.sited.file.admin.web;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import io.sited.db.FindView;
import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.FileConfig;
import io.sited.file.api.FileWebService;
import io.sited.file.api.directory.DirectoryResponse;
import io.sited.file.api.file.BatchDeleteFileRequest;
import io.sited.file.api.file.FileQuery;
import io.sited.file.api.file.FileRequest;
import io.sited.file.api.file.FileResponse;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.MultipartFile;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.exception.BadRequestException;
import io.sited.admin.AdminUser;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class FileAdminController {
    @Inject
    FileConfig fileConfig;

    @Inject
    FileWebService fileWebService;

    @Inject
    DirectoryWebService directoryWebService;

    @Path("/admin/ajax/file/:id")
    @GET
    public FileResponse get(Request request) {
        ObjectId id = request.pathParam("id", ObjectId.class);
        return fileWebService.get(id);
    }

    @Path("/admin/ajax/file/find")
    @PUT
    public FindView<FileResponse> find(Request request) throws IOException {
        FileQuery query = request.body(FileQuery.class);
        return fileWebService.find(query);
    }

    @Path("/admin/ajax/file/upload")
    @POST
    public FileResponse upload(Request request) throws IOException {
        Optional<MultipartFile> fileOptional = request.file("file");
        if (!fileOptional.isPresent()) {
            throw new BadRequestException("file", "file.error.missingFile");
        }
        MultipartFile file = fileOptional.get();

        ObjectId directoryId;
        Optional<ObjectId> directoryIdOptional = request.queryParam("directoryId", ObjectId.class);

        if (directoryIdOptional.isPresent()) {
            directoryId = directoryIdOptional.get();
        } else {
            DirectoryResponse directory = directoryWebService.findByName("upload");
            directoryId = directory.id;
        }

        String path = path(file.fileName, directoryId);
        try (InputStream inputStream = new FileInputStream(file.file)) {
            fileConfig.repository().put(path, inputStream);
        }

        FileRequest fileRequest = new FileRequest();
        fileRequest.directoryId = directoryId;
        fileRequest.title = request.queryParam("title").orElse(null);
        fileRequest.path = "/file" + path;
        fileRequest.description = request.queryParam("description").orElse(null);
        fileRequest.tags = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(request.queryParam("tags").orElse(""));
        fileRequest.length = file.file.length();
        fileRequest.title = Strings.isNullOrEmpty(fileRequest.title) ? file.fileName : fileRequest.title;
        fileRequest.requestBy = request.require(AdminUser.class).username;
        return fileWebService.create(fileRequest);
    }

    @Path("/admin/ajax/file/:id")
    @PUT
    public void update(Request request) throws IOException {
        ObjectId id = request.pathParam("id", ObjectId.class);
        FileRequest fileRequest = request.body(FileRequest.class);
        fileRequest.requestBy = request.require(AdminUser.class).username;
        fileWebService.update(id, fileRequest);
    }

    private String path(String fileName, ObjectId directoryId) {
        StringBuilder b = new StringBuilder();
        List<DirectoryResponse> directories = directoryWebService.fullPath(directoryId);
        for (DirectoryResponse directory : directories) {
            b.append(directory.name);
            if (b.charAt(b.length() - 1) != '/') {
                b.append('/');
            }
        }
        b.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        b.append('/');
        b.append(UUID.randomUUID().toString());
        b.append('.');
        b.append(Files.getFileExtension(fileName));
        return b.toString();
    }

    @Path("/admin/ajax/file/:id")
    @DELETE
    public void delete(Request request) throws IOException {
        ObjectId id = request.pathParam("id", ObjectId.class);
        fileWebService.delete(id);
    }

    @Path("/admin/ajax/file/delete")
    @POST
    public void batchDelete(Request request) throws IOException {
        fileWebService.batchDelete(request.body(BatchDeleteFileRequest.class));
    }
}
