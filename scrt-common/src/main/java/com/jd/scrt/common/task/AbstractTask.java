package com.jd.scrt.common.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jd.scrt.common.event.*;
import com.jd.scrt.common.task.event.TaskEvent;
import com.jd.scrt.common.task.event.TaskEventListener;
import org.apache.log4j.Logger;

/**
 * 拥有上下文环境的抽象任务
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public abstract class AbstractTask<T> implements Task<T>, EventDispatchCallback {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private static final String KEY_DISPLAY_NAME = "KEY_DISPLAY_NAME";

    private volatile ConcurrentMap<String, Object> taskContextMap = new ConcurrentHashMap<String, Object>();

    private volatile EventDispatcher eventDispatcher = new InvokedSyncEventDispatcher();

    @Override
    public void init() throws Exception {

    }

    @Override
    public void run() {

    }

    @Override
    public void destroy() throws Exception {
        taskContextMap.clear();
        eventDispatcher.clearListener();
    }

    @Override
    public void setContextAttribute(String key, Object value) throws Exception {
        taskContextMap.put(key, value);
    }

    @Override
    public Object getContextAttribute(String key) throws Exception {
        return taskContextMap.get(key);
    }

    @Override
    public String getDisplayName() throws Exception {
        return (String) this.getContextAttribute(KEY_DISPLAY_NAME);
    }

    @Override
    public void setDisplayName(String displayName) throws Exception {
        this.setContextAttribute(KEY_DISPLAY_NAME, displayName);
    }

    public boolean dispatchEvent(TaskEvent event) throws Exception {
        return this.dispatchEvent(event, this);
    }

    /**
     * 派发事件(带有回调方法)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @param callback
     * @return
     * @throws Exception
     */
    public boolean dispatchEvent(TaskEvent event, EventDispatchCallback callback) throws Exception {
        return eventDispatcher.dispatchEvent(event, callback);
    }

    /**
     * 设置注入TaskContext
     *
     * @param taskContextMap
     */
    public void setTaskContextMap(Map<String, Object> taskContextMap) {
        if (taskContextMap != null && taskContextMap.size() > 0) {
            this.taskContextMap.putAll(taskContextMap);
        }
    }

    /**
     * 设置注入EventDispatcher
     *
     * @param eventDispatcher
     */
    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public void registerListener(TaskEventListener listener) throws Exception {
        this.eventDispatcher.registerListener(listener);
    }

    /**
     * 设置注入TaskEventListener
     *
     * @param listener
     * @throws Exception
     */
    public void setListener(TaskEventListener listener) throws Exception {
        if (listener != null) {
            this.registerListener(listener);
        }
    }

    /**
     * 设置注入ListenerList
     *
     * @param list
     * @throws Exception
     */
    public void setListenerList(List<TaskEventListener> list) throws Exception {
        if (list != null && list.size() > 0) {
            for (TaskEventListener listener : list) {
                if (listener == null) {
                    continue;
                }
                this.registerListener(listener);
            }
        }
    }

    @Override
    public boolean dispartchEventSucceed(EventListener<? extends Event> listener, Event event) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("dispartchEventSucceed: listener[" + listener + "],event[" + event + "],type["
                    + event.getType() + "],data[" + event.getData() + "]");
        }
        return true;
    }

    @Override
    public boolean dispartchEventFailed(EventListener<? extends Event> listener, Event event) throws Exception {
        logger.warn("dispatchEventFailed: listener[" + listener + "],event[" + event + "],type[" + event.getType()
                + "],data[" + event.getData() + "]");
        return true;
    }

}
