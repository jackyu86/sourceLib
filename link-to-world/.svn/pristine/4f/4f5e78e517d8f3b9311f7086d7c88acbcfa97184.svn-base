package com.caej.insurance.receive.web;


import java.lang.reflect.Method;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caej.insurance.api.ReceiveWebService;
import com.caej.interfaces.client.impl.InsuranceClient;
import com.caej.util.RSAUtils;

import io.sited.util.JSON;


/**
 * 接收新老两个平台的接口请求
 * @author zjy
 *
 */
public class ReceiveWebServiceImpl implements ReceiveWebService{
	  private final Logger logger = LoggerFactory.getLogger(ReceiveWebServiceImpl.class);
	/**
	 * 接收新老两个平台的接口请求方法
	 * @param request
	 */
	@Override
	public Map<String,Object>  receive(String json){
		long l1=System.currentTimeMillis();
		String uuid=UUID.randomUUID().toString();
		logger.info("ReceiveWebServiceImpl====receive====encode  "+json+uuid);
		Map<String, Object> map=new HashMap<String, Object> ();
		Map<String, Object> resultMap=new HashMap<String, Object> ();
		try {
			json = RSAUtils.decryptByPrivateKey(json, (RSAPrivateKey)RSAUtils.getPrivateKey(Base64.decode(RSAUtils.priKey)));
			map=JSON.fromJSON(json, Map.class);
			String result=localCall(map);
			logger.info("ReceiveWebServiceImpl====receive====decode  "+map+uuid);
			resultMap.put("resultCode", "0");
			resultMap.put("xml", result);
		} catch (Exception e) {
			logger.error("ReceiveWebServiceImpl====receive====IOException "+e.getMessage());
			resultMap.put("resultCode", "-1");
			resultMap.put("xml", "参数异常！");
		}
		logger.info("本次请求系统执行时间为 "+(System.currentTimeMillis()-l1));
		return resultMap;
		
	}
	
	/**
	 * 反射调用
	 * @param map
	 * @return
	 */
	private String localCall(Map<String, Object> map) {
		if(!map.containsKey("method")||!map.containsKey("xml")||!map.containsKey("url")){
			return null;
		}
		Class clientClass=InsuranceClient.class;
		String result=null;
		try {
			Method m1 = clientClass.getDeclaredMethod(String.valueOf(map.get("method")),Map.class);
			result=(String) m1.invoke(clientClass.newInstance(), map);
		} catch (Exception e) {
			logger.error("ReceiveWebServiceImpl====localCall====Exception "+e.getMessage());
		} 
		return result; 
	}
	

}
