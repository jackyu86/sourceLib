package io.sited.user.service.impl;

import io.sited.db.MongoRepository;
import io.sited.http.exception.BadRequestException;
import io.sited.user.api.user.ResetPasswordRequest;
import io.sited.user.domain.ResetPasswordToken;
import io.sited.user.domain.ResetPasswordTokenStatus;
import io.sited.user.domain.User;
import io.sited.user.service.ResetPasswordTokenService;
import io.sited.user.service.UserService;
import org.bson.Document;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;

/**
 * @author chi
 */
public class MongoResetPasswordTokenServiceImpl implements ResetPasswordTokenService {
    @Inject
    UserService userService;

    @Inject
    MongoRepository<ResetPasswordToken> repository;

    @Override
    public ResetPasswordToken create(ResetPasswordRequest request) {
        Optional<User> user = userService.findByUsername(request.username);
        if (!user.isPresent()) {
            throw new BadRequestException("username", "user.error.userNoneExists");
        }
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.id = UUID.randomUUID().toString();
        resetPasswordToken.userId = user.get().id;
        resetPasswordToken.token = request.token;
        resetPasswordToken.createdBy = request.requestBy;
        resetPasswordToken.createdTime = now();
        resetPasswordToken.status = ResetPasswordTokenStatus.NEW;
        repository.insert(resetPasswordToken);
        return resetPasswordToken;
    }

    @Override
    public Optional<ResetPasswordToken> find(String userId, String token) {
        return repository.query(new Document("user_id", userId).append("token", token)).findOne();
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }
}
