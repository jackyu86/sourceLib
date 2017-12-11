package com.jd.scrt.common.utils;

import com.jd.scrt.common.util.Exceptions;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期相关工具
 * @author wudi10
 * 对日期进行处理
 * @since  2015-10-23
 */

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /*** 系统总的失效日期. */
    //public static final String DATE_FOREVER = "9999-12-31";
    /** 时间格式. */
    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /** 到小时分钟的日期格式. */
    public static final String FORMAT_DATETIME_HM = "yyyy-MM-dd HH:mm";
    /** 全时间格式. */
    public static final String FORMAT_FULLTIME = "yyyyMMddHHmmss";
    /** 日期格式. */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /** 日期格式. */
    //public static final String FORMAT_YEARMONTH = "yyyy-MM";
    /** 纯时间格式. */
    //public static final String FORMAT_TIME = "HH:mm:ss";

    /**
     * 得到给定日期的日期字符串 默认格式（yyyyMMddHHmmss）
     */
    public static String formatDate(Date date, String... pattern) {
        String formatDate;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0]);
        } else {
            formatDate = DateFormatUtils.format(date, FORMAT_FULLTIME);
        }
        return formatDate;
    }

    /**
     * 得到[当前时间]的日期时间字符串 格式（yyyyMMddHHmmss）
     */
    public static String currentDateTime() {
        return formatDate(new Date());
    }


    /**
     * 日期型字符串转化为日期 格式
     * { "yyyyMMddHHmmss", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss",
     * "yyyy-MM-dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(),
                    FORMAT_FULLTIME, FORMAT_DATE, FORMAT_DATETIME, FORMAT_DATETIME_HM);
        } catch (ParseException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 获取两个日期之间的天数
     * 注:使用该方法之前可以调用<code>getZeroDate</code>方法获取0点时间
     * @param before 前
     * @param after 后
     * @return 天数
     */
    public static double getDaysBetweenTwoDays(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }


    /**
     * 根据参数日期，获取日期当天0点
     * @param date 想要转换的日期
     */
    public static Date getZeroDate(Date date) {
        if (null == date)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }

    /** 根据参数日期，获取前一日0点 */
    public static Date getYesterdayZeroDate(Date currDate) {
        if (null == currDate)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        cal.add(Calendar.DATE, -1);
        return getZeroDate(cal.getTime());
    }

    /**
     * 根据参数日期，获取范围天数的当前时间
     * @param date 参数日期
     * @param days 天数，正数为向后计算，负数为向前计算<br>
     *             举个栗子：<br>
     *             Date date = new Date();<br>
     *             明天当前时间:<br>
     *             Date tomorrow = DateUtils.getSpecDaysCurrDate(date, 1);
     *             前天当前时间:<br>
     *             Date dayBeforeYesterday = DateUtils.getSpecDaysCurrDate(date, -2);
     */
    public static Date getSpecDaysCurrDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static void main(String[] args) {
        Date begin = parseDate("2015-02-01 00:11:11");

        Date end = parseDate("2015-03-03 22:00:12");

        System.out.println(begin);
        System.out.println(end);

        double d1 = getDaysBetweenTwoDays(begin, end);

        System.out.println(d1);

        begin = parseDate("2016-02-01 23:11:11");

        end = parseDate("2017-02-01 00:00:12");


        System.out.println(getDaysBetweenTwoDays(getZeroDate(begin), getZeroDate(end)));


    }


}
