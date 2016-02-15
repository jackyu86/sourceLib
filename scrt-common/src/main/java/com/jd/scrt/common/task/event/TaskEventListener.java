package com.jd.scrt.common.task.event;


import com.jd.scrt.common.event.EventListener;

/**
 * 任务事件监听器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public interface TaskEventListener extends EventListener<TaskEvent> {

    /**
     * 处理事件
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param e
     * @return
     * @throws Exception
     */
    public boolean processEvent(TaskEvent e) throws Exception;
}
