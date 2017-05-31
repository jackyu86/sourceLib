package io.sited.user.service.impl;

import io.sited.db.JDBCRepository;
import io.sited.http.exception.BadRequestException;
import io.sited.user.api.user.ResetPasswordRequest;
import io.sited.user.domain.ResetPasswordToken;
import io.sited.user.domain.ResetPasswordTokenStatus;
import io.sited.user.domain.User;
import io.sited.user.service.ResetPasswordTokenService;
import io.sited.user.service.UserService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;

/**
 * @author chi
 */
public class JDBCResetPasswordTokenServiceImpl implements ResetPasswordTokenService {
    @Inject
    UserService userService;

    @Inject
    JDBCRepository<ResetPasswordToken> repository;

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
        return repository.query("user_id=? AND token=?", userId, token).findOne();
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }
}
