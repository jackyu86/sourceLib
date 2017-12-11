package io.sited.user.service.impl;

import io.sited.db.MongoRepository;
import io.sited.user.domain.UserAutoLoginToken;
import io.sited.user.service.AutoLoginTokenService;
import org.bson.Document;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chi
 */
public class MongoAutoLoginTokenServiceImpl implements AutoLoginTokenService {
    @Inject
    MongoRepository<UserAutoLoginToken> repository;

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
        return repository.query(new Document("token", token)).findOne();
    }

    @Override
    public void deleteAllByUserId(String userId) {
        repository.query(new Document("user_id", userId)).delete();
    }
}
