package com.caej.admin;

import com.caej.admin.insuranceenum.EnumTypeAdminController;
import com.caej.admin.insuranceenum.EnumValueAdminController;
import com.caej.insurance.api.InsuranceModule;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.admin.AdminConfig;
import io.sited.admin.AdminModule;

/**
 * @author chi
 */
@ModuleInfo(name = "insurance-enum.admin", require = {InsuranceModule.class, AdminModule.class})
public class InsuranceEnumModule extends Module {
    @Override
    protected void configure() throws Exception {
        AdminConfig adminConfig = require(AdminConfig.class);
        adminConfig
            .controller(EnumTypeAdminController.class)
            .controller(EnumValueAdminController.class);

        adminConfig.console().install("enum");
    }
}
