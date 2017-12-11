package io.sited.user.web.controller;

import io.sited.http.GET;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.http.Response;
import io.sited.template.impl.code.Objects;
import io.sited.user.web.controller.user.CheckCaptchaCodeAJAXResponse;
import io.sited.user.web.service.CaptchaImage;
import io.sited.user.web.service.CaptchaImageService;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class CaptchaImageController {
    @Inject
    CaptchaImageService captchaImageService;

    @Path("/captcha.jpg")
    @GET
    public Response get(Request request) {
        CaptchaImage captchaImage = captchaImageService.create();
        request.session().set("captchaCode", captchaImage.text);
        return Response.body(captchaImage.content)
            .setContentType("image/jpeg");
    }

    @Path("/ajax/captcha/:captchaCode/check")
    @PUT
    public CheckCaptchaCodeAJAXResponse checkCaptchaCode(Request request) throws IOException {
        String captchaCode = request.pathParam("captchaCode");
        CheckCaptchaCodeAJAXResponse response = new CheckCaptchaCodeAJAXResponse();
        response.valid = Objects.equals(request.session().get("captchaCode"), captchaCode);
        return response;
    }
}
