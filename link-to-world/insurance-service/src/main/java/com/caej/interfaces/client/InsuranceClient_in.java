package com.caej.interfaces.client;

import java.util.Map;

/**
 * 保险公司发送接口
 * @author zjy
 *
 */
public interface InsuranceClient_in {
	/**
	 * 保险公司报文发送方法(web_api方式)
	 * @param map
	 * map键值如下: "xml":"请求报文","url":"接口地址","key":"秘钥","sign":"签名"
	 */
	public String send(Map<String, Object>  map);
   
}
