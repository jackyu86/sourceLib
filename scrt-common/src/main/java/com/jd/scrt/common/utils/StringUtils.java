package com.jd.scrt.common.utils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Yay on 2016/1/25.
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static int compareInteger(Integer p1, Integer p2){
        if (!isNull(p1, p2)){
            return p1.compareTo(p2);
        } else if (isNull(p1) && isNull(p2)){
            return 0;
        } else {
            return -1;
        }
    }

    public static int compareString(String p1, String p2){
        if (!isNull(p1, p2)){
            if (p1.equals(p2)){
                return 0;
            } else {
                return 1;
            }
        } else if (isNull(p1) && isNull(p2)){
            return 0;
        } else {
            return -1;
        }
    }

    public static int compaireBigDecimal(BigDecimal p1, BigDecimal p2){
        if (!isNull(p1, p2)){
            return p1.compareTo(p2);
        } else if (isNull(p1) && isNull(p2)){
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 判断字符串是否为空,其中包括空格，转行符
     *
     * @param str
     * @return 为空返回TRUE 不为空返回FLASE
     */
    public static boolean isNull(String str)
    {
        if (str == null || str.matches("\\s*") || "null".equalsIgnoreCase(str))
        {
            return true;
        }
        return false;
    }

    /**
     * 判断一个参数变理中所的的值是不是为空
     *
     * 如果有一个为空就返回FTRUE都不为空的时候返回FALASE
     *
     * @param str
     * @return
     */
    public static boolean isNull(String... str)
    {
        for (String value : str)
        {
            if (isNull(value))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是不是为空，为空返回true 不为空返回false
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj)
    {
        if (obj == null)
        {
            return true;
        }
        return false;
    }

    /**
     *
     * 判断可变参数是所的的值是不是为空，如果有一个为空则返因TRUE。都不为空时返回FALSE
     *
     * @param objs
     * @return
     */
    public static boolean isNull(Object... objs)
    {
        for (Object obj : objs)
        {
            if (isNull(obj))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组是不是为空。当数组为空或者大小小于1时都认为这个数组为空
     *
     * @param obj
     * @return 为空返回TRUE，否则返回FALSE
     */
    public static boolean isEmpty(Object[] obj)
    {
        if (StringUtils.isNull(obj))
        {
            return true;
        }

        if (obj.length <= 0) {

            return true;

        }
        return false;
    }

    /**
     * 判断列表是不是为空。当列表为空或者大小小于1时都认为这个列表为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection)
    {
        if (StringUtils.isNull(collection) || collection.size() <= 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 根据传入的字符串，如果传入的字符串为空则返回默认知，否则就返回原知
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public static String noNulls(String value, String defaultValue)
    {
        if (StringUtils.isNull(value))
        {
            return defaultValue;
        }
        return value;
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     * @param obj
     * @return
     * create by wangjunlei with add
     *
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

}
