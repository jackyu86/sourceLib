package io.sited.file.api;

import io.sited.db.FindView;
import io.sited.file.api.directory.CreateDirectoryRequest;
import io.sited.file.api.directory.DirectoryNodeResponse;
import io.sited.file.api.directory.DirectoryQuery;
import io.sited.file.api.directory.DirectoryRequest;
import io.sited.file.api.directory.DirectoryResponse;
import io.sited.file.api.directory.DirectoryStatisticsResponse;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author chi
 */
public interface DirectoryWebService {
    @Path("/api/file/directory/:id")
    @GET
    DirectoryResponse get(@PathParam("id") ObjectId id);

    @Path("/api/file/directory/name/:name")
    @GET
    DirectoryResponse findByName(@PathParam("name") String name);

    @Path("/api/file/directory/:id/path")
    @GET
    List<DirectoryResponse> fullPath(@PathParam("id") ObjectId id);

    @Path("/api/file/directory/tree")
    @GET
    List<DirectoryNodeResponse> tree();

    @Path("/api/file/directory/find")
    @GET
    FindView<DirectoryResponse> find(DirectoryQuery directoryQuery);

    @Path("/api/file/directory/statistics")
    @GET
    DirectoryStatisticsResponse statistics();

    @Path("/api/file/directory")
    @POST
    DirectoryResponse create(DirectoryRequest request);

    @Path("/api/file/directory/all")
    @PUT
    DirectoryResponse createAll(CreateDirectoryRequest request);

    @Path("/api/file/directory/:id")
    @PUT
    DirectoryResponse update(@PathParam("id") ObjectId id, DirectoryRequest request);

    @Path("/api/file/directory/:id")
    @DELETE
    void delete(@PathParam("id") ObjectId id);
}
