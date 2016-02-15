package com.jd.scrt.common.task;

import java.util.List;

/**
 * 任务执行者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface TaskExecutor<T> extends TaskHandler<T> {

    /**
     * 批量任务执行
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean execute(List<T> list) throws Exception;

    /**
     * 执行任务
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean execute(T t) throws Exception;
}
