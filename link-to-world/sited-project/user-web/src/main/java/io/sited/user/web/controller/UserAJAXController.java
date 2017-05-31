package io.sited.user.web.controller;


import com.google.common.base.Strings;
import io.sited.StandardException;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.http.exception.BadRequestException;
import io.sited.user.api.UserWebService;
import io.sited.user.api.user.ApplyResetPasswordRequest;
import io.sited.user.api.user.AuthenticationRequest;
import io.sited.user.api.user.AuthenticationResponse;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.PinCodeRequest;
import io.sited.user.api.user.ResetPasswordRequest;
import io.sited.user.api.user.ResetPasswordResponse;
import io.sited.user.api.user.UpdatePasswordRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UserResponse;
import io.sited.user.web.User;
import io.sited.user.web.UserWebOptions;
import io.sited.user.web.controller.user.ChangePasswordRequest;
import io.sited.user.web.controller.user.CheckUsernameAJAXResponse;
import io.sited.user.web.controller.user.LoginAJAXRequest;
import io.sited.user.web.controller.user.LoginAJAXResponse;
import io.sited.user.web.controller.user.RegisterAJAXRequest;
import io.sited.user.web.controller.user.ResetPasswordAJAXRequest;
import io.sited.user.web.controller.user.UpdateEmailAJAXRequest;
import io.sited.user.web.controller.user.UpdatePhoneAJAXRequest;
import io.sited.user.web.controller.user.UpdateUserAJAXRequest;
import io.sited.user.web.controller.user.UserAJAXResponse;
import io.sited.user.web.service.PinCode;
import io.sited.user.web.service.PinCodeService;
import io.sited.user.web.service.UserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chi
 */
public class UserAJAXController {
    public static final String COOKIE_FROM_URL = "_from_url";
    private final Logger logger = LoggerFactory.getLogger(UserAJAXController.class);

    @Inject
    UserWebService userWebService;
    @Inject
    PinCodeService pinCodeService;
    @Inject
    UserWebOptions userWebOptions;

    @Path("/ajax/user/login")
    @POST
    public Response login(Request request) throws IOException {
        LoginAJAXRequest loginAJAXRequest = request.body(LoginAJAXRequest.class);

        if (userWebOptions.captchaCodeEnabled) {
            Optional<String> captchaCode = request.session().get("captchaCode");

            if (!captchaCode.isPresent() || !captchaCode.get().equals(loginAJAXRequest.captchaCode)) {
                throw new BadRequestException("captchaCode", "user.error.invalidCaptchaCode");
            }
        }

        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.username = loginAJAXRequest.username;
        authenticationRequest.password = loginAJAXRequest.password;
        authenticationRequest.autoLogin = loginAJAXRequest.autoLogin;

        AuthenticationResponse authenticationResponse = userWebService.authenticate(authenticationRequest);
        UserResponse user = authenticationResponse.user;
        request.session().set(UserProvider.SESSION_USER_ID, user.id);

        if (Boolean.TRUE.equals(loginAJAXRequest.autoLogin)) {
            return Response.body(loginAJAXResponse(user.id, request))
                .setCookie(userWebOptions.autoLoginCookie, authenticationResponse.autoLoginToken, userWebOptions.autoLoginMaxAge)
                .setCookie(COOKIE_FROM_URL, null);
        } else {
            return Response.body(loginAJAXResponse(user.id, request)).setCookie(COOKIE_FROM_URL, null);
        }
    }

