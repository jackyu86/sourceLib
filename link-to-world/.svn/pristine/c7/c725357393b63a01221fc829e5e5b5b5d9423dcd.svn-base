package io.sited.user.api;


import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;
import io.sited.user.api.user.ApplyResetPasswordRequest;
import io.sited.user.api.user.AuthenticationRequest;
import io.sited.user.api.user.AuthenticationResponse;
import io.sited.user.api.user.BindUserRequest;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.OauthAuthenticateRequest;
import io.sited.user.api.user.ResetPasswordRequest;
import io.sited.user.api.user.ResetPasswordResponse;
import io.sited.user.api.user.TokenAuthenticationRequest;
import io.sited.user.api.user.UpdatePasswordRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UpdateUserStatusRequest;
import io.sited.user.api.user.UserQuery;
import io.sited.user.api.user.UserResponse;

import java.util.Optional;

/**
 * @author chi
 */
public interface UserWebService {
    @Path("/api/user/authenticate")
    @POST
    AuthenticationResponse authenticate(AuthenticationRequest request);

    @Path("/api/user/authenticate/token")
    @POST
    AuthenticationResponse authenticate(TokenAuthenticationRequest request);

    @Path("/api/user/authenticate/oauth")
    @POST
    AuthenticationResponse authenticate(OauthAuthenticateRequest request);

    @Path("/api/user/:id/oauth/bind")
    @POST
    UserResponse bind(@PathParam("id") String id, BindUserRequest request);

    @Path("/api/user/:id")
    @GET
    UserResponse get(@PathParam("id") String id);

    @Path("/api/user/find")
    @PUT
    FindView<UserResponse> find(UserQuery request);

    @Path("/api/user/username/:username")
    @GET
    Optional<UserResponse> findByUsername(@PathParam("username") String username);

    @Path("/api/user")
    @POST
    UserResponse create(CreateUserRequest request);

    @Path("/api/user/:id")
    @PUT
    UserResponse update(@PathParam("id") String id, UpdateUserRequest request);

    @Path("/api/user/:id/password")
    @PUT
    UserResponse updatePassword(@PathParam("id") String id, UpdatePasswordRequest request);

    @Path("/api/user/password/reset")
    @PUT
    ResetPasswordResponse resetPassword(ResetPasswordRequest request);

    @Path("/api/user/password/reset/apply")
    @PUT
    void applyResetPassword(ApplyResetPasswordRequest request);

    @Path("/api/user/:id/status")
    @POST
    void updateStatus(@PathParam("id") String id, UpdateUserStatusRequest request);

    @Path("/api/user/:id/token")
    @DELETE
    void clearTokens(@PathParam("id") String id);

    @Path("/api/user/:id")
    @DELETE
    void delete(@PathParam("id") String id);
}
