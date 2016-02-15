package com.jd.scrt.common.task.event;


import com.jd.scrt.common.task.Task;

/**
 * 任务执行事件(event type 1~9 为内置事件)
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class TaskExecuteEvent extends TaskEvent {

    private static final long serialVersionUID = 1L;

    public static final int EXECUTE_BEGIN = 1;
    public static final int EXECUTE_SUCCEED = 2;
    public static final int EXECUTE_SUCCEED_EXCEPTION = 3;
    public static final int EXECUTE_FAILED = 4;
    public static final int EXECUTE_FAILED_EXCEPTION = 5;
    public static final int EXECUTE_EXCEPTION = 6;
    public static final int EXECUTE_END = 7;

    public static final int SELECT_TASK = 8;
    public static final int FILTER_TASK = 9;

    public TaskExecuteEvent(Task<?> source) {
        super(source);
    }

}
