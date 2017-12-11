package com.jd.scrt.common.utils;

import java.util.Properties;

/**
 * Created by wangjunlei on 15/12/15.
 */
public class PomConfigUtil {

    public static Properties pro = null;

    static {
        pro = PropertiesUtils.readClazzPath("/properties/inpom.properties");
    }

    public static String getValue(String key){
        if(pro ==null) return null;

        return pro.getProperty(key);
    }

}
