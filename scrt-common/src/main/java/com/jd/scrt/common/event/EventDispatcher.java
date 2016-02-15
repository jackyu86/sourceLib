package com.jd.scrt.common.event;

/**
 * 事件派发者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public interface EventDispatcher {

    /**
     * 派发事件
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    public boolean dispatchEvent(Event event) throws Exception;

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
    public boolean dispatchEvent(Event event, EventDispatchCallback callback) throws Exception;

    /**
     * 注册监听者
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param listener
     */
    public void registerListener(EventListener<? extends Event> listener) throws Exception;

    /**
     * 移除监听者
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param listener
     */
    public void removeListener(EventListener<? extends Event> listener) throws Exception;

    /**
     * 清空监听者
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    public void clearListener() throws Exception;
}
