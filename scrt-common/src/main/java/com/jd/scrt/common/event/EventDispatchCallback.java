package com.jd.scrt.common.event;

/**
 * 事件派发回调接口
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public interface EventDispatchCallback {

    /**
     * 派发事件成功后回调方法
     *
     * @param listener
     * @param event
     * @return
     * @throws Exception
     */
    public boolean dispartchEventSucceed(EventListener<? extends Event> listener, Event event) throws Exception;

    /**
     * 派发事件失败后回调方法
     *
     * @param listener
     * @param event
     * @return
     * @throws Exception
     */
    public boolean dispartchEventFailed(EventListener<? extends Event> listener, Event event) throws Exception;
}
