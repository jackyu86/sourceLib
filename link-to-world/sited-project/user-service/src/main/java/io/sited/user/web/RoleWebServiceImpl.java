package io.sited.user.web;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import io.sited.db.FindView;
import io.sited.user.api.RoleWebService;
import io.sited.user.api.role.CreateRoleRequest;
import io.sited.user.api.role.RoleQuery;
import io.sited.user.api.role.RoleResponse;
import io.sited.user.api.role.UpdateRoleRequest;
import io.sited.user.domain.Role;
import io.sited.user.service.RoleService;

import javax.inject.Inject;
import java.util.Optional;

/**
 * @author chi
 */
public class RoleWebServiceImpl implements RoleWebService {
    @Inject
    RoleService roleService;

    @Override
    public RoleResponse get(String id) {
        return response(roleService.get(id));
    }

    @Override
    public FindView<RoleResponse> find(RoleQuery request) {
        return FindView.map(roleService.find(request), this::response);
    }

    @Override
    public Optional<RoleResponse> findByName(String name) {
        Optional<Role> role = roleService.findByName(name);
        if (role.isPresent()) {
            return Optional.of(response(role.get()));
        }
        return Optional.empty();
    }

    @Override
    public RoleResponse create(CreateRoleRequest request) {
        Role role = roleService.create(request);
        return response(role);
    }

    @Override
    public RoleResponse update(String id, UpdateRoleRequest request) {
        Role role = roleService.update(id, request);
        return response(role);
    }

    @Override
    public void delete(String id) {
        roleService.delete(id);
    }

    private RoleResponse response(Role role) {
        RoleResponse response = new RoleResponse();
        response.id = role.id;
        response.name = role.name;
        response.displayName = role.displayName;
        response.description = role.description;
        if (role.permissions != null) {
            response.permissions = Splitter.on(';').splitToList(role.permissions);
        } else {
            response.permissions = Lists.newArrayList();
        }
        response.createdTime = role.createdTime;
        response.createdBy = role.createdBy;
        response.updatedTime = role.updatedTime;
        response.updatedBy = role.updatedBy;
        return response;
    }
}
