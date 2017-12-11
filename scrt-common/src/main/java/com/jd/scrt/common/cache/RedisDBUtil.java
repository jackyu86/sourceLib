/*
 * 
 */
package com.jd.scrt.common.cache;

import com.jd.jim.cli.Cluster;
import com.jd.scrt.common.utils.GsonUtil;
import com.jd.scrt.common.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Jimdb缓存
 * Created by wangjunlei on 2016-01-24 17:19:50.
 */
@Repository
public class RedisDBUtil {

    /** The log. */
    private Logger log = Logger.getLogger(RedisDBUtil.class);

    /** The redis client. */
    @Resource(name = "jimClient")
    private Cluster jimClient;

    /**
     * 设置对象.
     * @param key    the key
     * @param object the object
     * @throws RuntimeException the exception
     */
    public void setObject(String key, Object object) throws RuntimeException {
        try {
            String json = GsonUtil.toJson(object);
            jimClient.set(key, json);
        } catch (Exception e) {
            log.error("保存REDIS对象出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Gets the object.
     * @param key  the key
     * @param type the type
     * @return the object
     * @throws RuntimeException the RuntimeException
     */
    public <T> T getObject(String key, Class<T> type) throws RuntimeException {
        String objectJson = this.get(key);
        if (StringUtils.isBlank(objectJson)) {
            return null;
        }
        return GsonUtil.fromJson(objectJson, type);
    }

    /**
     * Get String
     * @param key the key
     * @return the object json
     * @throws RuntimeException the exception
     */
    public String get(String key) throws RuntimeException {
        try {
            return jimClient.get(key);
        } catch (Exception e) {
            log.error("取出REDIS对象出错", e);
            throw new RuntimeException("取出REDIS对象出错");
        }
    }

    /**
     * Gets the object json.
     * @param key the key
     * @return the object json
     * @throws RuntimeException the exception
     */
    public String getObjectJson(String key) throws RuntimeException {
        try {
            if (jimClient.exists(key)) {
                return jimClient.get(key);
            }
            return null;
        } catch (Exception e) {
            log.error("取出REDIS对象出错", e);
            throw new RuntimeException("取出REDIS对象出错");
        }
    }

    /**
     * Checks if is exists.
     * @param key the key
     * @return true, if is exists
     */
    public boolean isExists(String key) {
        try {
            return jimClient.exists(key);
        } catch (Exception e) {
            log.error("取出REDIS对象出错", e);
        }
        return false;
    }

    /**
     * Sets the object by expire.
     * @param key    the key
     * @param object the object
     * @param min    the min
     */
    public void setObject(String key, Object object, int min)
            throws RuntimeException {
        long seconds = min * 60L;
        try {
            String json = GsonUtil.toJson(object);
            log.debug("*****转换对象为 Json ******" + json);
            jimClient.setEx(key, json, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("保存REDIS对象出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    public void setStr(String key, String object, int min)
            throws RuntimeException {
        long seconds = min * 60L;
        try {
            jimClient.setEx(key, object, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("保存REDIS对象出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Increase object.
     * @param key  the key
     * @param days the days
     * @return the long
     * @throws RuntimeException the exception
     */
    public long incrObject(String key, Integer days) throws RuntimeException {
        try {
            int seconds = days * 24 * 60 * 60;
            jimClient.expire(key, seconds, TimeUnit.SECONDS);
            return jimClient.incr(key);
        } catch (Exception e) {
            log.error("increase Object error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }


    /**
     * Delete key.
     * @param key the key
     * @throws RuntimeException the RuntimeException
     */
    public Long del(String key) throws RuntimeException {
        try {
            return jimClient.del(key);
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Delete by key.
     * @param key the key
     * @throws RuntimeException the RuntimeException
     */
    public void deleteByKey(String key) throws RuntimeException {

        try {
            if (this.isExists(key)) {
                jimClient.del(key);
            }
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * 将元素加入队列.
     * @param key    the key
     * @param object the object
     * @return the long
     * @throws RuntimeException the RuntimeException
     */
    public Long addToQueue(String key, Object object) throws RuntimeException {
        try {
            String json = GsonUtil.toJson(object);
            jimClient.lPush(key, json);// 往尾部添加
            return jimClient.lLen(key);
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * 将元素加入指定长度队列.
     * @param key    the key
     * @param object the object
     * @param length the length
     * @throws RuntimeException the RuntimeException
     */
    public void addToQueue(String key, Object object, int length)
            throws RuntimeException {

        try {
            String json = GsonUtil.toJson(object);
            jimClient.lPush(key, json);// 往尾部添加
            jimClient.lTrim(key, 0, length - 1);
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * 获取指定队列.
     * @param <T>  the generic type
     * @param key  the key
     * @param type the type
     * @return the from queue
     * @throws RuntimeException the RuntimeException
     */
    public <T> List<T> getFromQueue(String key, Class<T> type) throws RuntimeException {
        try {
            List<T> list = new ArrayList<T>();
            Long size = jimClient.lLen(key);
            List<String> bis = jimClient.lRange(key, 0, size.intValue() - 1);
            for (String bi : bis) {
                T obj = GsonUtil.fromJson(bi, type);
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * 获取指定长度队列.
     * @param <T>    the generic type
     * @param key    the key
     * @param length the length
     * @param type   the type
     * @return the from queue
     * @throws RuntimeException the RuntimeException
     */
    public <T> List<T> getFromQueue(String key, int length, Class<T> type)
            throws RuntimeException {
        try {
            List<T> list = new ArrayList<T>();
            List<String> bis = jimClient.lRange(key, 0, length - 1);
            for (String bi : bis) {
                T obj = GsonUtil.fromJson(bi, type);
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }

    }

    /**
     * 随机获取队列中一元素.
     * @param key  the key
     * @param type the type
     * @return the from queue random
     * @throws RuntimeException the RuntimeException
     */
    public Object getFromQueueRandom(String key, Class<?> type)
            throws RuntimeException {
        try {
            Long size = jimClient.lLen(key);
            Random r = new Random();
            int index = r.nextInt(size.intValue());
            String bi = jimClient.lIndex(key, index);
            return GsonUtil.fromJson(bi, type);
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Gets the queen size.
     * @param key the key
     * @return the queen size
     * @throws RuntimeException the RuntimeException
     */
    public Long getQueenSize(String key) throws RuntimeException {
        try {
            return jimClient.lLen(key);
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * 将元素加入队列,并且设置过期时间.
     * @param key    the key
     * @param object the object
     * @param min    the min
     * @return the long
     * @throws RuntimeException the RuntimeException
     */
    public Long addToQueueExpire(String key, Object object, int min)
            throws RuntimeException {
        try {
            String json = GsonUtil.toJson(object);
            jimClient.rPush(key, json);// 往尾部添加
            Long len = jimClient.lLen(key);
            int seconds = min * 60;
            jimClient.expire(key, seconds, TimeUnit.SECONDS);
            return len;
        } catch (Exception e) {
            log.error("删除REDIS KEY出错", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Hget all.
     * @param key the key
     * @return the map
     * @throws RuntimeException the RuntimeException
     */
    public Map<String, String> hgetAll(String key) throws RuntimeException {
        try {
            return jimClient.hGetAll(key);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Hkeys.
     * @param key the key
     * @return the sets the
     * @throws RuntimeException the RuntimeException
     */
    public Set<String> hkeys(String key) throws RuntimeException {
        try {
            return jimClient.hKeys(key);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Del hKey.
     * @param key   the key
     * @param field the field
     * @throws RuntimeException the RuntimeException
     */
    public void delHkey(String key, String field) throws RuntimeException {
        try {
            jimClient.hDel(key, field);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Hset.
     * @param key   the key
     * @param field the field
     * @param obj   the obj
     * @throws RuntimeException the RuntimeException
     */
    public void hset(String key, String field, Object obj) throws RuntimeException {
        try {
            String json = GsonUtil.toJson(obj);
            jimClient.hSet(key, field, json);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    public void hsetByExpire(String key, String field, Object obj, Integer min) throws RuntimeException {
        try {
            boolean hasExists = jimClient.exists(key);

            String json = GsonUtil.toJson(obj);
            jimClient.hSet(key, field, json);

            if (!hasExists) { //原来的Key不存在，就设置一下过期时间
                int seconds = min * 60;
                jimClient.expire(key, seconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Hget.
     * @param key   the key
     * @param field the field
     * @param type  the type
     * @return the object
     * @throws RuntimeException the RuntimeException
     */
    public Object hget(String key, String field, Class<?> type)
            throws RuntimeException {

        try {
            if (jimClient.hExists(key, field)) {
                String json = jimClient.hGet(key, field);
                return GsonUtil.fromJson(json, type);
            }
            return null;
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * HGet.获取原生字符串
     * @param key   the key
     * @param field the field
     * @return the object
     * @throws RuntimeException the RuntimeException
     */
    public String hgetString(String key, String field)
            throws RuntimeException {

        try {
            if (jimClient.hExists(key, field)) {
                return jimClient.hGet(key, field);
            }
            return null;
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Hincre.
     * @param key   the key
     * @param field the field
     * @param value the value
     * @param days  the days
     * @return the object
     * @throws RuntimeException the RuntimeException
     */
    public Object hincre(String key, String field, Integer value, Integer days)
            throws RuntimeException {
        try {
            int seconds = days * 24 * 60 * 60;
            jimClient.hIncrBy(key, field, value);
            jimClient.expire(key, seconds, TimeUnit.SECONDS);
            return null;
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Hexist.
     * @param key   the key
     * @param field the field
     * @return the boolean
     * @throws RuntimeException the RuntimeException
     */
    public Boolean hexist(String key, String field) throws RuntimeException {
        try {
            return jimClient.hExists(key, field);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Desc.
     * @param key the key
     * @return the long
     * @throws RuntimeException the RuntimeException
     */
    public Long desc(String key) throws RuntimeException {
        try {
            return jimClient.decr(key);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Adds the to r list.
     * @param key the key
     * @param obj the obj
     * @throws RuntimeException the RuntimeException
     */
    public void addToRList(String key, Object obj) throws RuntimeException {
        try {
            String json = GsonUtil.toJson(obj);
            jimClient.rPush(key, json);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Llen.
     * @param key the key
     * @return the long
     * @throws RuntimeException the RuntimeException
     */
    public long llen(String key) throws RuntimeException {
        try {
            return jimClient.lLen(key);
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * Gets the all list.
     * @param key the key
     * @return the all list
     * @throws RuntimeException the RuntimeException
     */
    public List<String> getAllList(String key) throws RuntimeException {
        try {
            List<String> list = jimClient.lRange(key, 0, -1);
            if (list != null && list.size() > 0) {
                jimClient.lTrim(key, list.size(), -1);
            }
            return list;
        } catch (Exception e) {
            log.error("getVendorAdvertiseFromCache error!", e);
            throw new RuntimeException("保存REDIS对象出错");
        }
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * @return 设置成功，返回 1 ;设置失败，返回 0
     */
    public Boolean setnx(String key, String value) {
        return jimClient.setNX(key, value);
    }

    /**
     * 设置缓存时间（分钟）
     * @param key key
     * @param min min
     * @return Boolean
     */
    public Boolean expire(String key, Integer min) {
        return jimClient.expire(key, min * 60, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存时间(秒)
     * @param key key
     * @param sec sec
     * @return Boolean
     */
    public Boolean expireBySecends(String key, Integer sec) {
        return jimClient.expire(key, sec, TimeUnit.SECONDS);
    }
}
