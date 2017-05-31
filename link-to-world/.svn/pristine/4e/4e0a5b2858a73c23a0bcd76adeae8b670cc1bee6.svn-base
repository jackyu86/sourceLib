package io.sited.file.web;

import com.google.common.io.Files;
import io.sited.db.FindView;
import io.sited.file.api.FileWebService;
import io.sited.file.api.file.BatchDeleteFileRequest;
import io.sited.file.api.file.FileQuery;
import io.sited.file.api.file.FileRequest;
import io.sited.file.api.file.FileResponse;
import io.sited.file.api.file.FileStatisticsResponse;
import io.sited.file.domain.File;
import io.sited.file.service.FileService;
import io.sited.http.PathParam;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * @author chi
 */
public class FileWebServiceImpl implements FileWebService {
    @Inject
    FileService fileService;

    @Override
    public FileResponse get(ObjectId id) {
        return response(fileService.get(id));
    }

    @Override
    public FindView<FileResponse> find(FileQuery query) {
        return FindView.map(fileService.find(query), this::response);
    }

    @Override
    public FileStatisticsResponse statistics() {
        FileStatisticsResponse response = new FileStatisticsResponse();
        response.count = fileService.count();
        return response;
    }

    @Override
    public FileResponse create(FileRequest request) {
        File file = new File();
        file.title = request.title;
        file.description = request.description;
        file.directoryId = request.directoryId;
        file.path = request.path;
        file.tags = request.tags;
        file.extension = Files.getFileExtension(request.path);
        file.length = request.length;
        file.updatedBy = request.requestBy;
        file.createdTime = LocalDateTime.now();
        file.createdBy = request.requestBy;
        file.updatedTime = LocalDateTime.now();

        fileService.create(file);
        return response(file);
    }

    @Override
    public FileResponse update(@PathParam("id") ObjectId id, FileRequest request) {
        return response(fileService.update(id, request));
    }

    @Override
    public void delete(@PathParam("id") ObjectId id) {
        fileService.delete(id);
    }

    @Override
    public void batchDelete(BatchDeleteFileRequest request) {
        fileService.batchDelete(request);
    }

    private FileResponse response(File file) {
        FileResponse response = new FileResponse();
        response.id = file.id;
        response.directoryId = file.directoryId;
        response.path = file.path;
        response.title = file.title;
        response.description = file.description;
        response.tags = file.tags;
        response.extension = file.extension;
        response.length = file.length;
        response.createdTime = file.createdTime;
        response.createdBy = file.createdBy;
        response.updatedTime = file.updatedTime;
        response.updatedBy = file.updatedBy;
        return response;
    }
}
