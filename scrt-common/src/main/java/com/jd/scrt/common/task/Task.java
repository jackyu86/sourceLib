package com.jd.scrt.common.task;

import com.jd.scrt.common.task.event.TaskEvent;

import java.util.Map;


/**
 * 任务接口
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface Task<T> extends Runnable {

    /**
     * 任务初始化
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    public void init() throws Exception;

    /**
     * 任务销毁
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    public void destroy() throws Exception;

    /**
     * 交换数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public Map<String, Object> exchangeData(String key, Map<String, Object> param) throws Exception;

    /**
     * 设置任务上下文参数
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @param value
     * @throws Exception
     */
    public void setContextAttribute(String key, Object value) throws Exception;

    /**
     * 获取任务上下文参数
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @return
     * @throws Exception
     */
    public Object getContextAttribute(String key) throws Exception;

    /**
     * 获取任务名称
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @return
     * @throws Exception
     */
    public String getDisplayName() throws Exception;

    /**
     * 设置任务名称
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param taskName
     * @throws Exception
     */
    public void setDisplayName(String displayName) throws Exception;

    /**
     * 派发事件
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param e
     * @return
     * @throws Exception
     */
    public boolean dispatchEvent(TaskEvent event) throws Exception;

}
