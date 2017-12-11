package io.sited.user.web;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.db.FindView;
import io.sited.http.PathParam;
import io.sited.http.exception.BadRequestException;
import io.sited.user.api.UserWebService;
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
import io.sited.user.api.user.UserStatusView;
import io.sited.user.domain.ResetPasswordToken;
import io.sited.user.domain.User;
import io.sited.user.domain.UserAutoLoginToken;
import io.sited.user.domain.UserStatus;
import io.sited.user.service.AutoLoginTokenService;
import io.sited.user.service.PasswordHasher;
import io.sited.user.service.ResetPasswordTokenService;
import io.sited.user.service.UserService;
import io.sited.util.JSON;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class UserWebServiceImpl implements UserWebService {
    @Inject
    AutoLoginTokenService autoLoginTokenService;

    @Inject
    UserService userService;

    @Inject
    ResetPasswordTokenService resetPasswordTokenService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<User> userOptional = userService.findByUsername(request.username);
        AuthenticationResponse response = new AuthenticationResponse();

        if (!userOptional.isPresent()) {
            throw new BadRequestException("username", "user.error.userNoneExists");
        }

        User user = userOptional.get();
        if (!user.hashedPassword.equals(new PasswordHasher(user.salt, request.password).hash(user.iteration))) {
            throw new BadRequestException("username", "user.error.invalidPassword");
        }

        if (user.status == UserStatus.INACTIVE) {
            throw new BadRequestException("username", "user.error.inactive");
        }

        if (user.status == UserStatus.AUDITING) {
            throw new BadRequestException("username", "user.error.auditing");
        }

        response.user = response(user);

        if (Boolean.TRUE.equals(request.autoLogin)) {
            UserAutoLoginToken userAutoLoginToken = autoLoginTokenService.create(user.id, UUID.randomUUID().toString(), null, request.requestBy);
            response.autoLoginToken = userAutoLoginToken.token;
        }
        return response;
    }

    @Override
    public AuthenticationResponse authenticate(TokenAuthenticationRequest request) {
        Optional<UserAutoLoginToken> autoLoginTokenOptional = autoLoginTokenService.findByToken(request.token);
        if (!autoLoginTokenOptional.isPresent()) {
            throw new StandardException("invalid token, token={}", request.token);
        }
        UserAutoLoginToken userAutoLoginToken = autoLoginTokenOptional.get();
        User user = userService.get(userAutoLoginToken.userId);

        AuthenticationResponse response = new AuthenticationResponse();
        response.user = response(user);
        response.autoLoginToken = autoLoginTokenService.create(user.id, UUID.randomUUID().toString(), null, request.requestBy).token;
        return response;
    }

    @Override
    public AuthenticationResponse authenticate(OauthAuthenticateRequest request) {
        throw new StandardException("not implement yet");
    }

    @Override
    public UserResponse bind(@PathParam("id") String id, BindUserRequest request) {
        throw new StandardException("not implement yet");
    }

    @Override
    public UserResponse get(String id) {
        return response(userService.get(id));
    }

    @Override
    public FindView<UserResponse> find(UserQuery request) {
        return FindView.map(userService.find(request), this::response);
    }

    @Override
    public Optional<UserResponse> findByUsername(String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return Optional.of(response(user.get()));
        }
        return Optional.empty();
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        User user = userService.create(request);
        return response(user);
    }

    @Override
    public UserResponse update(String id, UpdateUserRequest request) {
        User user = userService.update(id, request);
        return response(user);
    }

    @Override
    public UserResponse updatePassword(String id, UpdatePasswordRequest request) {
        User user = userService.updatePassword(id, request);
        return response(user);
    }

    @Override
    public void delete(String id) {
        userService.delete(id);
    }

    @Override
    public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        Optional<User> userOptional = userService.findByUsername(request.username);

        if (!userOptional.isPresent()) {
            throw new BadRequestException("username", "user.error.userNoneExists");
        }

        String userId = userOptional.get().id;
        ResetPasswordToken token = resetPasswordTokenService.create(request);
        ResetPasswordResponse response = new ResetPasswordResponse();
        response.code = token.token;
        response.userId = userId;
        return response;
    }

    @Override
    public void applyResetPassword(ApplyResetPasswordRequest request) {
        Optional<User> userOptional = userService.findByUsername(request.username);

        if (!userOptional.isPresent()) {
            throw new BadRequestException("username", "user.error.userNoneExists");
        }

        String userId = userOptional.get().id;
        Optional<ResetPasswordToken> resetPasswordToken = resetPasswordTokenService.find(userId, request.pinCode);
        if (!resetPasswordToken.isPresent()) {
            throw new BadRequestException("pinCode", "user.error.invalidPinCode");
        }

        resetPasswordTokenService.delete(resetPasswordToken.get().id);

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.password = request.newPassword;
        updatePasswordRequest.requestBy = request.requestBy;
        userService.updatePassword(userId, updatePasswordRequest);
    }

    @Override
    public void updateStatus(String id, UpdateUserStatusRequest request) {
        userService.updateStatus(id, request);
    }

    @Override
    public void clearTokens(String id) {
        autoLoginTokenService.deleteAllByUserId(id);
    }

    private UserResponse response(User user) {
        UserResponse response = new UserResponse();
        response.id = user.id;
        response.username = user.username;
        if (user.roles != null) {
            response.roles = Splitter.on(';').splitToList(user.roles);
        } else {
            response.roles = Lists.newArrayList();
        }
        response.email = user.email;
        response.phone = user.phone;
        response.fullName = user.fullName;
        response.imageURL = user.imageURL;
        response.provider = user.provider;
        response.fields = JSON.fromJSON(user.fields, Map.class);
        response.status = UserStatusView.valueOf(user.status.name());
        response.createdTime = user.createdTime;
        response.createdBy = user.createdBy;
        response.updatedTime = user.updatedTime;
        response.updatedBy = user.updatedBy;
        return response;
    }
}
