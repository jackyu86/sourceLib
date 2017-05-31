package io.sited.user.service.impl;

import io.sited.db.JDBCRepository;
import io.sited.user.domain.UserAutoLoginToken;
import io.sited.user.service.AutoLoginTokenService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class JDBCAutoLoginTokenServiceImpl implements AutoLoginTokenService {
    @Inject
    JDBCRepository<UserAutoLoginToken> repository;

    @Override
    public UserAutoLoginToken create(String userId, String token, LocalDateTime expiredTime, String requestBy) {
        UserAutoLoginToken userAutoLoginToken = new UserAutoLoginToken();
        userAutoLoginToken.id = UUID.randomUUID().toString();
        userAutoLoginToken.userId = userId;
        userAutoLoginToken.token = token;
        userAutoLoginToken.expiredTime = expiredTime;
        userAutoLoginToken.createdTime = LocalDateTime.now();
        userAutoLoginToken.createdBy = requestBy;
        repository.insert(userAutoLoginToken);
        return userAutoLoginToken;
    }

    public Optional<UserAutoLoginToken> findByToken(String token) {
        return repository.query("token=?", token).findOne();
    }

    @Override
    public void deleteAllByUserId(String userId) {
        repository.query("user_id=?", userId).delete();
    }
}
