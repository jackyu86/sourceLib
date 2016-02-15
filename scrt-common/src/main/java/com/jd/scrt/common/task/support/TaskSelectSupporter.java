package com.jd.scrt.common.task.support;

import java.util.List;
import java.util.Map;

/**
 * 任务获取支持者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface TaskSelectSupporter<T> extends TaskFilterSupporter<T> {

    /**
     * 根据参数获取任务(支持处理)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param param
     * @return
     * @throws Exception
     */
    public List<T> selectTask(Map<String, Object> param) throws Exception;

}
