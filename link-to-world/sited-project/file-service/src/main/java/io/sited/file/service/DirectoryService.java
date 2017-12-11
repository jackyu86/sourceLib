package io.sited.file.service;

import com.google.common.collect.Lists;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.file.api.directory.CreateDirectoryRequest;
import io.sited.file.api.directory.DirectoryQuery;
import io.sited.file.api.directory.DirectoryRequest;
import io.sited.file.domain.Directory;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

/**
 * @author chi
 */
public class DirectoryService {
    @Inject
    MongoRepository<Directory> repository;

    public Directory create(Directory directory) {
        return repository.insert(directory);
    }

    public Directory update(ObjectId id, DirectoryRequest request) {
        Directory directory = get(id);
        directory.parentId = request.parentId;
        directory.name = request.name;
        directory.displayOrder = request.displayOrder;
        directory.updatedTime = LocalDateTime.now();
        directory.updatedBy = request.requestBy;
        repository.update(id, directory);
        return directory;
    }

    public List<Directory> allChildren(ObjectId id) {
        Directory directory = get(id);

        List<Directory> directories = Lists.newArrayList();
        directories.add(directory);

        Deque<Directory> stack = Lists.newLinkedList();
        stack.add(directory);

        while (!stack.isEmpty()) {
            directory = stack.poll();
            List<Directory> children = children(directory.id);
            directories.addAll(children);
            children.forEach(stack::add);
        }

        return directories;
    }

    public List<Directory> children(ObjectId id) {
        return repository.query("parent_id", id).findMany();
    }

    public Optional<Directory> findByName(ObjectId parentId, String name) {
        Document filter = new Document();
        if (parentId != null) {
            filter.append("parent_id", parentId);
        }
        filter.append("name", name);
        return repository.query(filter).findOne();
    }

    public Directory get(ObjectId id) {
        return repository.get(id);
    }

    public void delete(ObjectId id) {
        repository.delete(id);
    }

    public List<Directory> findAll() {
        return repository.query().sort("display_order").findMany();
    }

    public List<Directory> chain(ObjectId id, List<Directory> list) {
        Directory directory = repository.get(id);
        list.add(0, directory);
        if (null == directory.parentId) {
            return list;
        }
        return chain(directory.parentId, list);
    }

    public List<Directory> parents(ObjectId id) {
        List<Directory> list = chain(id, Lists.newArrayList());
        return Lists.reverse(list.subList(1, list.size()));
    }

    public Directory createAll(CreateDirectoryRequest request) {
        Directory current = findByName(null, "/").get();
        StringBuilder b = new StringBuilder();
        for (int i = 1; i < request.path.length(); i++) {
            char c = request.path.charAt(i);
            if (c == '/' || i == request.path.length() - 1) {
                if (i == request.path.length() - 1 && c != '/') {
                    b.append(c);
                }
                String directoryName = b.toString();
                ObjectId parentId = current == null ? null : current.id;
                Optional<Directory> directory = findByName(parentId, directoryName);
                if (directory.isPresent()) {
                    current = directory.get();
                } else {
                    Directory newDirectory = new Directory();
                    newDirectory.displayName = directoryName;
                    newDirectory.name = directoryName;
                    newDirectory.parentId = parentId;
                    newDirectory.createdBy = request.requestBy;
                    newDirectory.updatedBy = request.requestBy;
                    newDirectory.createdTime = LocalDateTime.now();
                    newDirectory.updatedTime = LocalDateTime.now();

                    create(newDirectory);
                    current = newDirectory;
                }
                b.delete(0, b.length());
            } else {
                b.append(c);
            }
        }
        return current;
    }

    public FindView<Directory> find(DirectoryQuery directoryQuery) {
        return repository.query("parent_id", directoryQuery.parentId).page(directoryQuery.page)
            .limit(directoryQuery.limit).find();
    }

    public long count() {
        return repository.query().count();
    }
}
