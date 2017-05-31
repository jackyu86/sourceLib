package io.sited.user.web;

import io.sited.http.exception.BadRequestException;
import io.sited.user.api.PinCodeWebService;
import io.sited.user.api.user.PinCodeRequest;
import io.sited.user.api.user.PinCodeResponse;
import io.sited.user.service.PinCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @author chi
 */
public class PinCodeWebServiceImpl implements PinCodeWebService {
    private final Logger logger = LoggerFactory.getLogger(PinCodeWebServiceImpl.class);

    @Inject
    PinCodeService userPinCodeTrackingService;

    @Override
    public PinCodeResponse create(PinCodeRequest request) {
        if (userPinCodeTrackingService.canSendPinCode(request)) {
            String code = userPinCodeTrackingService.create(request);
            PinCodeResponse response = new PinCodeResponse();
            response.code = code;
            logger.info("send pinCode, email={}, phone={}, code={}", request.email, request.phone, code);
            return response;
        } else {
            throw new BadRequestException("pinCode", "user.error.pinCodeLimit");
        }
    }
}