    private LoginAJAXResponse loginAJAXResponse(String userId, Request request) {
        LoginAJAXResponse response = new LoginAJAXResponse();
        response.userId = userId;
        Optional<String> url = request.cookie(COOKIE_FROM_URL);
        url.ifPresent(s -> {
            try {
                response.fromURL = URLDecoder.decode(s, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new StandardException(e);
            }
        });
        return response;
    }

    @Path("/ajax/user/register")
    @POST
    public Response register(Request request) throws IOException {
        RegisterAJAXRequest registerAJAXRequest = request.body(RegisterAJAXRequest.class);
        pinCodeService.validate(request, registerAJAXRequest.username, registerAJAXRequest.pinCode);

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.username = registerAJAXRequest.username;
        createUserRequest.email = Strings.isNullOrEmpty(registerAJAXRequest.email) && !isTel(registerAJAXRequest.username) ? registerAJAXRequest.username : registerAJAXRequest.email;
        createUserRequest.phone = Strings.isNullOrEmpty(registerAJAXRequest.phone) && isTel(registerAJAXRequest.username) ? registerAJAXRequest.username : registerAJAXRequest.phone;
        createUserRequest.fullName = registerAJAXRequest.fullName;
        createUserRequest.requestBy = registerAJAXRequest.requestBy;
        createUserRequest.password = registerAJAXRequest.password;
        createUserRequest.fields = registerAJAXRequest.fields;

        UserResponse user = userWebService.create(createUserRequest);
        request.session().remove("pinCode");

        if (Boolean.TRUE.equals(userWebOptions.registerAutoLoginEnabled)) {
            request.session().set(UserProvider.SESSION_USER_ID, user.id);
        }
        return Response.body(loginAJAXResponse(user.id, request))
            .setCookie(COOKIE_FROM_URL, null);
    }


    @Path("/ajax/user/self")
    @PUT
    public void update(Request request) throws IOException {
        User user = request.require(User.class);
        UpdateUserAJAXRequest userAJAXRequest = request.body(UpdateUserAJAXRequest.class);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        if (userAJAXRequest.fullName != null) {
            updateUserRequest.fullName = userAJAXRequest.fullName;
        }
        if (userAJAXRequest.description != null) {
            updateUserRequest.description = userAJAXRequest.description;
        }
        if (userAJAXRequest.fields != null) {
            updateUserRequest.fields = userAJAXRequest.fields;
        }
        if (userAJAXRequest.imageURL != null) {
            updateUserRequest.imageURL = userAJAXRequest.imageURL;
        }
        userWebService.update(user.id, updateUserRequest);
    }


    @Path("/ajax/user/self")
    @GET
    public UserAJAXResponse self(Request request) {
        User user = request.require(User.class, null);
        if (user != null) {
            UserAJAXResponse response = new UserAJAXResponse();
            response.id = user.id;
            response.username = user.username;
            response.phone = user.phone;
            response.fullName = user.fullName;
            response.roles = user.roles;
            response.imageURL = user.imageURL;
            response.status = user.status;
            return response;
        } else {
            return null;
        }
    }

    @Path("/ajax/user/self/phone")
    @PUT
    public void updatePhone(Request request) throws IOException {
        User user = request.require(User.class);
        UpdatePhoneAJAXRequest updatePhoneAJAXRequest = request.body(UpdatePhoneAJAXRequest.class);
        pinCodeService.validate(request, updatePhoneAJAXRequest.phone, updatePhoneAJAXRequest.pinCode);

        UserResponse userResponse = userWebService.get(user.id);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.fullName = userResponse.fullName;
        updateUserRequest.email = userResponse.email;
        updateUserRequest.phone = updatePhoneAJAXRequest.phone;
        updateUserRequest.imageURL = userResponse.imageURL;
        updateUserRequest.roles = userResponse.roles;
        updateUserRequest.fields = userResponse.fields;
        updateUserRequest.status = userResponse.status;
        updateUserRequest.requestBy = "user-site";
        userWebService.update(userResponse.id, updateUserRequest);
        request.session().remove("pinCode");
    }

    @Path("/ajax/user/:username/check")
    @GET
    public CheckUsernameAJAXResponse checkUsername(Request request) throws IOException {
        String username = request.pathParam("username");
        Optional<UserResponse> user = userWebService.findByUsername(username);
        CheckUsernameAJAXResponse response = new CheckUsernameAJAXResponse();
        response.exist = user.isPresent();
        return response;
    }

    @Path("/ajax/user/self/email")
    @PUT
    public void updateEmail(Request request) throws IOException {
        User user = request.require(User.class);
        UpdateEmailAJAXRequest updatePhoneRequest = request.body(UpdateEmailAJAXRequest.class);

        pinCodeService.validate(request, updatePhoneRequest.email, updatePhoneRequest.pinCode);

        UserResponse userResponse = userWebService.get(user.id);
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.fullName = userResponse.fullName;
        updateUserRequest.email = updatePhoneRequest.email;
        updateUserRequest.phone = userResponse.phone;
        updateUserRequest.imageURL = userResponse.imageURL;
        updateUserRequest.roles = userResponse.roles;
        updateUserRequest.fields = userResponse.fields;
        updateUserRequest.status = userResponse.status;
        updateUserRequest.requestBy = "user-site";
        userWebService.update(userResponse.id, updateUserRequest);
        request.session().remove("pinCode");
    }

    @Path("/ajax/user/logout")
    @GET
    public Response logout(Request request) throws IOException {
        User user = request.require(User.class, null);
        if (user != null) {
            userWebService.clearTokens(user.id);
        }
        request.session().invalidate();
        return Response.empty().setCookie(userWebOptions.autoLoginCookie, null);
    }

    @Path("/ajax/user/reset-password")
    @POST
    public void resetPassword(Request request) throws IOException {
        ResetPasswordAJAXRequest resetPasswordAJAXRequest = request.body(ResetPasswordAJAXRequest.class);

        if (userWebOptions.captchaCodeEnabled) {
            Optional<String> captchaCode = request.session().get("captchaCode");

            if (!captchaCode.isPresent() || !captchaCode.get().equals(resetPasswordAJAXRequest.captchaCode)) {
                throw new BadRequestException("captchaCode", "user.error.invalidCaptchaCode");
            }
        }

        PinCodeRequest createPinCodeRequest = new PinCodeRequest();
        if (resetPasswordAJAXRequest.username.contains("@")) {
            createPinCodeRequest.email = resetPasswordAJAXRequest.username;
        } else {
            createPinCodeRequest.phone = resetPasswordAJAXRequest.username;
        }
        PinCode pinCode = pinCodeService.sendPinCode(request, createPinCodeRequest);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.username = resetPasswordAJAXRequest.username;
        resetPasswordRequest.token = pinCode.code;
        resetPasswordRequest.requestBy = "website";
        ResetPasswordResponse response = userWebService.resetPassword(resetPasswordRequest);

        logger.info("reset password code, username={},  code={}", resetPasswordAJAXRequest.username, response.code);
    }

    @Path("/ajax/user/reset-password/apply")
    @POST
    public void applyResetPassword(Request request) throws IOException {
        ApplyResetPasswordRequest resetPasswordRequest = request.body(ApplyResetPasswordRequest.class);
        pinCodeService.validate(request, resetPasswordRequest.username, resetPasswordRequest.pinCode);
        userWebService.applyResetPassword(resetPasswordRequest);
        request.session().remove("pinCode");
    }

    @Path("/ajax/user/self/password")
    @PUT
    public void changePassword(Request request) throws IOException {
        User user = request.require(User.class);
        ChangePasswordRequest resetPasswordRequest = request.body(ChangePasswordRequest.class);

        try {
            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.username = user.username;
            authenticationRequest.password = resetPasswordRequest.oldPassword;
            userWebService.authenticate(authenticationRequest);
        } catch (Exception e) {
            throw new BadRequestException("oldPassword", "user.error.invalidOnlyPassword", null, e);
        }

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest();
        updatePasswordRequest.password = resetPasswordRequest.newPassword;
        updatePasswordRequest.requestBy = resetPasswordRequest.requestBy;
        userWebService.updatePassword(user.id, updatePasswordRequest);
    }

    private boolean isTel(String username) {
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
