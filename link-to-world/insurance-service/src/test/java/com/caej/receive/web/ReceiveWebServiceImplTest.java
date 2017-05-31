package com.caej.receive.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import com.caej.util.RSAUtils;

import io.sited.util.JSON;

/**
 * @author miller
 */
public class ReceiveWebServiceImplTest {

    /**
     * 宏康接口测试
     * @throws Exception
     */
    @Test
    public void add() throws Exception {
    	String url="http://localhost:8081/api/receive/1234";
		InputStream is = null;
		ByteArrayOutputStream out = null;
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet(url);
    	HttpResponse response = httpClient.execute(httpGet);
    	if(response.getStatusLine().getStatusCode()==200){
    		is = response.getEntity().getContent();
			out = new ByteArrayOutputStream();
			byte[] b = new byte[100];  
			int len;  
			while((len=is.read(b))>0) { 
				 out.write(b,0,len);
			}
			out.flush();
			System.out.println(new String(out.toByteArray()));
    	}
    }

    /**
     * 泰康接口测试
     * @throws Exception
     */
    @Test
    public void add2() throws Exception  {
		String xml="fe355d547d506890689f290209102090024821|<?xml version=\"1.0\" encoding=\"GB2312\"?><消息 功能=\"投保\" 机构编号=\"4789\" 操作员编号=\"5996\"  流水号=\"T0115000303\" 来源=\"YDCA\" ><消息内容 姓名=\"被保人\" 证件号=\"350421199201014674\" 证件类型=\"身份证\" 性别=\"男\" 航班号=\"\" 移动电话=\"\" 电子邮件=\"\" 联系电话=\"\" 险种编号=\"10102542\" 险种名称=\"高额交通意外保险\" 出生日期=\"1992-01-01\" 保单生效日=\"2017-05-16 00:00:00\" 保险期间=\"365\" 保单结束日=\"2018-05-15 24:00:00\" 投保时间=\"2017-05-15 10:29:39\" 保费=\"500\" 单证号码=\"\" 出访国家=\"\" 访问目的=\"\" 受益人姓名=\"法定\" 受益人与被保险人关系=\"\" 受益人证件类型=\"\" 受益人证件号码=\"\" 受益人性别=\"\" /></消息>";
        String urls="http://developcsm.pension.taikang.com/ec/CsmOnlineInsure/csmEntry";
		Map<String, Object> map=new HashMap<>();
        map.put("xml", xml);
        map.put("url", urls);
        map.put("method", "tk_send");
        String json=JSON.toJSON(map);
		json = RSAUtils.encryptByPublicKey(json, (RSAPublicKey)RSAUtils.getPublicKey(Base64.decode(RSAUtils.pubKey)));

    	String url="http://localhost:8081/api/receive/";
		InputStream is = null;
		ByteArrayOutputStream out = null;
    	@SuppressWarnings({ "resource", "deprecation" })
		HttpClient httpClient = new DefaultHttpClient();
    	HttpGet httpGet = new HttpGet(url+json);
    	HttpResponse response = httpClient.execute(httpGet);
    	if(response.getStatusLine().getStatusCode()==200){
    		is = response.getEntity().getContent();
			out = new ByteArrayOutputStream();
			byte[] b = new byte[100];  
			int len;  
			while((len=is.read(b))>0) { 
				 out.write(b,0,len);
			}
			out.flush();
			System.out.println(new String(out.toByteArray()));
    	}
    	System.out.println(1234);
    }
}