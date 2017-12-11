package com.jd.scrt.common.task;

import java.util.List;
import java.util.Map;

/**
 * 任务过滤器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface TaskFilter<T> {

    /**
     * 过滤所获取的任务
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param list
     * @param param
     * @return
     * @throws Exception
     */
    public List<T> filter(List<T> list, Map<String, Object> param) throws Exception;
}
