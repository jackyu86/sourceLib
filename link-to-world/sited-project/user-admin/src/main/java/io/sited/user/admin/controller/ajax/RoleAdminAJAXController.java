package io.sited.user.admin.controller.ajax;


import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.user.api.RoleWebService;
import io.sited.user.api.role.CreateRoleRequest;
import io.sited.user.api.role.RoleQuery;
import io.sited.user.api.role.RoleResponse;
import io.sited.user.api.role.UpdateRoleRequest;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class RoleAdminAJAXController {
    @Inject
    RoleWebService roleWebService;

    @Path("/admin/ajax/role/:id")
    @GET
    public RoleResponse get(Request request) {
        String id = request.pathParam("id");
        return roleWebService.get(id);
    }

    @Path("/admin/ajax/role/find")
    @PUT
    public FindView<RoleResponse> find(Request request) throws IOException {
        RoleQuery query = request.body(RoleQuery.class);
        return roleWebService.find(query);
    }

    @Path("/admin/ajax/role/:id")
    @PUT
    public RoleResponse update(Request request) throws IOException {
        String id = request.pathParam("id");
        UpdateRoleRequest role = request.body(UpdateRoleRequest.class);
        return roleWebService.update(id, role);
    }

    @Path("/admin/ajax/role")
    @POST
    public RoleResponse create(Request request) throws IOException {
        CreateRoleRequest role = request.body(CreateRoleRequest.class);
        return roleWebService.create(role);
    }

    @Path("/admin/ajax/role/:id")
    @DELETE
    public void delete(Request request) throws IOException {
        String id = request.pathParam("id");
        roleWebService.delete(id);
    }
}
