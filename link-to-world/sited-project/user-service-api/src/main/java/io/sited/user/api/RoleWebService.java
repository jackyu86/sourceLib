package io.sited.user.api;


import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;
import io.sited.user.api.role.CreateRoleRequest;
import io.sited.user.api.role.RoleQuery;
import io.sited.user.api.role.RoleResponse;
import io.sited.user.api.role.UpdateRoleRequest;

import java.util.Optional;

/**
 * @author chi
 */
public interface RoleWebService {
    @Path("/api/role/:id")
    @GET
    RoleResponse get(@PathParam("id") String id);

    @Path("/api/role/find")
    @PUT
    FindView<RoleResponse> find(RoleQuery request);

    @Path("/api/role/name/:name")
    @GET
    Optional<RoleResponse> findByName(@PathParam("name") String name);

    @Path("/api/role")
    @POST
    RoleResponse create(CreateRoleRequest request);

    @Path("/api/role/:id")
    @PUT
    RoleResponse update(@PathParam("id") String id, UpdateRoleRequest request);

    @Path("/api/role/:id")
    @DELETE
    void delete(@PathParam("id") String id);
}
