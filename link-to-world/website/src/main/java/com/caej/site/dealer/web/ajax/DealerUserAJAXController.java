package com.caej.site.dealer.web.ajax;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.site.user.service.UserService;
import com.google.common.collect.Lists;

import app.dealer.api.DealerUserWebService;
import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerResponse;
import app.dealer.api.dealer.DealerStatusView;
import app.dealer.api.dealer.DealerUserQuery;
import app.dealer.api.dealer.DealerUserResponse;
import app.dealer.api.dealer.DealerUserStatusView;
import app.dealer.api.dealer.UpdatePayPasswordRequest;
import io.sited.db.FindView;
import io.sited.http.DELETE;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.NotFoundException;
import io.sited.http.exception.UnauthorizedException;
import io.sited.user.api.user.UserResponse;
import io.sited.user.api.user.UserStatusView;
import io.sited.user.web.User;
import io.sited.user.web.controller.user.ChangePasswordRequest;

/**
 * @author miller
 */
public class DealerUserAJAXController {
    @Inject
    DealerUserWebService dealerUserWebService;
    @Inject
    UserService userService;

    @Path("/ajax/dealer/user")
    @PUT("GET")
    public Response list(Request request) throws IOException {
        User user = request.require(User.class);
        DealerResponse currentDealer = request.require(DealerResponse.class);
        if (!isDealerAdmin(user)) throw new UnauthorizedException(request.path());
        DealerUserQueryAJAXRequest dealerUserQueryAJAXRequest = request.body(DealerUserQueryAJAXRequest.class);
        DealerUserQuery dealerUserQuery = new DealerUserQuery();
        DealerUserResponse dealerUserResponse = dealerUserWebService.get(user.id).orElseThrow(() -> new UnauthorizedException("not dealer"));
        dealerUserQuery.dealerId = dealerUserResponse.dealerId;
        dealerUserQuery.roles = Lists.newArrayList("dealerUser");
        dealerUserQuery.order = "created_time";
        dealerUserQuery.limit = dealerUserQueryAJAXRequest.limit;
        dealerUserQuery.page = dealerUserQueryAJAXRequest.page;
        FindView<DealerUserResponse> dealerUserResponses = dealerUserWebService.find(dealerUserQuery);
        return Response.body(FindView.map(dealerUserResponses, dealerUser -> dealerUserAJAXResponse(dealerUser, currentDealer)));
    }

    @Path("/ajax/dealer/user/:userId/self/pay-password")
    @PUT
    public void changePayPassword(Request request) {
        User user = request.require(User.class);
        ChangePasswordRequest resetPasswordRequest = request.body(ChangePasswordRequest.class);

        if (!isDealerAdmin(user)) {
            throw new UnauthorizedException("unauthorized to create dealer user, userId={}", user.id);
        }

        UpdatePayPasswordRequest updatePayPasswordRequest = new UpdatePayPasswordRequest();
        updatePayPasswordRequest.payPassword = resetPasswordRequest.newPassword;
        updatePayPasswordRequest.requestBy = resetPasswordRequest.requestBy;
        dealerUserWebService.updatePayPassword(request.pathParam("userId"), updatePayPasswordRequest);
    }

    @Path("/ajax/dealer/user")
    @POST
    public Response create(Request request) {
        CreateDealerUserAJAXRequest createDealerUserAJAXRequest = request.body(CreateDealerUserAJAXRequest.class);
        User current = request.require(User.class);
        if (!isDealerAdmin(current)) {
            throw new UnauthorizedException("unauthorized to create dealer user, userId={}", current.id);
        }
        UserResponse userResponse = userService.createUser(createDealerUserAJAXRequest, request);
        DealerUserResponse dealerUserResponse = dealerUserWebService.get(current.id).get();
        CreateDealerUserRequest createDealerUserRequest = new CreateDealerUserRequest();
        createDealerUserRequest.userId = userResponse.id;
        createDealerUserRequest.dealerId = dealerUserResponse.dealerId;
        createDealerUserRequest.roles = Lists.newArrayList("dealerUser");
        createDealerUserRequest.status = DealerUserStatusView.ACTIVE;
        createDealerUserRequest.requestBy = "kdlins-website";
        dealerUserWebService.create(createDealerUserRequest);
        return Response.empty();
    }

