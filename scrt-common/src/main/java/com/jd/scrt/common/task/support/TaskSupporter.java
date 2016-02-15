package com.jd.scrt.common.task.support;


import com.jd.scrt.common.task.event.TaskEventListener;

/**
 * 任务获取及执行的全能支持者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface TaskSupporter<T> extends TaskEventListener, TaskSelectSupporter<T>, TaskExecuteSupporter<T> {

}
