package io.sited.user.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.BadRequestException;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.user.api.user.UpdatePasswordRequest;
import io.sited.user.api.user.UpdateUserRequest;
import io.sited.user.api.user.UpdateUserStatusRequest;
import io.sited.user.api.user.UserQuery;
import io.sited.user.domain.User;
import io.sited.user.domain.UserStatus;
import io.sited.user.service.PasswordHasher;
import io.sited.user.service.UserService;
import io.sited.util.JSON;
import org.bson.Document;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author chi
 */
public class MongoUserServiceImpl implements UserService {
    @Inject
    MongoRepository<User> repository;

    @Override
    public User update(String id, UpdateUserRequest request) {
        User user = get(id);
        if (request.description != null) {
            user.description = request.description;
        }
        if (request.fullName != null) {
            user.fullName = request.fullName;
        }
        if (request.phone != null) {
            user.phone = request.phone;
        }
        if (request.email != null) {
            user.email = request.email;
        }
        if (request.imageURL != null) {
            user.imageURL = request.imageURL;
        }
        if (request.roles != null) {
            user.roles = Joiner.on(';').join(request.roles);
        }
        if (request.status != null) {
            user.status = UserStatus.valueOf(request.status.name());
        }
        if (request.fields != null) {
            user.fields = JSON.toJSON(request.fields);
        }
        user.updatedTime = LocalDateTime.now();
        user.updatedBy = request.requestBy;
        repository.update(id, user);
        return user;
    }

    @Override
    public User get(String id) {
        return repository.query(new Document("_id", id)).findOne().get();
    }

    @Override
    public User updateStatus(String id, UpdateUserStatusRequest request) {
        User user = get(id);
        user.status = UserStatus.valueOf(request.status.name());
        user.updatedTime = LocalDateTime.now();
        user.updatedBy = request.requestBy;
        repository.update(id, user);
        return user;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public User create(CreateUserRequest request) {
        if (isUsernameExists(request.username)) {
            throw new BadRequestException("username", "user.error.usernameExists");
        }

        User user = new User();
        user.id = UUID.randomUUID().toString();
        user.username = request.username;
        user.salt = salt();
        user.iteration = (int) (System.currentTimeMillis() % 4 + 1);
        user.hashedPassword = new PasswordHasher(user.salt, request.password).hash(user.iteration);
        if (request.roles != null) {
            user.roles = Joiner.on(';').join(request.roles);
        }
        user.createdTime = LocalDateTime.now();
        user.createdBy = request.requestBy;
        user.description = request.description;
        user.email = request.email;
        user.phone = request.phone;
        user.imageURL = request.imageURL;
        user.provider = request.provider;
        user.fields = JSON.toJSON(request.fields);
        user.status = request.status == null ? UserStatus.ACTIVE : UserStatus.valueOf(request.status.name());
        user.fullName = request.fullName;
        repository.insert(user);
        return user;
    }

    @Override
    public boolean isUsernameExists(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public boolean isEmailExits(String email) {
        return email == null || findByEmail(email).isPresent();
    }

    @Override
    public boolean isPhoneExists(String phone) {
        return phone == null || findByPhone(phone).isPresent();
    }

    private String salt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return Hashing.md5().hashBytes(bytes).toString();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.query(new Document("username", username)).findOne();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.query(new Document("email", email)).findOne();
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return repository.query(new Document("phone", phone)).limit(1).findOne();
    }

    @Override
    public FindView<User> find(UserQuery query) {
        Document document = new Document();
        if (!Strings.isNullOrEmpty(query.username)) {
            document.append("username", query.username);
        }
        if (!Strings.isNullOrEmpty(query.email)) {
            document.append("email", query.email);
        }
        if (!Strings.isNullOrEmpty(query.phone)) {
            document.append("phone", query.phone);
        }
        if (!Strings.isNullOrEmpty(query.fullName)) {
            document.append("full_name", query.fullName);
        }
        if (!Strings.isNullOrEmpty(query.status)) {
            document.append("status", query.status);
        }
        if (!Strings.isNullOrEmpty(query.role)) {
            document.append("roles", Pattern.compile(".*" + query.role + ".*"));
        }
        return repository.query(document)
            .page(query.page)
            .limit(query.limit)
            .sort("updated_time", true).find();
    }

    @Override
    public User updatePassword(String id, UpdatePasswordRequest request) {
        User user = get(id);
        user.salt = salt();
        user.iteration = (int) (System.currentTimeMillis() % 4 + 1);
        user.hashedPassword = new PasswordHasher(user.salt, request.password).hash(user.iteration);
        user.updatedTime = LocalDateTime.now();
        user.updatedBy = request.requestBy;
        repository.update(id, user);
        return user;
    }
}
