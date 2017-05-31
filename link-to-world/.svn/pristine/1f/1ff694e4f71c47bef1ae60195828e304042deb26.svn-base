package io.sited.user.web.controller;

import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.Path;
import io.sited.http.Request;
import io.sited.user.api.user.PinCodeRequest;
import io.sited.user.web.controller.pincode.CheckPinCodeAJAXResponse;
import io.sited.user.web.service.PinCodeService;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author chi
 */
public class PinCodeAJAXController {
    @Inject
    PinCodeService pinCodeService;

    @Path("/ajax/pin-code/send")
    @POST
    public void sendPinCode(Request request) throws IOException {
        PinCodeRequest createPinCodeRequest = request.body(PinCodeRequest.class);
        pinCodeService.sendPinCode(request, createPinCodeRequest);
    }

    @Path("/ajax/user/:username/pin-code/:pinCode/check")
    @GET
    public CheckPinCodeAJAXResponse checkPinCode(Request request) {
        String username = request.pathParam("username");
        String pinCode = request.pathParam("pinCode");
        CheckPinCodeAJAXResponse response = new CheckPinCodeAJAXResponse();
        response.valid = pinCodeService.isValidPinCode(request, username, pinCode);
        return response;
    }
}
