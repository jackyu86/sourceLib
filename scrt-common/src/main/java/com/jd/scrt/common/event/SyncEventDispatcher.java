package com.jd.scrt.common.event;

import java.util.Collection;
import java.util.Iterator;

/**
 * 同步实现派发事件
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class SyncEventDispatcher extends AbstractEventDispatcher {

    @Override
    protected boolean dispartchEvent(Collection<EventListener<? extends Event>> listeners, Event event, EventDispatchCallback callback)
            throws Exception {

        Iterator<EventListener<? extends Event>> iterator = listeners.iterator();
        EventListener<? extends Event> listener = null;
        if (listeners.size() == 1) {
            listener = iterator.next();
            if (this.dispartchEvent(listener, event)) {// 如果处理成功
                if (callback != null) {
                    callback.dispartchEventSucceed(listener, event);
                }
                return true;
            } else {
                if (callback != null) {
                    callback.dispartchEventFailed(listener, event);
                }
                return false;
            }
        } else {
            while (iterator.hasNext()) {
                listener = iterator.next();

                if (listener == null) {
                    continue;
                }
                if (this.dispartchEvent(listener, event)) {// 如果处理成功
                    if (callback != null) {
                        callback.dispartchEventSucceed(listener, event);
                    }
                } else {
                    if (callback != null) {
                        callback.dispartchEventFailed(listener, event);
                    }
                }
            }
            return true;
        }
    }

    @SuppressWarnings("unchecked")
    protected boolean dispartchEvent(EventListener<? extends Event> listener, Event event) throws Exception {
        if (listener == null || event == null) {
            logger.warn("dispartchEvent: listener or event cannot be null!");
            return false;
        }
        try {
            EventListener<Event> simple_listener = (EventListener<Event>) listener;
            return simple_listener.processEvent(event);
        } catch (NoSuchMethodException e) {
            logger.error("dispartchEvent-error: 尚未实现事件的处理方法(public boolean processEvent(Event e) throws Exception;)！", e);
            return false;
        } catch (Throwable t) {
            logger.error("dispartchEvent-error:", t);
            return false;
        }
    }
}
