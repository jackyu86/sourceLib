package com.jd.scrt.common.task.event;

import com.jd.scrt.common.task.Task;

import java.util.List;
import java.util.Map;


/**
 * 任务过滤事件
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class TaskFilterEvent extends TaskEvent {

    private static final long serialVersionUID = 1L;

    private List<?> invokeArgs1;
    private Map<String, Object> invokeArgs2;
    private List<?> invokeReturn;

    public TaskFilterEvent(Task<?> source) {
        super(source);
    }

    public List<?> getInvokeArgs1() {
        return invokeArgs1;
    }

    public void setInvokeArgs1(List<?> invokeArgs1) {
        this.invokeArgs1 = invokeArgs1;
    }

    public Map<String, Object> getInvokeArgs2() {
        return invokeArgs2;
    }

    public void setInvokeArgs2(Map<String, Object> invokeArgs2) {
        this.invokeArgs2 = invokeArgs2;
    }

    public List<?> getInvokeReturn() {
        return invokeReturn;
    }

    public void setInvokeReturn(List<?> invokeReturn) {
        this.invokeReturn = invokeReturn;
    }
}
