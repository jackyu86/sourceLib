package com.jd.scrt.common.cache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jd.scrt.common.util.SerializeUtils;
import org.apache.log4j.Logger;


/**
 * 抽象缓存实现
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public abstract class AbstractCache implements Cache, Runnable {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private volatile ScheduledExecutorService scheduledExecutor;
    /**
     * 调度执行方法: scheduleAtFixedRate(默认),scheduleWithFixedDelay可选.
     */
    private String executorMethod = "scheduleAtFixedRate";
    /**
     * 首次执行的延迟时间(秒)
     */
    private int initialDelay = 60;
    /**
     * 连续执行之间的周期(秒)
     */
    private int period = 60;
    /**
     * 一次执行终止和下一次执行开始之间的延迟(秒)
     */
    private int delay = 60;

    public void init() throws Exception {
        logger.info("AbstractCache init...");
    }

    public void start() throws Exception {
        long t0 = System.currentTimeMillis();
        scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        if ("scheduleWithFixedDelay".equals(this.getExecutorMethod())) {
            scheduledExecutor.scheduleWithFixedDelay(this, this.getInitialDelay(), this.getDelay(), TimeUnit.SECONDS);
        } else {
            scheduledExecutor.scheduleAtFixedRate(this, this.getInitialDelay(), this.getPeriod(), TimeUnit.SECONDS);
        }
        long t1 = System.currentTimeMillis();
        logger.info("cache-start: executorMethod[" + executorMethod + "],initialDelay[" + this.getInitialDelay() + "],period[" + this.getPeriod() + "],delay[" + this.getDelay() + "] in " + (t1 - t0) + " ms.");
    }

    @Override
    public void run() {
        logger.info("cache heartbeat running...");
    }

    public void stop() throws Exception {
        long t0 = System.currentTimeMillis();
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdown();
        }
        long t1 = System.currentTimeMillis();
        logger.info("cache stop  in " + (t1 - t0) + " ms.");
    }

    public void destroy() throws Exception {
        logger.info("AbstractCache init...");
    }

    /**
     * 序列化
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param obj
     * @return
     */
    public byte[] serialize(Object obj) throws Exception {
        return SerializeUtils.serialize(obj);
    }

    /**
     * 反序列化
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param bytes
     * @return
     */
    public Object unserialize(byte[] bytes) throws Exception {
        return SerializeUtils.unserialize(bytes);
    }

    /**
     * 获取合法的超时时间（ 毫秒）
     *
     * @param timeout
     * @return
     * @throws Exception
     */
    public long getTimeout(long timeout) throws Exception {
        timeout = (timeout > Integer.MAX_VALUE) ? Integer.MAX_VALUE : timeout;
        return (timeout <= 0) ? Integer.MAX_VALUE : timeout;
    }

    /**
     * 超时时间转换（ 毫秒单位-->秒单位）
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param timeout
     * @return
     * @throws Exception
     */
    public int getTimeoutSeconds(long timeout) throws Exception {
        timeout = this.getTimeout(timeout);
        int seconds = (int) (timeout / 1000);
        return seconds;
    }

    // ---------- getter and setter -----------//

    protected String getExecutorMethod() {
        return executorMethod;
    }

    public void setExecutorMethod(String executorMethod) {
        this.executorMethod = executorMethod;
    }

    protected int getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }

    protected int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    protected int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


}
