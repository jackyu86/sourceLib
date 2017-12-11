package com.caej.underwriting;

import java.time.Duration;
import java.time.LocalTime;

import com.caej.api.KdlinsApiModule;
import com.caej.insurance.api.InsuranceModule;
import com.caej.order.OrderModule;
import com.caej.product.api.ProductModule;
import com.caej.scheduler.JobSchedulerModule;
import com.caej.underwriting.api.UnderwritingModule;
import com.caej.underwriting.api.UnderwritingWebService;
import com.caej.underwriting.domain.UnderwritingRecord;
import com.caej.underwriting.job.UnderwritingJob;
import com.caej.underwriting.service.KdlinsUnderWritingClient;
import com.caej.underwriting.service.UnderWritingClient;
import com.caej.underwriting.service.UnderwritingRecordService;
import com.caej.underwriting.service.UnderwritingRequestBuildService;
import com.caej.underwriting.service.UnderwritingService;
import com.caej.underwriting.web.UnderwritingWebServiceImpl;

import io.sited.ModuleInfo;
import io.sited.api.APIConfig;
import io.sited.api.APIModule;
import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.scheduler.JobOptions;
import io.sited.scheduler.SchedulerConfig;
import io.sited.scheduler.SchedulerModule;

/**
 * @author miller
 */
@ModuleInfo(name = "underwriting.api", require = {APIModule.class, DBModule.class, KdlinsApiModule.class, InsuranceModule.class, ProductModule.class,
    OrderModule.class, SchedulerModule.class, JobSchedulerModule.class})
public class UnderwritingModuleImpl extends UnderwritingModule {
    @Override
    protected void configure() throws Exception {
        MongoConfig mongoConfig = require(MongoConfig.class);
        mongoConfig.entity(UnderwritingRecord.class);
        bind(UnderwritingRecordService.class);

        UnderwritingOptions underwritingConfig = options(UnderwritingOptions.class);
        UnderwritingService underwritingService = new UnderwritingService(underwritingConfig);
        bind(UnderwritingService.class, underwritingService);
        bind(UnderwritingRequestBuildService.class);
        bind(UnderWritingClient.class, bind(KdlinsUnderWritingClient.class));

        if (underwritingConfig.jobEnabled) {
            JobOptions jobOptions = new JobOptions();
            jobOptions.start = LocalTime.MIN;
            jobOptions.interval = Duration.ofSeconds(Long.valueOf(underwritingConfig.jobInterval));
            require(SchedulerConfig.class)
                .schedule(bind(UnderwritingJob.class), jobOptions);
        }

        APIConfig apiConfig = require(APIConfig.class);
        apiConfig.service(UnderwritingWebService.class, UnderwritingWebServiceImpl.class);
    }
}
