package io.sited.user.service;

import io.sited.user.domain.UserAutoLoginToken;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author chi
 */
public interface AutoLoginTokenService {
    UserAutoLoginToken create(String userId, String token, LocalDateTime expiredTime, String requestBy);

    Optional<UserAutoLoginToken> findByToken(String token);

    void deleteAllByUserId(String userId);
}
