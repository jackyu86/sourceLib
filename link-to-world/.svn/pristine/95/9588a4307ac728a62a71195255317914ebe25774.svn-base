package io.sited.file.service;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.file.api.file.BatchDeleteFileRequest;
import io.sited.file.api.file.FileQuery;
import io.sited.file.api.file.FileRequest;
import io.sited.file.domain.Directory;
import io.sited.file.domain.File;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class FileService {
    @Inject
    MongoRepository<File> repository;

    @Inject
    DirectoryService directoryService;

    public File get(ObjectId id) {
        return repository.get(id);
    }

    public Optional<File> findByPath(String path) {
        return repository.query("path", path).findOne();
    }

    public FindView<File> find(FileQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.title)) {
            filter.append("title", new Document("$regex", Pattern.compile(Pattern.quote(query.title))));
        }

        if (query.directoryId != null) {
            List<Directory> directories = directoryService.allChildren(query.directoryId);
            filter.append("directory_id", new Document("$in", directories.stream().map(directory -> directory.id).collect(Collectors.toList())));
        } else {
            filter.append("directory_id", null);
        }

        return repository.query(filter)
            .page(query.page).limit(query.limit)
            .sort("updated_time", true).find();
    }

    public void create(File file) {
        repository.insert(file);
    }

    public File update(ObjectId id, FileRequest request) {
        File file = get(id);
        file.title = request.title;
        file.description = request.description;
        file.directoryId = request.directoryId;
        file.path = request.path;
        file.tags = request.tags;
        file.extension = Files.getFileExtension(request.path);
        file.length = request.length;
        file.updatedBy = request.requestBy;
        file.updatedTime = LocalDateTime.now();

        repository.update(id, file);
        return file;
    }

    public boolean delete(ObjectId id) {
        return repository.delete(id);
    }

    public void batchDelete(BatchDeleteFileRequest request) {
        repository.batchDelete(request.ids);
    }

    public long count() {
        return repository.query().count();
    }
}
