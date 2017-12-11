package com.jd.scrt.common.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 内存缓存
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.3
 */
public class MemoryCache extends AbstractCache {

    private volatile ConcurrentMap<String, CacheEntry> cache = new ConcurrentHashMap<String, CacheEntry>();

    public void init() throws Exception {
        super.start();
    }

    public void destroy() throws Exception {
        super.stop();
    }

    @Override
    public Object put(String key, Object value) throws Exception {
        return this.put(key, value, 0);
    }

    @Override
    public Object put(String key, Object value, long timeout) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("put: key[" + key + "],timeout[" + timeout + "]");
        }
        CacheEntry cacheEntry = this.putEntryIfAbsent(key);
        return cacheEntry.reset(key, value, this.getTimeout(timeout));
    }

    /**
     * 同步加载数据（锁定单线程加载）
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @param cacheLoader
     * @param timeout
     * @return
     * @throws Exception
     */
    public Object load(String key, CacheLoader cacheLoader, long timeout) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("put: key[" + key + "],timeout[" + timeout + "]");
        }
        CacheEntry cacheEntry = this.putEntryIfAbsent(key);
        return cacheEntry.reload(key, cacheLoader, this.getTimeout(timeout));
    }

    /**
     * 在key上赋值一个CacheEntry
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @return
     * @throws Exception
     */
    protected CacheEntry putEntryIfAbsent(String key) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("putEntryIfAbsent: key[" + key + "]");
        }
        CacheEntry cacheEntry = new CacheEntry();
        CacheEntry oldCacheEntry = cache.putIfAbsent(key, cacheEntry);
        if (null != oldCacheEntry) {// 如果原来有值
            logger.debug("putEntryIfAbsent: key[" + key + "] already exist!");
            cacheEntry = oldCacheEntry;
        }
        return cacheEntry;
    }

    @Override
    public Object get(String key) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("get: key[" + key + "]");
        }
        CacheEntry cacheEntry = cache.get(key);
        if (cacheEntry == null) {
            return null;
        } else {
            return cacheEntry.takeOut();
        }
    }

    @Override
    public Object remove(String key) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("remove: key[" + key + "]");
        }
        CacheEntry cacheEntry = cache.get(key);
        if (cacheEntry == null) {
            cacheEntry = cache.remove(key);
            return cacheEntry == null ? null : cacheEntry.takeOut();
        } else {
            return cacheEntry.remove();
        }
    }

    @Override
    public boolean containsKey(String key) throws Exception {
        return cache.containsKey(key);
    }

    /**
     * 清理已经过期的数据
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @throws Exception
     */
    protected void clearTimeoutData() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("clearTimeoutData begin...");
        }
        long t0 = System.currentTimeMillis();
        int count = 0;
        String key = null;
        boolean is_remove = false;
        CacheEntry cacheEntry = null;
        Entry<String, CacheEntry> entry = null;
        Iterator<Entry<String, CacheEntry>> iter = cache.entrySet().iterator();
        while (iter.hasNext()) {
            entry = (Entry<String, CacheEntry>) iter.next();
            key = entry.getKey();
            cacheEntry = entry.getValue();
            if (cacheEntry == null) {
                logger.debug("clearTimeoutData: key[" + key + "],cacheEntry is null,cache.remove(key)");
                cache.remove(key);
                count++;
            } else {
                is_remove = cacheEntry.removeIfTimeout();
                if (is_remove) {
                    count++;
                    logger.info("clearTimeoutData: key[" + key + "] remove true!");
                } else {
                    logger.debug("clearTimeoutData: key[" + key + "] remove false!");
                }
            }
        }
        logger.info("clearTimeoutData: count[" + count + "],running in " + (System.currentTimeMillis() - t0) + " ms.");
    }

    @Override
    public void run() {
        try {
            this.clearTimeoutData();
        } catch (Throwable t) {
            logger.error("MemoryCache running error:", t);
        }
    }

    /**
     * 缓存数据对象
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     */
    class CacheEntry {

        private String key;
        private Object value;
        private long expiredTime;

        /**
         * 重置缓存数据
         * <p/>
         * Created by wangjunlei on 2016-01-24 17:19:50.
         *
         * @param value
         * @param timeout
         * @throws Exception
         */
        public Object reset(String key, Object value, long timeout) throws Exception {
            synchronized (this) {
                this.key = key;
                this.value = value;
                this.expiredTime = System.currentTimeMillis() + timeout;
                return this.value;
            }
        }

        /**
         * 重新加载数据
         * <p/>
         * Created by wangjunlei on 2016-01-24 17:19:50.
         *
         * @param key
         * @param value
         * @param timeout
         * @throws Exception
         */
        public Object reload(String key, CacheLoader cacheLoader, long timeout) throws Throwable {
            synchronized (this) {
                Object value = cacheLoader.load(key);
                this.key = key;
                this.value = value;
                this.expiredTime = System.currentTimeMillis() + timeout;
                return this.value;
            }
        }

        /**
         * 取出缓存数据
         * <p/>
         * Created by wangjunlei on 2016-01-24 17:19:50.
         *
         * @return
         * @throws Exception
         */
        public Object takeOut() throws Exception {
            synchronized (this) {
                long t = this.expiredTime - System.currentTimeMillis();
                if (t < 0) {// 如果已经超时
                    this.value = null;
                }
                return this.value;
            }
        }

        /**
         * 自杀式移除
         * <p/>
         * Created by wangjunlei on 2016-01-24 17:19:50.
         *
         * @return
         * @throws Exception
         */
        public Object remove() throws Exception {
            synchronized (this) {
                cache.remove(this.key);
                return this.value;
            }
        }

        /**
         * 如果已经超时，则移除键值映射关系
         * <p/>
         * Created by wangjunlei on 2016-01-24 17:19:50.
         *
         * @return
         * @throws Exception
         */
        public boolean removeIfTimeout() throws Exception {
            synchronized (this) {
                long t = this.expiredTime - System.currentTimeMillis();
                if (t < 0) {// 如果已经超时
                    cache.remove(this.key);
                    return true;
                }
                return false;
            }
        }
    }
}
