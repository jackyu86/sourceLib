package com.jd.scrt.common.util;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

/**
 * Jackson Json 工具类
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class JacksonUtils {

    private static ObjectMapper defaultMapper;
    private static ObjectMapper formatedMapper;

    static {
        defaultMapper = new ObjectMapper();// 默认的ObjectMapper
        defaultMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性

        formatedMapper = new ObjectMapper();
        formatedMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        formatedMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));// 所有日期格式都统一为固定格式
        formatedMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * 将对象转化为json数据
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String toJson(Object obj) throws Exception {
        if (obj == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return defaultMapper.writeValueAsString(obj);
    }

    /**
     * json数据转化为对象(Class)
     * <p/>
     * <br>e.g.
     * <br>	  User u = JacksonUtil.parseJson(jsonValue, User.class);
     * <br>   User[] arr = JacksonUtil.parseJson(jsonValue, User[].class);
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param jsonValue
     * @param classValue
     * @return
     * @throws Exception
     */
    public static <T> T parseJson(String jsonValue, Class<T> valueType) throws Exception {
        if (jsonValue == null || valueType == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) defaultMapper.readValue(jsonValue, valueType);
    }

    /**
     * json数据转化为对象(Type)
     *
     * @param jsonValue
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String jsonValue, Type type) throws Exception {
        if (jsonValue == null || type == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) defaultMapper.readValue(jsonValue, defaultMapper.constructType(type));
    }

    /**
     * json数据转化为对象(JavaType)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param jsonValue
     * @param valueType
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String jsonValue, JavaType valueType) throws Exception {
        if (jsonValue == null || valueType == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) defaultMapper.readValue(jsonValue, valueType);
    }

    /**
     * json数据转化为对象(TypeReference)
     * <p/>
     * <br>e.g.
     * <br>	  TypeReference<List<User>> typeRef = new TypeReference<List<User>>(){};
     * <br>	  List<User> list = JacksonUtil.parseJson(jsonValue, typeRef);
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param jsonValue
     * @param valueTypeRef
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJson(String jsonValue, TypeReference<T> valueTypeRef) throws Exception {
        if (jsonValue == null || valueTypeRef == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) defaultMapper.readValue(jsonValue, valueTypeRef);
    }

    /**
     * 将对象转化为json数据(时间转换格式： "yyyy-MM-dd HH:mm:ss")
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String toJsonWithFormat(Object obj) throws Exception {
        if (obj == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return formatedMapper.writeValueAsString(obj);
    }

    /**
     * json数据转化为对象(时间转换格式： "yyyy-MM-dd HH:mm:ss")
     * <p/>
     * <br>e.g.
     * <br>	  User u = JacksonUtil.parseJsonWithFormat(jsonValue, User.class);
     * <br>   User[] arr = JacksonUtil.parseJsonWithFormat(jsonValue, User[].class);
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param jsonValue
     * @param classValue
     * @return
     * @throws Exception
     */
    public static <T> T parseJsonWithFormat(String jsonValue, Class<T> valueType) throws Exception {
        if (jsonValue == null || valueType == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) formatedMapper.readValue(jsonValue, valueType);
    }

    /**
     * json数据转化为对象(Type)
     *
     * @param jsonValue
     * @param type
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJsonWithFormat(String jsonValue, Type type) throws Exception {
        if (jsonValue == null || type == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) formatedMapper.readValue(jsonValue, formatedMapper.constructType(type));
    }

    /**
     * json数据转化为对象(JavaType)
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param jsonValue
     * @param valueType
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJsonWithFormat(String jsonValue, JavaType valueType) throws Exception {
        if (jsonValue == null || valueType == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) formatedMapper.readValue(jsonValue, valueType);
    }

    /**
     * json数据转化为对象(TypeReference)
     * <p/>
     * <br>e.g.
     * <br>	  TypeReference<List<User>> typeRef = new TypeReference<List<User>>(){};
     * <br>	  List<User> list = JacksonUtil.parseJsonWithFormat(jsonValue, typeRef);
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param jsonValue
     * @param valueTypeRef
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T parseJsonWithFormat(String jsonValue, TypeReference<T> valueTypeRef) throws Exception {
        if (jsonValue == null || valueTypeRef == null) {
            throw new IllegalArgumentException("this argument is required; it must not be null");
        }
        return (T) formatedMapper.readValue(jsonValue, valueTypeRef);
    }

}
