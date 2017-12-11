package io.sited.admin.impl.web.ajax;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.sited.StandardException;
import io.sited.admin.impl.web.ajax.permission.PermissionGroup;
import io.sited.http.GET;
import io.sited.http.HttpConfig;
import io.sited.http.Path;
import io.sited.http.impl.HandlerRef;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class PermissionAJAXController {
    @Inject
    HttpConfig httpConfig;

    @Path("/admin/ajax/permission")
    @GET
    public List<PermissionGroup> list() {
        List<HandlerRef> handlerRefs = httpConfig.handlerRefs();
        Map<String, Set<String>> permissionGroups = Maps.newHashMap();

        handlerRefs.forEach(handlerRef -> {
            if (handlerRef.permission != null) {
                String module = moduleName(handlerRef.permission);
                if (permissionGroups.containsKey(module)) {
                    Set<String> permissions = permissionGroups.get(module);
                    if (!permissions.contains(handlerRef.permission)) {
                        permissions.add(handlerRef.permission);
                    }
                } else {
                    permissionGroups.put(module, Sets.newHashSet(handlerRef.permission));
                }
            }
        });

        return permissionGroups.entrySet().stream().map(entry -> {
            PermissionGroup permissionGroup = new PermissionGroup();
            permissionGroup.name = entry.getKey();
            permissionGroup.permissions = Lists.newArrayList(entry.getValue());
            return permissionGroup;
        }).collect(Collectors.toList());
    }

    private String moduleName(String permission) {
        int p = permission.lastIndexOf('.');
        if (p <= 0) {
            throw new StandardException("invalid permission, permission={}", permission);
        }
        return permission.substring(0, p);
    }
}
