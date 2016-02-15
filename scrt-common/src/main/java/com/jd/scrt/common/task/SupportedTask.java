package com.jd.scrt.common.task;

import com.jd.scrt.common.task.support.DefaultTaskSupporter;
import com.jd.scrt.common.task.support.TaskSupporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 被支持的任务(兼容1.0.2版本)
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public abstract class SupportedTask<T> extends AbstractScheduledTask<T> {

    private TaskSupporter<T> taskSupporter = new DefaultTaskSupporter<T>();

    /**
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
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

        List<T> list = this.getTaskSupporter().selectTask(all_param);
        if (list == null || list.size() < 1) {
            return list;
        }
        return this.filter(list, all_param);
    }

    /**
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    @Override
    public List<T> filter(List<T> list, Map<String, Object> param) throws Exception {
        return this.getTaskSupporter().filterTask(list, param);
    }

    /**
     * 任务执行过程逻辑
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    @Override
    public boolean execute(T t) throws Exception {
        String display_name = this.getDisplayName();
        try {
            if (this.getTaskSupporter().executeBegin(t)) {
                logger.debug(display_name + "执行executeBegin成功...");
            } else {
                logger.warn(display_name + "执行executeBegin失败！");
                return false;
            }
            boolean is_success = this.handleTask(t);
            if (is_success) {
                logger.debug(display_name + "执行handleTask成功...");
                if (this.getTaskSupporter().executeSucceed(t)) {
                    logger.debug(display_name + "执行executeSucceed成功！");
                    return true;
                } else {
                    logger.warn(display_name + "执行executeSucceed失败...");
                    if (this.getTaskSupporter().executeSucceedException(t)) {
                        logger.info(display_name + "执行executeSucceedException成功！");
                    } else {
                        logger.warn(display_name + "执行executeSucceedException失败...");
                    }
                }
            } else {
                logger.info(display_name + "执行handleTask失败...");
                if (this.getTaskSupporter().executeFailed(t)) {
                    logger.info(display_name + "执行executeFailed成功！");
                } else {
                    logger.warn(display_name + "执行executeFailed失败...");
                    if (this.getTaskSupporter().executeFailedException(t)) {
                        logger.info(display_name + "执行executeFailedException成功！");
                    } else {
                        logger.warn(display_name + "执行executeFailedException失败！");
                    }
                }
            }

        } catch (Throwable e) {
            logger.error(display_name + "运行异常 :" + e.getMessage(), e);
            if (this.getTaskSupporter().executeException(t)) {
                logger.info(display_name + "执行executeException成功！");
            } else {
                logger.warn(display_name + "执行executeException失败！");
            }
        } finally {
            if (this.getTaskSupporter().executeEnd(t)) {
                logger.debug(display_name + "执行executeEnd成功！");
            } else {
                logger.warn(display_name + "执行executeEnd失败！");
            }
        }
        return false;
    }

    /*---------- getter and setter ----------*/
    public void setTaskSupporter(TaskSupporter<T> taskSupporter) throws Exception {
        this.taskSupporter = taskSupporter;
        if (taskSupporter != null) {
            this.registerListener(taskSupporter);
        }
    }

    public TaskSupporter<T> getTaskSupporter() {
        return taskSupporter;
    }
}
