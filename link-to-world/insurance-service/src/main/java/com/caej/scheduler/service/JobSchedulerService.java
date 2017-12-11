package com.caej.scheduler.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Inject;

import com.caej.scheduler.domain.JobScheduler;
import com.caej.scheduler.domain.JobSchedulerStatus;

import io.sited.db.JDBCRepository;

/**
 * @author miller
 */
public class JobSchedulerService {
    @Inject
    JDBCRepository<JobScheduler> repository;

    public boolean canRun(String name) {
        Optional<JobScheduler> optional = findByName(name);
        if (!optional.isPresent()) return false;
        JobScheduler jobScheduler = optional.get();
        if (JobSchedulerStatus.PROCESSING.name().equals(jobScheduler.status.name())) return false;
        long effectedRows = toProcess(name, jobScheduler.updatedTime);
        return effectedRows > 0;
    }

    public Optional<JobScheduler> findByName(String name) {
        return repository.query("name=?", name).findOne();
    }

    private long toProcess(String name, LocalDateTime updatedTime) {
        return repository.query("update job_scheduler set status='PROCESSING',updated_time=? where status='WAITING' and updated_time=? and name=?",
            LocalDateTime.now(), updatedTime, name).execute();
    }

    public void toWait(String name) {
        repository.query("update job_scheduler set status='WAITING',updated_time=? where name=?", LocalDateTime.now(), name).execute();
    }

    public void toError(String name, String error) {
        repository.query("update job_scheduler set status='WAITING',updated_time=?,last_error=? where name=?", LocalDateTime.now(), error, name).execute();
    }

    public void create(JobScheduler jobScheduler) {
        repository.insert(jobScheduler);
    }
}
