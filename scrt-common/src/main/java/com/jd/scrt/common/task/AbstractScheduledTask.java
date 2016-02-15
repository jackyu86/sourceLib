package com.jd.scrt.common.task;

import java.util.List;
import java.util.Map;

/**
 * 调度任务抽象实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public abstract class AbstractScheduledTask<T> extends AbstractTask<T> implements ScheduledTask<T> {

    /**
     * 获取查询参数key（exchangeData中参数key的一个常量）
     */
    public static final String KEY_SELECT_PARAM = "selectParam";

    @Override
    public void run() {
        try {
            List<T> list = this.select(null);
            this.execute(list);
        } catch (Throwable t) {
            logger.error("run-error:", t);
        }
    }

    @Override
    public List<T> select(Map<String, Object> param) throws Exception {
        throw new UnsupportedOperationException("Task select operation is not supported, Override this method please!");
    }

    @Override
    public List<T> filter(List<T> list, Map<String, Object> param) throws Exception {
        return list;
    }

    @Override
    public boolean execute(List<T> list) throws Exception {
        if (list == null || list.size() < 1) {
            return false;
        }
        for (T t : list) {
            this.execute(t);
        }
        return true;
    }

    @Override
    public boolean execute(T t) throws Exception {
        return this.handleTask(t);
    }
}
