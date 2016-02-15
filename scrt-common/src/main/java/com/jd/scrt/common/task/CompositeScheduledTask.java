package com.jd.scrt.common.task;

import java.util.List;
import java.util.Map;

/**
 * 组合模式调度任务
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public class CompositeScheduledTask<T> extends AbstractScheduledTask<T> {

    private TaskSelector<T> taskSelector;
    private TaskFilter<T> taskFilter;
    private TaskExecutor<T> taskExecutor;
    private TaskHandler<T> taskHandler;

    @Override
    public Map<String, Object> exchangeData(String key, Map<String, Object> param) throws Exception {
        return null;
    }

    @Override
    public List<T> select(Map<String, Object> param) throws Exception {
        if (this.getTaskSelector() != null) {
            return this.getTaskSelector().select(param);
        } else {
            return super.select(param);
        }
    }

    @Override
    public List<T> filter(List<T> list, Map<String, Object> param) throws Exception {
        if (this.getTaskFilter() != null) {
            return this.getTaskFilter().filter(list, param);
        } else if (this.getTaskSelector() != null) {
            return this.getTaskSelector().filter(list, param);
        } else {
            return super.filter(list, param);
        }
    }

    @Override
    public boolean execute(List<T> list) throws Exception {
        if (this.getTaskExecutor() != null) {
            return this.getTaskExecutor().execute(list);
        } else {
            return super.execute(list);
        }
    }

    @Override
    public boolean execute(T t) throws Exception {
        if (this.getTaskExecutor() != null) {
            return this.getTaskExecutor().execute(t);
        } else {
            return super.execute(t);
        }
    }

    @Override
    public boolean handleTask(T t) throws Exception {
        if (this.getTaskHandler() != null) {
            return this.getTaskHandler().handleTask(t);
        } else if (this.getTaskExecutor() != null) {
            return this.getTaskExecutor().handleTask(t);
        } else {
            throw new UnsupportedOperationException(
                    "Task handleTask operation is not supported, Override this method please!");
        }
    }

    // ---------- getter and setter----------//
    protected TaskSelector<T> getTaskSelector() {
        return taskSelector;
    }

    public void setTaskSelector(TaskSelector<T> taskSelector) {
        this.taskSelector = taskSelector;
    }

    protected TaskFilter<T> getTaskFilter() {
        return taskFilter;
    }

    public void setTaskFilter(TaskFilter<T> taskFilter) {
        this.taskFilter = taskFilter;
    }

    protected TaskExecutor<T> getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor<T> taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    protected TaskHandler<T> getTaskHandler() {
        return taskHandler;
    }

    public void setTaskHandler(TaskHandler<T> taskHandler) {
        this.taskHandler = taskHandler;
    }

}
