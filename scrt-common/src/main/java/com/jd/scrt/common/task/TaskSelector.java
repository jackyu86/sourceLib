package com.jd.scrt.common.task;

import java.util.List;
import java.util.Map;

/**
 * 任务获取者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface TaskSelector<T> extends TaskFilter<T> {

    /**
     * 根据参数获取任务
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param param
     * @return
     * @throws Exception
     */
    public List<T> select(Map<String, Object> param) throws Exception;
}
