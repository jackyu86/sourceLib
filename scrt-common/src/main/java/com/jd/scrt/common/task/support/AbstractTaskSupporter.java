package com.jd.scrt.common.task.support;

import com.jd.scrt.common.task.event.TaskEventListenerAdapter;
import com.jd.scrt.common.task.event.TaskExecuteEvent;
import com.jd.scrt.common.task.event.TaskFilterEvent;
import com.jd.scrt.common.task.event.TaskSelectEvent;

import java.util.List;
import java.util.Map;


/**
 * 任务获取及执行的全能支持者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public abstract class AbstractTaskSupporter<T> extends TaskEventListenerAdapter implements TaskSupporter<T> {

    /**
     * 处理任务获取事件
     *
     * @param event
     * @return
     * @throws Exception
     */
    @Override
    public boolean processEvent(TaskSelectEvent event) throws Exception {
        Map<String, Object> all_param = event.getInvokeArgs1();
        List<T> list = this.selectTask(all_param);
        event.setInvokeReturn(list);
        return true;
    }

    /**
     * 处理任务过滤事件
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean processEvent(TaskFilterEvent event) throws Exception {
        List<T> list = (List<T>) event.getInvokeArgs1();
        Map<String, Object> param = event.getInvokeArgs2();
        List<T> result = this.filterTask(list, param);
        event.setInvokeReturn(result);
        return true;
    }

    /**
     * 任务执行‘开始’时
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean onExecuteBegin(TaskExecuteEvent event) throws Exception {
        return this.executeBegin((T) event.getData());
    }

    /**
     * 任务执行‘成功’时
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean onExecuteSucceed(TaskExecuteEvent event) throws Exception {
        return this.executeSucceed((T) event.getData());
    }

    /**
     * 任务执行‘成功’时异常
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean onExecuteSucceedException(TaskExecuteEvent event) throws Exception {
        return this.executeSucceedException((T) event.getData());
    }

    /**
     * 任务执行‘失败’时
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean onExecuteFailed(TaskExecuteEvent event) throws Exception {
        return this.executeFailed((T) event.getData());
    }

    /**
     * 任务执行‘失败’时异常
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean onExecuteFailedException(TaskExecuteEvent event) throws Exception {
        return this.executeFailedException((T) event.getData());
    }

    /**
     * 任务执行‘异常’时
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean onExecuteException(TaskExecuteEvent event) throws Exception {
        return this.executeException((T) event.getData());
    }

    /**
     * 任务执行‘结束’时
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param event
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean onExecuteEnd(TaskExecuteEvent event) throws Exception {
        return this.executeEnd((T) event.getData());
    }

    @Override
    public List<T> selectTask(Map<String, Object> param) throws Exception {
        return null;
    }

    @Override
    public List<T> filterTask(List<T> list, Map<String, Object> param) throws Exception {
        return list;
    }

    @Override
    public boolean executeBegin(T t) throws Exception {
        return true;
    }

    @Override
    public boolean executeSucceed(T t) throws Exception {
        return true;
    }

    @Override
    public boolean executeSucceedException(T t) throws Exception {
        return true;
    }

    @Override
    public boolean executeFailed(T t) throws Exception {
        return true;
    }

    @Override
    public boolean executeFailedException(T t) throws Exception {
        return true;
    }

    @Override
    public boolean executeException(T t) throws Exception {
        return true;
    }

    @Override
    public boolean executeEnd(T t) throws Exception {
        return true;
    }

}
