package io.sited.user.service;

import io.sited.db.FindView;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UpdatePasswordRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UpdateUserStatusRequest;
import io.sited.user.api.user.UserQuery;
import io.sited.user.domain.User;

import java.util.Optional;

/**
 * @author chi
 */
public interface UserService {
    User get(String id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    FindView<User> find(UserQuery request);

    boolean isUsernameExists(String username);

    boolean isEmailExits(String email);

    boolean isPhoneExists(String phone);

    User create(CreateUserRequest request);

    User updatePassword(String id, UpdatePasswordRequest request);

    User update(String id, UpdateUserRequest request);

    User updateStatus(String id, UpdateUserStatusRequest request);

    boolean delete(String id);
}
