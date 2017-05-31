package io.sited.user.web.service;

import io.sited.http.Request;
import io.sited.http.exception.BadRequestException;
import io.sited.queue.Queue;
import io.sited.user.api.PinCodeWebService;
import io.sited.user.api.user.PinCodeRequest;
import io.sited.user.api.user.PinCodeResponse;
import io.sited.user.web.UserWebOptions;
import io.sited.user.web.message.CreatePinCodeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Hubery.Chen
 */
public class PinCodeService {
    private final Logger logger = LoggerFactory.getLogger(PinCodeService.class);

    @Inject
    PinCodeWebService pinCodeWebService;
    @Inject
    Queue<CreatePinCodeEvent> queue;
    @Inject
    UserWebOptions userWebOptions;

    public void validate(Request request, String username, String pinCodeVal) {
//        if (userWebOptions.pinCodeEnabled) {
//            Optional<PinCode> pinCode = request.session().get("pinCode", PinCode.class);
//            if (!pinCode.isPresent()
//                || !pinCode.get().getUserName().equals(username)
//                || !pinCode.get().code.equals(pinCodeVal)
//                || !LocalDateTime.now().isBefore(pinCode.get().createTime.plusMinutes(1))) {
//                throw new BadRequestException("pinCode", "user.error.invalidPinCode");
//            }
//        }
    }

    public boolean isValidPinCode(Request request, String username, String pinCodeValue) {
        if (userWebOptions.pinCodeEnabled) {
            Optional<PinCode> pinCode = request.session().get("pinCode", PinCode.class);
            return pinCode.isPresent() && pinCode.get().getUserName().equals(username)
                && pinCode.get().code.equals(pinCodeValue);
        }
        return true;
    }

    public PinCode sendPinCode(Request request, PinCodeRequest createPinCodeRequest) {
        createPinCodeRequest.ip = request.client().ip();
        PinCodeResponse response = pinCodeWebService.create(createPinCodeRequest);

        PinCode pinCode = new PinCode();
        pinCode.email = createPinCodeRequest.email;
        pinCode.phone = createPinCodeRequest.phone;
        pinCode.code = response.code;
        pinCode.createTime = LocalDateTime.now();
        request.session().set("pinCode", pinCode);
        logger.info("create pin code, email={}, phone={}, code={}", createPinCodeRequest.email, createPinCodeRequest.phone, response.code);

        CreatePinCodeEvent event = new CreatePinCodeEvent();
        event.email = createPinCodeRequest.email;
        event.phone = createPinCodeRequest.phone;
        event.code = pinCode.code;
        queue.publish(event);

        return pinCode;
    }
}
