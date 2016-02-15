package com.jd.scrt.common.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.jd.scrt.common.util.GenericsUtils;
import org.apache.log4j.Logger;


/**
 * 事件派发者抽象实现类
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public abstract class AbstractEventDispatcher implements EventDispatcher, EventDispatchCallback {

    protected final Logger logger = Logger.getLogger(this.getClass());

    protected static final String LISTENER_METHOD = "processEvent";

    private final ReentrantLock listenerLock = new ReentrantLock();

    /**
     * 事件监听者Map（此处不可使用ConcurrentMap，因为需要保存key值为null的数据）
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    // private List<EventListener<? extends Event>> listenerList = new
    // ArrayList<EventListener<? extends Event>>();
    private volatile Map<Class<? extends Event>, List<EventListener<? extends Event>>> listenerMap = new HashMap<Class<? extends Event>, List<EventListener<? extends Event>>>();

    protected abstract boolean dispartchEvent(Collection<EventListener<? extends Event>> listeners, Event event, EventDispatchCallback callback)
            throws Exception;

    @Override
    public boolean dispatchEvent(Event event) throws Exception {
        return this.dispatchEvent(event, this);
    }

    @Override
    public boolean dispatchEvent(Event event, EventDispatchCallback callback) throws Exception {
        if (event == null) {
            logger.warn("dispatchEvent: parameter cannot be null, return false!");
            return false;
        }
        Collection<EventListener<? extends Event>> listeners = this.getListeners(event);
        return this.dispartchEvent(listeners, event, callback);
    }

    protected Collection<EventListener<? extends Event>> getListeners(Event event) throws Exception {
        List<EventListener<? extends Event>> listenerList = new ArrayList<EventListener<? extends Event>>();
        List<EventListener<? extends Event>> event_listeners = listenerMap.get(event.getClass());
        if (event_listeners != null && event_listeners.size() > 0) {
            listenerList.addAll(event_listeners);
        }
        List<EventListener<? extends Event>> null_listeners = listenerMap.get(null);
        if (null_listeners != null && null_listeners.size() > 0) {
            listenerList.addAll(null_listeners);
        }
        return listenerList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void registerListener(EventListener<? extends Event> listener) throws Exception {
        listenerLock.lock();
        try {
            Class<? extends Event> event_class = (Class<? extends Event>) GenericsUtils.getInterfacesClassGenricType(listener.getClass());
            List<EventListener<? extends Event>> listenerList = listenerMap.get(event_class);
            if (listenerList == null) {
                listenerList = new ArrayList<EventListener<? extends Event>>();
                listenerMap.put(event_class, listenerList);
            }
            listenerList.add(listener);
        } finally {
            listenerLock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void removeListener(EventListener<? extends Event> listener) throws Exception {
        listenerLock.lock();
        try {
            Class<? extends Event> event_class = (Class<? extends Event>) GenericsUtils.getInterfacesClassGenricType(listener.getClass());
            List<EventListener<? extends Event>> listenerList = listenerMap.get(event_class);
            if (listenerList == null) {
                listenerList = new ArrayList<EventListener<? extends Event>>();
                listenerMap.put(event_class, listenerList);
            }
            listenerList.remove(listener);
        } finally {
            listenerLock.unlock();
        }
    }

    @Override
    public void clearListener() throws Exception {
        listenerLock.lock();
        try {
            listenerMap.clear();
        } finally {
            listenerLock.unlock();
        }
    }

    @Override
    public boolean dispartchEventSucceed(EventListener<? extends Event> listener, Event event) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("dispartchEventSucceed: listener[" + listener + "],event[" + event + "],type[" + event.getType() + "],data["
                    + event.getData() + "]");
        }
        return true;
    }

    @Override
    public boolean dispartchEventFailed(EventListener<? extends Event> listener, Event event) throws Exception {
        logger.warn("dispatchEventFailed: listener[" + listener + "],event[" + event + "],type[" + event.getType() + "],data[" + event.getData()
                + "]");
        return true;
    }

}
