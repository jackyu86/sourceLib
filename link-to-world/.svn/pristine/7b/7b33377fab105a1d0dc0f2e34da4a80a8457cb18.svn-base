package io.sited.user.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;
import io.sited.user.api.role.CreateRoleRequest;
import io.sited.user.api.role.RoleQuery;
import io.sited.user.api.role.UpdateRoleRequest;
import io.sited.user.domain.Role;
import io.sited.user.service.RoleService;
import org.bson.Document;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;

/**
 * @author chi
 */
public class MongoRoleServiceImpl implements RoleService {
    @Inject
    MongoRepository<Role> repository;

    @Override
    public Optional<Role> findByName(String roleName) {
        return repository.query(new Document("name", roleName)).findOne();
    }

    @Override
    public FindView<Role> find(RoleQuery query) {
        Document filter = Strings.isNullOrEmpty(query.name) ? new Document() : new Document("name", query.name);
        return repository.query(filter).sort("updated_time", true).page(query.page).limit(query.limit)
            .find();
    }

    @Override
    public boolean isRoleExists(String roleName) {
        return findByName(roleName).isPresent();
    }

    @Override
    public Role create(CreateRoleRequest request) {
        if (isRoleExists(request.name)) {
            throw new BadRequestException("name", "user.error.roleExists");
        }
        Role role = new Role();
        role.id = UUID.randomUUID().toString();
        role.name = request.name;
        if (request.permissions != null) {
            role.permissions = Joiner.on(';').join(request.permissions);
        }
        role.displayName = request.displayName;
        role.description = request.description;
        role.createdTime = now();
        role.createdBy = request.requestBy;
        role.updatedTime = now();
        role.updatedBy = request.requestBy;
        repository.insert(role);
        return role;
    }

    @Override
    public Role update(String id, UpdateRoleRequest request) {
        Role role = this.get(id);
        role.displayName = request.displayName;
        role.description = request.description;
        role.permissions = Joiner.on(';').join(request.permissions);
        role.updatedTime = LocalDateTime.now();
        role.updatedBy = request.requestBy;
        repository.update(id, role);
        return role;
    }

    @Override
    public boolean delete(String id) {
        return repository.delete(id);
    }

    @Override
    public Role get(String id) {
        Optional<Role> role = repository.query(new Document("_id", id)).findOne();
        if (!role.isPresent()) {
            throw new NotFoundException("missing role, id={}", id);
        }
        return role.get();
    }
}
