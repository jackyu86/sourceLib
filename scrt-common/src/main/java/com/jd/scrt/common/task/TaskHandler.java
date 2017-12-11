package com.jd.scrt.common.task;

/**
 * 任务处理者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface TaskHandler<T> {

    /**
     * 处理任务
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean handleTask(T t) throws Exception;
}
