package com.jd.scrt.common.task;

import com.jd.scrt.common.task.event.TaskExecuteEvent;
import com.jd.scrt.common.task.event.TaskFilterEvent;
import com.jd.scrt.common.task.event.TaskSelectEvent;
import com.jd.scrt.common.task.support.TaskSupporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 模板模式调度任务
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.3
 */
public abstract class TemplateScheduledTask<T> extends AbstractScheduledTask<T> {

    /**
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> select(Map<String, Object> param) throws Exception {
        // 从具体实现子类提取的查询参数
        Map<String, Object> select_param = this.exchangeData(KEY_SELECT_PARAM, param);

        Map<String, Object> all_param = new HashMap<String, Object>();
        if (param != null) {
            all_param.putAll(param);
        }
        if (select_param != null) {
            all_param.putAll(select_param);
        }

        TaskSelectEvent event = new TaskSelectEvent(this);
        event.setInvokeArgs1(all_param);
        if (this.dispatchEvent(event)) {
            List<T> list = (List<T>) event.getInvokeReturn();
            return this.filter(list, all_param);
        } else {
            logger.warn(this.getDisplayName() + "-select: dispatchEvent TaskEvent.SELECT_TASK fail...");
            return new ArrayList<T>(0);
        }
    }

    /**
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> filter(List<T> list, Map<String, Object> param) throws Exception {
        if (list == null || list.size() == 0) {
            return list;
        }

        TaskFilterEvent event = new TaskFilterEvent(this);
        event.setInvokeArgs1(list);
        event.setInvokeArgs2(param);

        if (this.dispatchEvent(event)) {
            List<T> result = (List<T>) event.getInvokeReturn();
            return result;
        } else {
            logger.warn(this.getDisplayName() + "-filter: dispatchEvent TaskEvent.FILTER_TASK fail...");
            return list;
        }
    }

    /**
     * 任务执行过程逻辑模板
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    @Override
    public boolean execute(T t) throws Exception {
        String display_name = this.getDisplayName();
        try {
            if (this.dispatchTaskExecuteEvent(TaskExecuteEvent.EXECUTE_BEGIN, t)) {
                logger.debug(display_name + "执行executeBegin成功...");
            } else {
                logger.warn(display_name + "执行executeBegin失败！");
                return false;
            }
            boolean is_success = this.handleTask(t);
            if (is_success) {
                logger.debug(display_name + "执行handleTask成功...");
                if (this.dispatchTaskExecuteEvent(TaskExecuteEvent.EXECUTE_SUCCEED, t)) {
                    logger.debug(display_name + "执行executeSucceed成功！");
                    return true;
                } else {
                    logger.warn(display_name + "执行executeSucceed失败...");
                    if (this.dispatchTaskExecuteEvent(TaskExecuteEvent.EXECUTE_SUCCEED_EXCEPTION, t)) {
                        logger.info(display_name + "执行executeSucceedException成功！");
                    } else {
                        logger.warn(display_name + "执行executeSucceedException失败...");
                    }
                }
            } else {
                logger.info(display_name + "执行handleTask失败...");
                if (this.dispatchTaskExecuteEvent(TaskExecuteEvent.EXECUTE_FAILED, t)) {
                    logger.info(display_name + "执行executeFailed成功！");
                } else {
                    logger.warn(display_name + "执行executeFailed失败...");
                    if (this.dispatchTaskExecuteEvent(TaskExecuteEvent.EXECUTE_FAILED_EXCEPTION, t)) {
                        logger.info(display_name + "执行executeFailedException成功！");
                    } else {
                        logger.warn(display_name + "执行executeFailedException失败！");
                    }
                }
            }

        } catch (Throwable e) {
            logger.error(display_name + "运行异常 :" + e.getMessage(), e);
            if (this.dispatchTaskExecuteEvent(TaskExecuteEvent.EXECUTE_EXCEPTION, t)) {
                logger.info(display_name + "执行executeException成功！");
            } else {
                logger.warn(display_name + "执行executeException失败！");
            }
        } finally {
            if (this.dispatchTaskExecuteEvent(TaskExecuteEvent.EXECUTE_END, t)) {
                logger.debug(display_name + "执行executeEnd成功！");
            } else {
                logger.warn(display_name + "执行executeEnd失败！");
            }
        }
        return false;
    }

    /**
     * 派发TaskExecuteEvent事件
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    private boolean dispatchTaskExecuteEvent(int type, Object data) throws Exception {
        TaskExecuteEvent event = new TaskExecuteEvent(this);
        event.setType(type);
        event.setData(data);
        return this.dispatchEvent(event);
    }

    /**
     * 注入支持者（兼容1.0.2版本SupportedTask）
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param taskSupporter
     * @throws Exception
     */
    public void setTaskSupporter(TaskSupporter<T> taskSupporter) throws Exception {
        if (taskSupporter != null) {
            this.registerListener(taskSupporter);
        }
    }

}
