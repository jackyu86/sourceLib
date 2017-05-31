package io.sited.file.api;

import io.sited.db.FindView;
import io.sited.file.api.file.BatchDeleteFileRequest;
import io.sited.file.api.file.FileQuery;
import io.sited.file.api.file.FileRequest;
import io.sited.file.api.file.FileResponse;
import io.sited.file.api.file.FileStatisticsResponse;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;
import io.sited.http.QueryParam;
import org.bson.types.ObjectId;

/**
 * @author chi
 */
public interface FileWebService {
    @Path("/api/file/:id")
    @PUT
    FileResponse get(@QueryParam("id") ObjectId id);

    @Path("/api/file/find")
    @PUT
    FindView<FileResponse> find(FileQuery query);

    @Path("/api/file/file/statistics")
    @GET
    FileStatisticsResponse statistics();

    @Path("/api/file")
    @POST
    FileResponse create(FileRequest request);

    @Path("/api/file/:id")
    @PUT
    FileResponse update(@PathParam("id") ObjectId id, FileRequest request);

    @Path("/api/file/:id")
    @DELETE
    void delete(@PathParam("id") ObjectId id);

    @Path("/api/file/delete")
    @POST
    void batchDelete(BatchDeleteFileRequest request);
}
