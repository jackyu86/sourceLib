package com.caej.admin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.caej.admin.dealer.InitDealerService;
import com.caej.admin.site.web.IndexController;
import com.caej.insurance.api.InsuranceModule;
import com.caej.product.api.ProductModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;

import app.dealer.api.DealerModule;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.StandardException;
import io.sited.customer.api.CustomerModule;
import io.sited.db.DBModule;
import io.sited.file.admin.FileAdminModule;
import io.sited.http.HttpConfig;
import io.sited.http.impl.HandlerRef;
import io.sited.user.admin.UserAdminModule;
import io.sited.user.api.RoleWebService;
import io.sited.user.api.UserWebService;
import io.sited.user.api.role.CreateRoleRequest;
import io.sited.user.api.role.RoleResponse;
import io.sited.user.api.user.CreateUserRequest;
import io.sited.util.JSON;
import io.sited.web.WebConfig;
import io.sited.web.WebModule;

/**
 * @author chi
 */
@ModuleInfo(name = "back-office", require = {DBModule.class, WebModule.class,
        FileAdminModule.class, UserAdminModule.class, DealerAdminModule.class, TicketAdminModule.class,
        OrderAdminModule.class, CustomerModule.class, DealerModule.class, ProductModule.class,
        InsuranceModule.class})
public class BackOfficeModule extends Module {
    @Override
    protected void configure() throws Exception {
        bind(InitDealerService.class);

        require(WebConfig.class).controller(IndexController.class);
        init();
    }

    private void init() {
        InitDealerService initDealerService = require(InitDealerService.class);
        initDealerService.init("db/dealer.json");

        RoleWebService roleWebService = require(RoleWebService.class);
        Optional<RoleResponse> role = roleWebService.findByName("admin");
        if (!role.isPresent()) {
            roles().forEach(roleWebService::create);
            UserWebService userWebService = require(UserWebService.class);
            users().forEach(userWebService::create);
        }
    }

    public List<String> list() {
        List<HandlerRef> handlerRefs = require(HttpConfig.class).handlerRefs();
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

        List<String> permissions = Lists.newArrayList();

        permissionGroups.entrySet().forEach(entry -> {
            permissions.addAll(entry.getValue());
        });
        return permissions;
    }

    private String moduleName(String permission) {
        int p = permission.lastIndexOf('.');
        if (p <= 0) {
            throw new StandardException("invalid permission, permission={}", permission);
        }
        return permission.substring(0, p);
    }

    @SuppressWarnings("unchecked")
    private List<CreateRoleRequest> roles() {
        try (InputStream inputStream = Resources.getResource("db/role.json").openStream()) {
            List list = JSON.fromJSON(CharStreams.toString(new InputStreamReader(inputStream)), List.class);
            return (List<CreateRoleRequest>) list.stream().map(object -> JSON.convert(object, CreateRoleRequest.class)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    private List<CreateUserRequest> users() {
        try (InputStream inputStream = Resources.getResource("db/user.json").openStream()) {
            List list = JSON.fromJSON(CharStreams.toString(new InputStreamReader(inputStream)), List.class);
            return (List<CreateUserRequest>) list.stream().map(object -> JSON.convert(object, CreateUserRequest.class)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }
}