    @Path("/ajax/dealer/user/:userId/block")
    @PUT
    public void block(Request request) throws IOException {
        User user = request.require(User.class);
        String userId = request.pathParam("userId");
        validDealerUser(user, userId);
        dealerUserWebService.lock(userId);
    }

    @Path("/ajax/dealer/user/:userId/unblock")
    @PUT
    public void unblock(Request request) throws IOException {
        User user = request.require(User.class);
        String userId = request.pathParam("userId");
        validDealerUser(user, userId);
        dealerUserWebService.unlock(userId);
        userService.updateStatus(userId, UserStatusView.ACTIVE);
    }

    @Path("/ajax/dealer/user/:userId")
    @PUT
    public Response update(Request request) {
        User user = request.require(User.class);
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(request.pathParam("userId"));
        if (!dealerUser.isPresent()) {
            throw new NotFoundException(request.path());
        }
        if (!isDealerAdmin(user, dealerUser.get())) {
            throw new UnauthorizedException("unauthorized update dealer user, userId={}", user.id);
        }

        UpdateDealerUserAJAXRequest updateDealerUserAJAXRequest = request.body(UpdateDealerUserAJAXRequest.class);
        User current = request.require(User.class);
        userService.updateUser(current.id, updateDealerUserAJAXRequest);
        return Response.empty();
    }

    @Path("/ajax/dealer/user/:userId")
    @DELETE
    public void delete(Request request) throws IOException {
        String userId = request.pathParam("userId");
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(userId);
        if (!dealerUser.isPresent()) {
            throw new NotFoundException(request.path());
        }
        User user = request.require(User.class);
        if (!isDealerAdmin(user, dealerUser.get())) {
            throw new UnauthorizedException("unauthorized delete dealer user, userId={}", user.id);
        }
        dealerUserWebService.delete(userId);
        userService.delete(userId);
    }

    private void validDealerUser(User user, String userId) {
        Optional<DealerUserResponse> dealerUser = dealerUserWebService.get(userId);
        if (!dealerUser.isPresent()) {
            throw new NotFoundException("dealer user is not found, userId={}", userId);
        }
        if (!isDealerAdmin(user, dealerUser.get())) {
            throw new UnauthorizedException("unauthorized update dealer user, userId={}", user.id);
        }
    }

    private boolean isDealerAdmin(User user) {
        List<String> roles = user.roles;
        return roles != null && roles.contains("dealerAdmin");
    }

    private boolean isDealerAdmin(User user, DealerUserResponse dealerUserResponse) {
        List<String> roles = user.roles;
        if (roles != null && roles.contains("dealerAdmin")) {
            Optional<DealerUserResponse> admin = dealerUserWebService.get(user.id);
            return admin.get().dealerId.equals(dealerUserResponse.dealerId);
        }
        return false;
    }

    private DealerUserAJAXResponse dealerUserAJAXResponse(DealerUserResponse dealerUser, DealerResponse currentDealer) {
        DealerUserAJAXResponse dealerUserAJAXResponse = new DealerUserAJAXResponse();
        UserResponse user = userService.get(dealerUser.userId);
        dealerUserAJAXResponse.id = user.id;
        dealerUserAJAXResponse.email = user.email;
        dealerUserAJAXResponse.username = user.username;
        dealerUserAJAXResponse.fullName = user.fullName;
        dealerUserAJAXResponse.phone = user.phone;
        dealerUserAJAXResponse.status = dealerUser.status;
        dealerUserAJAXResponse.canEdit = currentDealer.status.equals(DealerStatusView.ACTIVE);
        return dealerUserAJAXResponse;
    }
}
