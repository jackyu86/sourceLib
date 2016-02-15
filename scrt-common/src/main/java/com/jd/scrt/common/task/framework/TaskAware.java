package com.jd.scrt.common.task.framework;


import com.jd.scrt.common.task.Task;

/**
 * 任务框架应有的意识
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public interface TaskAware<T> {

    /**
     * 设置通用业务任务
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param task
     */
    public void setTask(Task<T> task);

}
