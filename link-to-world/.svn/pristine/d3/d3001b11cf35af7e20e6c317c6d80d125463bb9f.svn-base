package io.sited.user.admin.controller.ajax;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.sited.admin.AdminUser;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.user.admin.service.AdminUserProvider;
import io.sited.user.api.RoleWebService;
import io.sited.user.api.UserWebService;
import io.sited.user.api.role.RoleResponse;
import io.sited.user.api.user.AuthenticationRequest;
import io.sited.user.api.user.AuthenticationResponse;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UpdatePasswordRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UserQuery;
import io.sited.user.api.user.UserResponse;
import io.sited.util.JSON;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * @author chi
 */
public class UserAdminAJAXController {
    @Inject
    UserWebService userWebService;
    @Inject
    RoleWebService roleWebService;

    @Path("/admin/ajax/user/:id/password")
    @POST
    public void updatePassword(Request request) {
        String id = request.pathParam("id");
        UpdatePasswordRequest updatePasswordRequest = request.body(UpdatePasswordRequest.class);
        updatePasswordRequest.requestBy = "user-admin";
        userWebService.updatePassword(id, updatePasswordRequest);
    }

    @Path("/admin/ajax/user/login")
    @POST
    public void authenticate(Request request) throws IOException {
        AuthenticationRequest authenticationRequest = request.body(AuthenticationRequest.class);
        AuthenticationResponse authenticationResponse = userWebService.authenticate(authenticationRequest);

        UserResponse user = authenticationResponse.user;
        Set<String> permissions = Sets.newHashSet();
        user.roles.forEach(name -> {
            Optional<RoleResponse> role = roleWebService.findByName(name);
            if (role.isPresent()) {
                permissions.addAll(role.get().permissions);
            }
        });

        AdminUser admin = new AdminUser();
        admin.id = user.id;
        admin.username = user.username;
        admin.fullName = user.fullName;
        admin.roles = user.roles;

        admin.permissions = Lists.newArrayList(permissions);
        request.session().set(AdminUserProvider.USER_KEY, JSON.toJSON(admin));
    }

    @Path("/admin/ajax/user/:id")
    @GET
    public UserResponse get(Request request) throws IOException {
        String id = request.pathParam("id");
        return userWebService.get(id);
    }

    @Path("/admin/ajax/user/find")
    @PUT
    public FindView<UserResponse> find(Request request) throws IOException {
        UserQuery query = request.body(UserQuery.class);
        return userWebService.find(query);
    }

    @Path("/admin/ajax/user/:id")
    @PUT
    public UserResponse update(Request request) throws IOException {
        String id = request.pathParam("id");
        UpdateUserRequest updateUserRequest = request.body(UpdateUserRequest.class);
        return userWebService.update(id, updateUserRequest);
    }

    @Path("/admin/ajax/user")
    @POST
    public UserResponse create(Request request) throws IOException {
        CreateUserRequest createUserRequest = request.body(CreateUserRequest.class);
        return userWebService.create(createUserRequest);
    }

    @Path("/admin/ajax/user/:id")
    @DELETE
    public void delete(Request request) throws IOException {
        String id = request.pathParam("id");
        userWebService.delete(id);
    }

    @Path("/admin/ajax/user/logout")
    @GET
    public void logout(Request request) throws IOException {
        AdminUser user = request.require(AdminUser.class, null);
        if (user != null) {
            userWebService.clearTokens(user.id);
        }
        request.session().invalidate();
    }
}
