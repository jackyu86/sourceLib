package com.jd.scrt.common.event;

/**
 * 事件监听者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public interface EventListener<T extends Event> extends java.util.EventListener {

    public boolean processEvent(T event) throws Exception;
}
