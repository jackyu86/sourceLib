package com.jd.scrt.common.io.excel.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.log4j.Logger;

/**
 * Excel文件时间字段转换类
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class ExcelDateConverter extends AbstractConverter {

    private static final Logger logger = Logger.getLogger(ExcelDateConverter.class);

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 默认为标准日期时间格式

    protected String convertToString(Object value) throws Throwable {
        if (value == null) {
            return "";
        } else if (value instanceof String) {
            return (String) value;
        }
        Date date = null;
        if (value instanceof Date) {
            date = (Date) value;
        } else if (value instanceof Calendar) {
            date = ((Calendar) value).getTime();
        } else if (value instanceof Long) {
            date = new Date(((Long) value).longValue());
        }
        String result = null;
        if (date == null || dateFormat == null) {
            result = String.valueOf(value);
        } else {
            result = dateFormat.format(date);
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Object convertToType(Class type, Object value) throws Throwable {
        String str_value = String.valueOf(value).trim();
        if ("null".equals(str_value) || str_value.length() < 1) {
            return null;
        }
        Object result = null;
        try {
            return dateFormat.parse(str_value);
        } catch (Exception e) {
            logger.error("convertToType-error:", e);
            result = null;
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Class getDefaultType() {
        return Date.class;
    }

    // ---------- setter and getter ----------//

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

}
