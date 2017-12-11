package com.qsx.dubbo.util;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * Created by qiu on 17/3/7.
 */
public class JsonUtil<T> {
    public static final String CODE_SUCCESS = "0";

    private String msg;
    private String ret;//opt result:0.success
    private T data;

    public JsonUtil() {
    }

    public JsonUtil(String msg, T data, String ret) {
        this.msg = msg;
        this.data = data;
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断结果是否成功
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isOK() {
        return CODE_SUCCESS.equals(this.ret);
    }

    /**
     * 直接构造操作成功的Json实例
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> JsonUtil success(T data) {
        JsonUtil<T> json = new JsonUtil<T>();
        json.setRet(CODE_SUCCESS);
        json.setMsg("OK");
        json.setData(data);
        return json;
    }

    public static <T> JsonUtil success(T data, String msg) {
        JsonUtil<T> json = new JsonUtil<T>();
        json.setRet(CODE_SUCCESS);
        json.setMsg(msg);
        json.setData(data);
        return json;
    }



    public static <T> JsonUtil fail(String code, String msg) {
        JsonUtil<T> json = new JsonUtil<T>();
        json.setRet(code);
        json.setMsg(msg);
        return json;
    }
}
