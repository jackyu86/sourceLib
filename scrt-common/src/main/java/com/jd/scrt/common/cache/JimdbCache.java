package com.jd.scrt.common.cache;


import com.jd.jim.cli.Cluster;

import java.util.concurrent.TimeUnit;

/**
 * Jimdb缓存
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.7
 */
public class JimdbCache extends AbstractCache {

    private Cluster jimdbClient;

    @Override
    public Object put(String key, Object value) throws Exception {
        return this.put(key, value, 0);
    }

    @Override
    public Object put(String key, Object value, long timeout) throws Exception {
        if (key == null) {
            return null;
        }
        int seconds = this.getTimeoutSeconds(timeout);
        byte[] b_key = key.getBytes();
        byte[] b_val = this.serialize(value);
        jimdbClient.setEx(b_key, b_val, seconds, TimeUnit.SECONDS);
        logger.info("put: key[" + key + "],timeout[" + timeout + "] over!");
        return value;
    }

    @Override
    public Object get(String key) throws Exception {
        if (key == null) {
            return null;
        }
        logger.debug("get: key[" + key + "]");
        byte[] b_key = key.getBytes();
        byte[] b_val = jimdbClient.get(b_key);

        Object value = null;
        if (b_val != null && b_val.length > 0) {
            value = this.unserialize(b_val);
        }
        return value;
    }

    @Override
    public Object remove(String key) throws Exception {
        if (key == null) {
            return null;
        }
        Object value = this.get(key);
        Long count = jimdbClient.del(key);
        logger.info("remove: key[" + key + "],count[" + count + "] over!");
        return value;
    }

    @Override
    public boolean containsKey(String key) throws Exception {
        if (key == null) {
            return false;
        }
        Boolean b = jimdbClient.exists(key);
        logger.debug("containsKey: key[" + key + "], b[" + b + "]");
        return b == null ? false : b.booleanValue();
    }


	/*---------- getter and setter ----------*/

    public Cluster getJimdbClient() {
        return jimdbClient;
    }

    public void setJimdbClient(Cluster jimdbClient) {
        this.jimdbClient = jimdbClient;
    }
}
