package com.jd.scrt.common.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/*
 * Created by wangjunlei on 2016-01-24 17:19:50.
 */
public class GsonUtil {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new UtilDateSerializer())
            .registerTypeAdapter(Date.class, new UtilDateDeserializer())
            .registerTypeAdapter(DateFormat.class, new UtilDateFormatSerializer())
            .registerTypeAdapter(DateFormat.class, new UtilDateFormatDeserializer())
            .setDateFormat(DateFormat.LONG)
            .create();

    private static final Type MAP_TYPE = new TypeToken<Map<String, Object>>(){}.getType();
    private static final Type STRING_MAP_TYPE = new TypeToken<Map<String, String>>(){}.getType();

    private static ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * @param json String
     * @param For  scrt, to get the type for {@code Collection<Foo>}, you should use
     *             new TypeToken<List<String>>(){}
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDomain(String json, TypeToken<T> token) {

        return (T) gson.fromJson(json, token.getType());
    }

    public static JsonElement parseString(String json) {
        if (json != null && json.trim().length() > 0) {
            JsonParser parser = new JsonParser();
            return parser.parse(json);
        }
        return JsonNull.INSTANCE;
    }

    public static boolean containsKey(String key, String jsonStr) {
        if (key == null || key.trim().length() == 0)
            return false;
        if (jsonStr == null || jsonStr.trim().length() == 0)
            return false;

        Map<String, Object> map = fromJson(jsonStr, Map.class);
        if (map == null || map.keySet().size() == 0)
            return false;

        return map.containsKey(key);
    }

    public static String getKeyValue(String key, String jsonStr) {
        return getKeyValue(key, parseString(jsonStr));
    }

    public static String getKeyValue(String key, JsonElement element) {

        if (key != null && key.trim().length() > 0 && element != null) {

            JsonObject object = null;
            try {
                if (element.isJsonObject()) {
                    object = element.getAsJsonObject();
                    element = object.get(key);
                    if (element.isJsonPrimitive()) {
                        return element.getAsString();
                    } else {
                        return element.toString();
                    }
                }
                return element.toString();
            } catch (Exception e) {
            } finally {
                object = null;
            }
        }
        return null;
    }

    public static String removeKey(String key, JsonElement element) {

        if (key != null && key.trim().length() > 0 && element != null) {

            JsonObject object = null;
            try {
                if (element.isJsonObject()) {
                    object = element.getAsJsonObject();
                    object.remove(key);
                    return object.toString();
                }
                return element.toString();
            } catch (Exception e) {
            } finally {
                object = null;
            }
        }
        return null;
    }

    public static String toJson(Object o) {

        return gson.toJson(o);
    }

    public static String toJsonString(Object o) {

        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, TypeToken<T> token) {

        return (T) gson.fromJson(json, token.getType());
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {

        return (T) gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(Class<T> classOfT, String json) {

        return (T) gson.fromJson(json, classOfT);
    }

    /**
     * 慎用，会将数值型字段转换成Double 例如Long字段会转换成Double
     * @param json json
     * @return Map对象
     */
    @Deprecated
    public static Map<String, Object> fromJson2ObjMap(String json) {

        return gson.fromJson(json, MAP_TYPE);
    }
    public static Map<String, String> fromJson2StrMap(String json) {

        return gson.fromJson(json, STRING_MAP_TYPE);
    }

    private static class UtilDateDeserializer implements
            JsonDeserializer<Date> {

        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {

            if (null == json) {
                return null;
            }
            String value = json.getAsString();
            Date date = null;
            if ((null != value) && (!value.trim().equals(""))
                    && (!value.trim().equals("null"))) {
                value = value.trim();
                String format = "yyyy-MM-dd HH:mm:ss";
                try {
                    if (value.contains("-")) {
                        if (value.length() == 19) {
                            format = "yyyy-MM-dd HH:mm:ss";
                        } else if (value.length() == 16) {
                            format = "yyyy-MM-dd HH:mm";
                        } else if (value.length() == 13) {
                            format = "yyyy-MM-dd HH";
                        } else if (value.length() == 10) {
                            format = "yyyy-MM-dd";
                        }
                    } else if (value.contains(":")) {
                        if (value.length() == 8) {
                            format = "HH:mm:ss";
                        } else if (value.length() == 5) {
                            format = "HH:mm";
                        }
                    } else if (value.length() == 2) {
                        format = "HH";
                    } else if (value.length() == 4) {
                        format = "HHmm";
                    } else if (value.length() == 6) {
                        format = "HHmmss";
                    } else if (value.length() == 8) {
                        format = "yyyyMMdd";
                    } else if (value.length() == 10) {
                        format = "yyyyMMddHH";
                    } else if (value.length() == 12) {
                        format = "yyyyMMddHHmm";
                    } else if (value.length() == 14) {
                        format = "yyyyMMddHHmmss";
                    }
                    if (null != format) {
                        date = new SimpleDateFormat(format).parse(value);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    value = null;
                    format = null;
                }
            }
            return date;
        }
    }

    private static class UtilDateSerializer implements JsonSerializer<Date> {

        public JsonElement serialize(Date src, Type typeOfSrc,
                                     JsonSerializationContext context) {

            DateFormat format = dateFormat.get();

            return new JsonPrimitive(format.format(src));
        }
    }

    private static class UtilDateFormatSerializer implements JsonSerializer<DateFormat> {

        public JsonElement serialize(DateFormat src, Type typeOfSrc,
                                     JsonSerializationContext context) {

            return null;
        }
    }

    private static class UtilDateFormatDeserializer implements JsonDeserializer<DateFormat> {

        public DateFormat deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException {

            return null;
        }
    }

    public static void main(String[] args) {
        containsKey("dd", "");

        TypeToken<Map<String, Object>> mapTypeToken = new TypeToken<Map<String, Object>>(){};
        TypeToken<Map<String, String>> stringMapTypeToken = new TypeToken<Map<String, String>>(){};


        String jsonStr = "{'cc':'cc','dd':'12','bb':'true'}";
        Map<String, String> stringMap = fromJson2StrMap(jsonStr);
        System.out.println(stringMap);

        jsonStr = "{'cc':'cc','dd':12,'bb':true,'lMax':" + Long.MAX_VALUE
                + ",'lMin':" + Long.MIN_VALUE
                + ",'lZero':" + "0"
                + "}";
        Map<String, String> stringMap1 = fromJson2StrMap(jsonStr);
        System.out.println(stringMap1);

    }

}
