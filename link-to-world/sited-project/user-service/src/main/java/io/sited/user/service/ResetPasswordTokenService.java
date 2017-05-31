package io.sited.user.service;

import io.sited.user.api.user.ResetPasswordRequest;
import io.sited.user.domain.ResetPasswordToken;

import java.util.Optional;

/**
 * @author chi
 */
public interface ResetPasswordTokenService {
    ResetPasswordToken create(ResetPasswordRequest request);

    Optional<ResetPasswordToken> find(String userId, String token);

    boolean delete(String id);
}
