package com.jd.scrt.common.task.event;

import com.jd.scrt.common.task.Task;

import java.util.List;
import java.util.Map;


/**
 * 任务获取事件
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class TaskSelectEvent extends TaskEvent {

    private static final long serialVersionUID = 1L;

    private Map<String, Object> invokeArgs1;
    private List<?> invokeReturn;

    public TaskSelectEvent(Task<?> source) {
        super(source);
    }

    public Map<String, Object> getInvokeArgs1() {
        return invokeArgs1;
    }

    public void setInvokeArgs1(Map<String, Object> invokeArgs1) {
        this.invokeArgs1 = invokeArgs1;
    }

    public List<?> getInvokeReturn() {
        return invokeReturn;
    }

    public void setInvokeReturn(List<?> invokeReturn) {
        this.invokeReturn = invokeReturn;
    }

}
