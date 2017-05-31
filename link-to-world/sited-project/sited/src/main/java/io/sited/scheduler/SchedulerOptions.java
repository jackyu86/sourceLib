package io.sited.scheduler;

/**
 * @author chi
 */
public class SchedulerOptions {
    public Integer threads = 10;
    public SchedulerJobOptions job = new SchedulerJobOptions();

    public static class SchedulerJobOptions {
        public Integer threads = 10;
        public Boolean enabled = true;
    }
}
