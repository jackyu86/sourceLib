package com.jd.scrt.common.task.event;

/**
 * 任务事件监听适配器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public abstract class TaskEventListenerAdapter implements TaskEventListener {

    @Override
    public boolean processEvent(TaskEvent event) throws Exception {
        return true;
    }

    /**
     * 处理任务获取事件
     *
     * @param event
     * @return
     * @throws Exception
     */
    public boolean processEvent(TaskSelectEvent event) throws Exception {
        return true;
    }

    /**
     * 处理任务过滤事件
     *
     * @param event
     * @return
     * @throws Exception
     */
    public boolean processEvent(TaskFilterEvent event) throws Exception {
        return true;
    }

    /**
     * 处理任务执行事件
     *
     * @param event
     * @return
     * @throws Exception
     */
    public boolean processEvent(TaskExecuteEvent event) throws Exception {
        if (event == null) {
            return false;
        }
        boolean b = true;

        switch (event.getType()) {
            case TaskExecuteEvent.EXECUTE_BEGIN:
                b = this.onExecuteBegin(event);
                break;
            case TaskExecuteEvent.EXECUTE_SUCCEED:
                b = this.onExecuteSucceed(event);
                break;
            case TaskExecuteEvent.EXECUTE_SUCCEED_EXCEPTION:
                b = this.onExecuteSucceedException(event);
                break;
            case TaskExecuteEvent.EXECUTE_FAILED:
                b = this.onExecuteFailed(event);
                break;
            case TaskExecuteEvent.EXECUTE_FAILED_EXCEPTION:
                b = this.onExecuteFailedException(event);
                break;
            case TaskExecuteEvent.EXECUTE_EXCEPTION:
                b = this.onExecuteException(event);
                break;
            case TaskExecuteEvent.EXECUTE_END:
                b = this.onExecuteEnd(event);
                break;

            default:
                b = this.processTaskExecuteEvent(event);// 默认处理自定义事件
                break;
        }
        return b;
    }

    /**
     * 处理自定义事件
     *
     * @param event
     * @return
     * @throws Exception
     */
    public boolean processTaskExecuteEvent(TaskExecuteEvent event) throws Exception {
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
    public boolean onExecuteBegin(TaskExecuteEvent event) throws Exception {
        return true;
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
    public boolean onExecuteSucceed(TaskExecuteEvent event) throws Exception {
        return true;
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
    public boolean onExecuteSucceedException(TaskExecuteEvent event) throws Exception {
        return true;
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
    public boolean onExecuteFailed(TaskExecuteEvent event) throws Exception {
        return true;
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
    public boolean onExecuteFailedException(TaskExecuteEvent event) throws Exception {
        return true;
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
    public boolean onExecuteException(TaskExecuteEvent event) throws Exception {
        return true;
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
    public boolean onExecuteEnd(TaskExecuteEvent event) throws Exception {
        return true;
    }

}
