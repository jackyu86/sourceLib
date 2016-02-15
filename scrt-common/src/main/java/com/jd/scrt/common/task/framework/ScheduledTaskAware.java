package com.jd.scrt.common.task.framework;


import com.jd.scrt.common.task.ScheduledTask;

/**
 * 调度任务框架应有的意识
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public interface ScheduledTaskAware<T> {

    /**
     * 设置调度任务
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param task
     */
    public void setScheduledTask(ScheduledTask<T> scheduledTask);

}
