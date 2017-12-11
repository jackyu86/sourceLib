package com.jd.scrt.common.task.support;

/**
 * 任务执行支持者
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @param <T>
 * @since 1.0.2
 */
public interface TaskExecuteSupporter<T> {

    /**
     * 任务执行‘开始’时(支持处理)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean executeBegin(T t) throws Exception;

    /**
     * 任务执行‘成功’时(支持处理)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean executeSucceed(T t) throws Exception;

    /**
     * 任务执行‘成功’时(支持处理)异常
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean executeSucceedException(T t) throws Exception;

    /**
     * 任务执行‘失败’时(支持处理)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean executeFailed(T t) throws Exception;

    /**
     * 任务执行‘失败’时(支持处理)异常
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean executeFailedException(T t) throws Exception;

    /**
     * 任务执行‘异常’时(支持处理)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean executeException(T t) throws Exception;

    /**
     * 任务执行‘结束’时(支持处理)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param t
     * @return
     * @throws Exception
     */
    public boolean executeEnd(T t) throws Exception;

}
