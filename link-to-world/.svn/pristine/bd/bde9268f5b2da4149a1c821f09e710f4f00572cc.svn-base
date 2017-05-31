package io.sited.scheduler.impl;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.Service;
import io.sited.scheduler.JobOptions;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author chi
 */
public class Scheduler {
    private final ScheduledExecutorService scheduledExecutorService;
    private final List<Service> services = Lists.newArrayList();

    public Scheduler(int threads) {
        scheduledExecutorService = Executors.newScheduledThreadPool(threads);
    }

    public void schedule(Runnable job, JobOptions jobOptions) {
        services.add(new AbstractScheduledService() {
            @Override
            protected void runOneIteration() throws Exception {
                job.run();
            }

            @Override
            protected ScheduledExecutorService executor() {
                return scheduledExecutorService;
            }

            @Override
            protected Scheduler scheduler() {
                if (jobOptions.fixedInterval) {
                    return Scheduler.newFixedRateSchedule(0, jobOptions.interval.toMillis(), TimeUnit.MILLISECONDS);
                } else {
                    return Scheduler.newFixedDelaySchedule(0, jobOptions.interval.toMillis(), TimeUnit.MILLISECONDS);
                }
            }
        });
    }

    public void start() {
        services.forEach(Service::startAsync);
    }

    public void stop() {
        services.forEach(Service::stopAsync);
    }
}
