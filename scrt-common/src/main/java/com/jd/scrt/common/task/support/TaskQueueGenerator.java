package com.jd.scrt.common.task.support;

/**
 * 任务队列生成器
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public interface TaskQueueGenerator {

    /**
     * 根据queueKey生成相应的queueId
     *
     * @param queueKey queue标识
     * @param ref      生成参照
     * @return
     * @throws Exception
     */
    public String generateTaskQueue(String queueKey, String ref) throws Exception;
}
