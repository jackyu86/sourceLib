package com.jd.scrt.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * 消息文本格式器工具类
 * <p/>
 * Created by wangjunlei on 2016-01-24 17:19:50.
 *
 * @since 1.0.6
 */
public class MessageFormater {

    private static final Logger logger = Logger.getLogger(MessageFormater.class);

    private static final String MARKER_BEGIN = "{";
    private static final String MARKER_END = "}";

    private Pattern messagePattern = Pattern.compile("\\{([^\\}]+)\\}");

    /**
     * 静态成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static MessageFormater instance = new MessageFormater();
    }

    private MessageFormater() {
        logger.info("MessageFormater init...");
    }

    /**
     * 获取MessageFormater实例
     *
     * @return
     */
    public static MessageFormater getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 对消息模板进行格式化
     * <p>
     * String msg =
     * "erp.BasicWS.sayHello-{0}-{1.name}-{1.age}-{1.shoe.size}-{2.key1}-{2.key2.colour}"
     * ;<br>
     * Object[] args = {"中国",person,map};<br>
     * return: "erp.BasicWS.sayHello-中国-jason-23-43-value1-red"<br>
     * </p>
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param message
     * @param args
     * @return
     * @throws Exception
     */
    public String format(String message, Object... args) throws Exception {
        if (message == null || message.length() < 1 || args == null || args.length < 1) {
            logger.warn("format: parameter is empty, return message!");
            return message;
        }
        List<String> markers = this.matche(message);
        if (markers != null && markers.size() > 0) {
            Map<String, Object> params = this.extractParams(markers, args);
            return this.replaceMarkers(message, markers, params);
        }
        return message;
    }

    /**
     * 匹配占位符表达式
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param message
     * @return
     * @throws Exception
     */
    protected List<String> matche(String message) throws Exception {
        if (message == null || message.length() < 1) {
            logger.warn("matche: parameter is empty, return null!");
            return null;
        }
        List<String> list = new ArrayList<String>();
        Matcher m = this.getMessagePattern().matcher(message);
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }

    /**
     * 抽取占位符所用的参数
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param markes
     * @param args
     * @return
     * @throws Exception
     */
    protected Map<String, Object> extractParams(List<String> markes, Object... args) throws Exception {
        if (markes == null || args == null) {
            logger.warn("extractParams: parameter is empty, return null!");
            return null;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        for (String marker : markes) {
            params.put(marker, this.extractParam(marker, args));
        }
        return params;
    }

    /**
     * 抽取占位符所用的参数
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param marker
     * @param args
     * @return
     * @throws Exception
     */
    protected Object extractParam(String marker, Object... args) throws Exception {
        if (marker == null || marker.length() == 0 || args == null) {
            logger.warn("extractParam: parameter is empty, return marker!");
            return marker;
        }
        if (marker.indexOf(".") < 0) {// 没有"."符号，不需要读取对象属性
            return args[Integer.parseInt(marker)];
        }
        String[] mk_array = marker.split("\\.", 2);// "."为正则特殊字符，需要转义(只分隔（2-1）次)
        int arg_index = Integer.parseInt(mk_array[0]);
        Object arg = args[arg_index];
        String prop = mk_array[1];
        return BeanUtils.getProperty(arg, prop);
    }

    /**
     * 参数占位符替换为参数值
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param message
     * @param markers
     * @param params
     * @return
     * @throws Exception
     */
    protected String replaceMarkers(String message, List<String> markers, Map<String, Object> params) throws Exception {
        if (message == null || markers == null || params == null) {
            logger.warn("replaceMarkers: parameter is empty, return message!");
            return message;
        }
        for (String marker : markers) {
            message = message.replace(this.getFullMarker(marker), String.valueOf(params.get(marker)));
        }
        return message;
    }

    /**
     * 获取完整的参数占位符
     * <p/>
     * Created by wangjunlei on 2016-01-24 17:19:50.
     *
     * @param marker
     * @return
     */
    protected String getFullMarker(String marker) {
        return MARKER_BEGIN + marker + MARKER_END;
    }

    protected Pattern getMessagePattern() {
        return messagePattern;
    }

}
