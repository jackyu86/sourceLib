package com.caej.interfaces.client.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsuranceClient {
	private final Logger logger = LoggerFactory.getLogger(InsuranceClient.class);

	/**
	 * 宏康保险公司报文发送方法(web_api方式)
	 * 
	 * @param map
	 *            map键值如下: "xml":"请求报文","url":"接口地址","key":"秘钥","sign":"签名"
	 */
	public String hk_send(Map<String, Object> map) {
		String content = null;
		HttpURLConnection httpURLConnection = null;
		if (map == null || map.get("url") == null || map.get("xml") == null) {
			content = "必需参数url、xml为空";
			return content;
		}
		try {
			URL url = new URL(String.valueOf(map.get("url")));
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setConnectTimeout(50000);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(map.get("xml")).length() + "");
			OutputStream os = httpURLConnection.getOutputStream();
			byte[] buf = String.valueOf(map.get("xml")).getBytes();
			os.write(buf);
			os.flush();
			os.close();
			if (httpURLConnection.getResponseCode() == 200) {
				InputStream is = httpURLConnection.getInputStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buf1 = new byte[1024];
				int len = 0;
				while ((len = is.read(buf1)) != -1) {
					bos.write(buf1, 0, len);
				}
				byte[] data1 = bos.toByteArray();
				bos.flush();
				bos.close();
				is.close();
				content = new String(data1, "GB2312");
			}

		} catch (MalformedURLException e) {
			logger.error(
					"InsuranceClientImpl-------------------------send---------MalformedURLException" + e.getMessage());
		} catch (Exception e) {
			logger.error("InsuranceClientImpl-------------------------send---------Exception" + e.getMessage());
		} finally {
			httpURLConnection.disconnect();
		}
		return content;
	}

	/**
	 * 泰康保险公司报文发送方法(web_api方式)
	 * 
	 * @param map
	 *            map键值如下: "xml":"请求报文","url":"接口地址","key":"秘钥","sign":"签名"
	 */
	public String tk_send(Map<String, Object> map) {
		HttpPost request = new HttpPost(map.get("url").toString());
		try {
			request.setEntity(new StringEntity(map.get("xml").toString(), "GB2312"));
			final HttpParams httpParams = new BasicHttpParams();
			// 默认超时30s
			HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
			HttpResponse response = new DefaultHttpClient(httpParams).execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				String strResult = EntityUtils.toString(response.getEntity());
				return strResult;
			} else {
				logger.error("InsuranceClientImpl-getcode" + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {
			logger.error("InsuranceClientImpl-----getReturnXml----Exception" + e.getMessage());
		}
		return "";
	}

}
