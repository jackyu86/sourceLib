package com.jd.scrt.common.task.support;

import java.util.List;
import java.util.Map;

/**
 * 组合模式任务支持者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public class CompositeTaskSupporter<T> extends AbstractTaskSupporter<T> {

    private TaskSelectSupporter<T> taskSelectSupporter;
    private TaskFilterSupporter<T> taskFilterSupporter;
    private TaskExecuteSupporter<T> taskExecuteSupporter;

    @Override
    public List<T> selectTask(Map<String, Object> param) throws Exception {
        if (this.getTaskSelectSupporter() != null) {
            return this.getTaskSelectSupporter().selectTask(param);
        } else {
            return super.selectTask(param);
        }
    }

    @Override
    public List<T> filterTask(List<T> list, Map<String, Object> param) throws Exception {
        if (this.getTaskFilterSupporter() != null) {
            return this.getTaskFilterSupporter().filterTask(list, param);
        } else if (this.getTaskSelectSupporter() != null) {
            return this.getTaskSelectSupporter().filterTask(list, param);
        } else {
            return super.filterTask(list, param);
        }
    }

    @Override
    public boolean executeBegin(T t) throws Exception {
        if (this.getTaskExecuteSupporter() != null) {
            return this.getTaskExecuteSupporter().executeBegin(t);
        } else {
            return super.executeBegin(t);
        }
    }

    @Override
    public boolean executeSucceed(T t) throws Exception {
        if (this.getTaskExecuteSupporter() != null) {
            return this.getTaskExecuteSupporter().executeSucceed(t);
        } else {
            return super.executeSucceed(t);
        }
    }

    @Override
    public boolean executeSucceedException(T t) throws Exception {
        if (this.getTaskExecuteSupporter() != null) {
            return this.getTaskExecuteSupporter().executeSucceedException(t);
        } else {
            return super.executeSucceedException(t);
        }
    }

    @Override
    public boolean executeFailed(T t) throws Exception {
        if (this.getTaskExecuteSupporter() != null) {
            return this.getTaskExecuteSupporter().executeFailed(t);
        } else {
            return super.executeFailed(t);
        }
    }

    @Override
    public boolean executeFailedException(T t) throws Exception {
        if (this.getTaskExecuteSupporter() != null) {
            return this.getTaskExecuteSupporter().executeFailedException(t);
        } else {
            return super.executeFailedException(t);
        }
    }

    @Override
    public boolean executeException(T t) throws Exception {
        if (this.getTaskExecuteSupporter() != null) {
            return this.getTaskExecuteSupporter().executeException(t);
        } else {
            return super.executeException(t);
        }
    }

    @Override
    public boolean executeEnd(T t) throws Exception {
        if (this.getTaskExecuteSupporter() != null) {
            return this.getTaskExecuteSupporter().executeEnd(t);
        } else {
            return super.executeEnd(t);
        }
    }

    // ---------- getter and setter----------//
    protected TaskSelectSupporter<T> getTaskSelectSupporter() {
        return taskSelectSupporter;
    }

    public void setTaskSelectSupporter(TaskSelectSupporter<T> taskSelectSupporter) {
        this.taskSelectSupporter = taskSelectSupporter;
    }

    protected TaskFilterSupporter<T> getTaskFilterSupporter() {
        return taskFilterSupporter;
    }

    public void setTaskFilterSupporter(TaskFilterSupporter<T> taskFilterSupporter) {
        this.taskFilterSupporter = taskFilterSupporter;
    }

    protected TaskExecuteSupporter<T> getTaskExecuteSupporter() {
        return taskExecuteSupporter;
    }

    public void setTaskExecuteSupporter(TaskExecuteSupporter<T> taskExecuteSupporter) {
        this.taskExecuteSupporter = taskExecuteSupporter;
    }

}
