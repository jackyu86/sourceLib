package com.caej.scheduler;

import com.caej.scheduler.domain.JobScheduler;
import com.caej.scheduler.service.JobSchedulerService;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.JDBCConfig;

/**
 * @author miller
 */
@ModuleInfo(name = "job-scheduler", require = {APIModule.class, DBModule.class})
public class JobSchedulerModule extends Module {

    @Override
    protected void configure() throws Exception {
        JDBCConfig jdbcConfig = require(JDBCConfig.class);
        jdbcConfig.entity(JobScheduler.class);
        bind(JobSchedulerService.class);
        export(JobSchedulerService.class);
    }
}