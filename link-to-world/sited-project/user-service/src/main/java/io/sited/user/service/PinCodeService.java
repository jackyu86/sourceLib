package io.sited.user.service;

import io.sited.user.api.user.PinCodeRequest;

/**
 * @author chi
 */
public interface PinCodeService {
    String create(PinCodeRequest request);

    boolean canSendPinCode(PinCodeRequest request);
}
