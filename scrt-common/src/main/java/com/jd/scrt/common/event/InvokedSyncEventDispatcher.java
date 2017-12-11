package com.jd.scrt.common.event;

import java.lang.reflect.Method;


/**
 * 反射实现调用监听者的处理方法
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class InvokedSyncEventDispatcher extends SyncEventDispatcher {

    protected boolean dispartchEvent(EventListener<? extends Event> listener, Event event) throws Exception {
        if (listener == null || event == null) {
            logger.warn("dispartchEvent: listener or event cannot be null!");
            return false;
        }
        try {
            // 通过反射，将事件实体注入并执行事件监听器的事件处理方法(重载方式)
            Method method = this.getProcessEventMethod(listener, event);
            Boolean b = (Boolean) method.invoke(listener, event);
            return b == null ? false : b.booleanValue();
        } catch (NoSuchMethodException e) {
            logger.error("dispartchEvent-error: 尚未实现事件的处理方法(重载方法)！", e);
            return false;
        } catch (Throwable t) {
            logger.error("dispartchEvent-error:", t);
            return false;
        }
    }

    protected Method getProcessEventMethod(EventListener<? extends Event> listener, Event event) throws Exception {
        return listener.getClass().getMethod(LISTENER_METHOD, event.getClass());
    }

}
