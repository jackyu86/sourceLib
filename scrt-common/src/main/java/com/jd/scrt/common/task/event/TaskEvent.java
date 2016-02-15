package com.jd.scrt.common.task.event;


import com.jd.scrt.common.event.Event;
import com.jd.scrt.common.task.Task;

/**
 * 任务事件
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class TaskEvent extends Event {

    private static final long serialVersionUID = 1L;

    public TaskEvent(Task<?> source) {
        super(source);
    }

    public Task<?> getSource() {
        return (Task<?>) source;
    }

}
