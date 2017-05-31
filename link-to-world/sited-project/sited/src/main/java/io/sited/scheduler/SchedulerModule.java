package io.sited.scheduler;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.scheduler.impl.Scheduler;
import io.sited.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chi
 */
@ModuleInfo(name = "scheduler")
public class SchedulerModule extends Module implements SchedulerConfig {
    private final Logger logger = LoggerFactory.getLogger(SchedulerModule.class);
    private Scheduler scheduler;

    @Override
    public SchedulerConfig schedule(Runnable job, JobOptions jobOptions) {
        logger.info("schedule job, job={}, options={}", job.getClass().getCanonicalName(), JSON.toJSON(jobOptions));
        scheduler.schedule(job, jobOptions);
        return this;
    }

    @Override
    protected void configure() throws Exception {
        SchedulerOptions options = options(SchedulerOptions.class);
        scheduler = new Scheduler(options.job.threads);

        if (options.job.enabled) {
            onStartup(scheduler::start);
            onShutdown(scheduler::stop);
        } else {
            logger.info("scheduler job is disabled");
        }

        bind(SchedulerConfig.class, this);
        export(SchedulerConfig.class);
    }
}
