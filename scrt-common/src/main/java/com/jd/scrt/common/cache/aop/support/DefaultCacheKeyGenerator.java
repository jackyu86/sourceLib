package com.jd.scrt.common.cache.aop.support;

import com.jd.scrt.common.util.MessageFormater;

import java.lang.reflect.Method;


/**
 * 默认缓存KEY生成器
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class DefaultCacheKeyGenerator extends AbstractCacheKeyGenerator {

    public static final String AT_ARGS = "@args";

    /**
     * 获取cache key，支持两种自定义占位符：‘{0}’ 和 ‘@args0’。
     * 如果CacheMethod中key为空，则返回默认key:[全类名]+[.]+[方法名]+[-args1]+...
     * <p/>
     * eg.
     * <p/>
     * String msg = "etms.qc.basicMajor-{0,date} {0,time}-{1}-{2}";
     * Object[] args = {new Date(),"jason",new Integer(27)};
     * <p/>
     * return:etms.qc.basicMajor-2013-12-5 11:58:18-jason-27
     */
    @Override
    public String generate(String key, Object target, Method method, Object[] args) throws Exception {
        if (key == null || key.length() == 0) {
            return this.generateDefaultCacheKey(target, method, args);
        }
        return this.generateCacheKey(key, args);
    }

    /**
     * 生成缓存KEY
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param key
     * @param args
     * @return
     * @throws Exception
     */
    protected String generateCacheKey(String key, Object[] args) throws Exception {
        if (key == null || key.length() < 1 || args == null || args.length < 1) {
            return key;
        }
        key = MessageFormater.getInstance().format(key, args);

        //兼容旧版本‘@args’占位符方式
        if (key != null && key.indexOf(AT_ARGS) >= 0) {
            //倒序替换，解决‘@args11’会被'@args1'替换的问题
            for (int i = args.length - 1; i >= 0; i--) {
                key = key.replace(AT_ARGS + i, String.valueOf(args[i]));
            }
        }
        return key;
    }

}
