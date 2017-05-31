package io.sited.file.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.db.FindView;
import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.directory.CreateDirectoryRequest;
import io.sited.file.api.directory.DirectoryNodeResponse;
import io.sited.file.api.directory.DirectoryQuery;
import io.sited.file.api.directory.DirectoryRequest;
import io.sited.file.api.directory.DirectoryResponse;
import io.sited.file.api.directory.DirectoryStatisticsResponse;
import io.sited.file.domain.Directory;
import io.sited.file.service.DirectoryService;
import io.sited.http.PathParam;
import io.sited.http.exception.NotFoundException;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class DirectoryWebServiceImpl implements DirectoryWebService {
    @Inject
    DirectoryService directoryService;

    @Override
    public DirectoryResponse get(ObjectId id) {
        return response(directoryService.get(id));
    }

    @Override
    public DirectoryResponse findByName(String name) {
        Optional<Directory> directory = directoryService.findByName(null, name);
        if (!directory.isPresent()) {
            throw new NotFoundException("missing directory, name={}", name);
        }
        return response(directory.get());
    }

    @Override
    public DirectoryResponse createAll(CreateDirectoryRequest request) {
        return response(directoryService.createAll(request));
    }

    @Override
    public List<DirectoryResponse> fullPath(ObjectId id) {
        return directoryService.chain(id, Lists.newArrayList()).stream().map(this::response).collect(Collectors.toList());
    }

    @Override
    public List<DirectoryNodeResponse> tree() {
        List<Directory> directories = directoryService.findAll();
        return tree(directories);
    }

    private List<DirectoryNodeResponse> tree(List<Directory> directories) {
        Map<ObjectId, DirectoryNodeResponse> index = Maps.newHashMap();
        List<DirectoryNodeResponse> roots = Lists.newArrayList();

        directories.forEach(directory -> {
            DirectoryNodeResponse node = new DirectoryNodeResponse();
            node.directory = response(directory);
            index.put(directory.id, node);

            if (directory.parentId == null) {
                roots.add(node);
            }
        });

        directories.forEach(directory -> {
            if (directory.parentId != null && index.containsKey(directory.parentId)) {
                DirectoryNodeResponse parent = index.get(directory.parentId);
                if (parent.children == null) {
                    parent.children = Lists.newArrayList();
                }
                parent.children.add(index.get(directory.id));
            }
        });
        return roots;
    }

    @Override
    public FindView<DirectoryResponse> find(DirectoryQuery directoryQuery) {
        return FindView.map(directoryService.find(directoryQuery), this::response);
    }

    @Override
    public DirectoryStatisticsResponse statistics() {
        DirectoryStatisticsResponse response = new DirectoryStatisticsResponse();
        response.count = directoryService.count();
        return response;
    }

    @Override
    public DirectoryResponse create(DirectoryRequest request) {
        Directory directory = new Directory();
        directory.parentId = request.parentId;
        directory.displayName = request.displayName;
        directory.name = request.name;
        directory.displayOrder = request.displayOrder;
        directory.createdTime = LocalDateTime.now();
        directory.updatedTime = LocalDateTime.now();
        directory.createdBy = request.requestBy;
        directory.updatedBy = request.requestBy;
        return response(directoryService.create(directory));
    }

    @Override
    public DirectoryResponse update(@PathParam("id") ObjectId id, DirectoryRequest request) {
        return response(directoryService.update(id, request));
    }

    @Override
    public void delete(@PathParam("id") ObjectId id) {
        directoryService.delete(id);
    }


    private DirectoryResponse response(Directory directory) {
        DirectoryResponse response = new DirectoryResponse();
        response.id = directory.id;
        response.displayName = directory.displayName;
        response.parentId = directory.parentId;
        response.name = directory.name;
        response.displayOrder = directory.displayOrder;
        response.createdTime = directory.createdTime;
        response.updatedTime = directory.updatedTime;
        response.createdBy = directory.createdBy;
        response.updatedBy = directory.updatedBy;
        return response;
    }
}
