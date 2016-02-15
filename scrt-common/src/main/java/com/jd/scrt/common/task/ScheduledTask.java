package com.jd.scrt.common.task;

/**
 * 被调度的任务接口
 *
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * 
 * @param <T>
 * @since 1.0.3
 */
public interface ScheduledTask<T> extends Task<T>, TaskSelector<T>, TaskExecutor<T> {

}
