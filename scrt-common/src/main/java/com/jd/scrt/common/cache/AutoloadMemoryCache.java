package com.jd.scrt.common.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 自动加载内存缓存
 * Created by wangjunlei on 2016-01-24 17:19:50.
 * 
 * @since 1.0.6
 *
 */
public class AutoloadMemoryCache extends MemoryCache {

	private Map<String,CacheLoader> cacheLoaderMap = new HashMap<String,CacheLoader>();
	
	@Override
	public void run() {
		try {
			this.autoloadCache();
		} catch (Throwable t) {
			logger.error("AutoloadMemoryCache running error:", t);
		}
	}
	
	/**
	 * 自动加载缓存
	 * @throws Exception
	 */
	protected void autoloadCache() throws Throwable {
		if(cacheLoaderMap == null || cacheLoaderMap.isEmpty()){
			logger.info("autoloadCache: cacheLoaderMap isEmpty,return!");
			return;
		}
		Entry<String, CacheLoader> entry = null;
		String key = null;
		CacheLoader loader = null;
		Iterator<Entry<String, CacheLoader>> iter = cacheLoaderMap.entrySet().iterator();
		while (iter.hasNext()) {
			entry = iter.next();
			key = entry.getKey();
			loader = entry.getValue();
			if(loader == null){
				logger.info("autoloadCache: key[" + key + "] CacheLoader is null,return!");
			}
			else{
				this.autoloadCache(key, loader);
			}
		}
	}
	
	/**
	 * 通过CacheLoader自动加载
	 * 
	 * @param key
	 * @param loader
	 * @throws Throwable
	 */
	protected void autoloadCache(String key,CacheLoader loader) throws Throwable {
		try {
			Object value = loader.load(key);
			if(value == null){
				logger.warn("autoloadCache: key[" + key + "] value is null,don't put cache!");
			}
			else{
				this.put(key, value);
				logger.info("autoloadCache: key[" + key + "],value[" + value + "]");
			}
		} catch (Throwable e) {
			logger.error("autoloadCache-error:", e);
		}
	}

	// ---------- getter and setter ----------//
	
	public Map<String, CacheLoader> getCacheLoaderMap() {
		return cacheLoaderMap;
	}

	public void setCacheLoaderMap(Map<String, CacheLoader> cacheLoaderMap) {
		this.cacheLoaderMap = cacheLoaderMap;
	}
}
