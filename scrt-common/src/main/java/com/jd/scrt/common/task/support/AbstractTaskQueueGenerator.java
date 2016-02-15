package com.jd.scrt.common.task.support;

import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 抽象任务队列生成器
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public abstract class AbstractTaskQueueGenerator implements TaskQueueGenerator {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private String queueListPrefix = "queue.list.";
    private String queueSizePrefix = "queue.size.";
    private String defaultQueueKey = "default";
    private String defaultQueueVal = "0";

    private Map<String, String> properties = null;

    private Random random = new Random();


    /**
     * 根据list分配队列
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param queueList
     * @param ref
     * @return
     * @throws Exception
     */
    protected String generateQueueByList(String queueList, String ref) throws Exception {
        if (queueList == null || queueList.length() == 0) {
            logger.warn("generateQueueByList: queueList cannot be empty,return defaultQueueVal! ");
            return this.getDefaultQueueVal();
        }
        String[] ids = queueList.split(",");
        int k = this.random(ref) % ids.length;
        return String.valueOf(ids[k]);
    }

    /**
     * 根据size分配队列
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param queueSize
     * @param ref
     * @return
     * @throws Exception
     */
    protected String generateQueueBySize(String queueSize, String ref) throws Exception {
        if (queueSize == null || queueSize.length() == 0) {
            logger.warn("generateQueueBySize: queueSize cannot be empty,return defaultQueueVal! ");
            return this.getDefaultQueueVal();
        }
        int q_size = Integer.parseInt(queueSize);
        int k = this.random(ref) % q_size;
        return String.valueOf(k);
    }

    /**
     * 产生随机数
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param str
     * @return
     * @throws Exception
     */
    protected int random(String str) throws Exception {
        int k = 0;
        if (str != null && str.length() > 1) {
            k = str.hashCode();
        } else {
            k = random.nextInt(100000000);
        }
        return Math.abs(k);
    }

    /**
     * 在配置文件获取配置信息
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @param defaultValue
     * @return
     * @throws Exception
     */
    protected String getProperty(String key) throws Exception {
        return this.properties.get(key);
    }

    // ---------- getter and setter -----------//
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getQueueListPrefix() {
        return queueListPrefix;
    }

    public void setQueueListPrefix(String queueListPrefix) {
        this.queueListPrefix = queueListPrefix;
    }

    public String getQueueSizePrefix() {
        return queueSizePrefix;
    }

    public void setQueueSizePrefix(String queueSizePrefix) {
        this.queueSizePrefix = queueSizePrefix;
    }

    public String getDefaultQueueKey() {
        return defaultQueueKey;
    }

    public void setDefaultQueueKey(String defaultQueueKey) {
        this.defaultQueueKey = defaultQueueKey;
    }

    public String getDefaultQueueVal() {
        return defaultQueueVal;
    }

    public void setDefaultQueueVal(String defaultQueueVal) {
        this.defaultQueueVal = defaultQueueVal;
    }

}
