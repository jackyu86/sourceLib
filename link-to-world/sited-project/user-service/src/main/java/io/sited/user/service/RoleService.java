package io.sited.user.service;

import io.sited.db.FindView;
import io.sited.user.api.role.CreateRoleRequest;
import io.sited.user.api.role.RoleQuery;
import io.sited.user.api.role.UpdateRoleRequest;
import io.sited.user.domain.Role;

import java.util.Optional;

/**
 * @author chi
 */
public interface RoleService {
    Role get(String id);

    Optional<Role> findByName(String name);

    FindView<Role> find(RoleQuery query);

    boolean isRoleExists(String roleName);

    Role create(CreateRoleRequest role);

    Role update(String id, UpdateRoleRequest role);

    boolean delete(String id);
}
