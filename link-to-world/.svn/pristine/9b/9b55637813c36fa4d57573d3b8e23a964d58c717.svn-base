package io.sited.file.admin.web;

import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.directory.DirectoryNodeResponse;
import io.sited.file.api.directory.DirectoryRequest;
import io.sited.file.api.directory.DirectoryResponse;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.admin.AdminUser;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * @author chi
 */
public class DirectoryAdminController {
    @Inject
    DirectoryWebService directoryWebService;

    @Path("/admin/ajax/file/directory")
    @POST
    public DirectoryResponse create(Request request) throws IOException {
        AdminUser user = request.require(AdminUser.class);
        DirectoryRequest directoryRequest = request.body(DirectoryRequest.class);
        directoryRequest.requestBy = user.username;
        return directoryWebService.create(directoryRequest);
    }

    @Path("/admin/ajax/file/directory/:id")
    @DELETE
    public void delete(Request request) throws IOException {
        directoryWebService.delete(request.pathParam("id", ObjectId.class));
    }

    @Path("/admin/ajax/file/directory/tree")
    @GET
    public List<DirectoryNodeResponse> tree() {
        return directoryWebService.tree();
    }
}
