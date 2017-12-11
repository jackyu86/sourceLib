package com.jd.scrt.common.utils;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaosiming on 2016/1/28.
 */
public class HttpClientUtils {

    public static final String URL_PARAM_CHARSET_UTF8 = "UTF-8"; // 定义编码格式 UTF-8
    public static final String URL_PARAM_CHARSET_GBK = "GBK"; // 定义编码格式 GBK
    private static final String EMPTY = "";

    private static MultiThreadedHttpConnectionManager connectionManager = null;
    private static int connectionTimeOut = 2000;
    private static int socketTimeOut = 4000;
    private static int maxConnectionPerHost = 20;
    private static int maxTotalConnections = 20;
    private static HttpClient client;

    static {
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        client = new HttpClient(connectionManager);
    }

    /**
     * POST方式提交数据
     *
     * @param url     待请求的URL
     * @param params  要提交的数据
     * @param charset 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String sendPost(String url, Map<String, String> params, String charset) throws Exception {
        url = url.replace("https", "http");

        String response = EMPTY;
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        // 将表单的值放入postMethod中
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            String value = params.get(key);
            postMethod.addParameter(key, value);
        }
        try {
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
            } else {
                throw new RuntimeException("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            throw new HttpException("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            throw new IOException("发生网络异常", e);
        } catch (Exception e) {
            throw new Exception(String.format("POST方式提交数据异常（%s）", e.getMessage()), e);
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return response;
    }

    /**
     * POST方式提交数据
     *
     * @param url     待请求的URL
     * @param body    要提交的数据
     * @param charset 编码
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String sendPost2Body(String url, String body, String charset) throws Exception {
        url = url.replace("https", "http");

        StringBuffer response = new StringBuffer(EMPTY);
        InputStream inputStream = null;
        BufferedReader reader = null;
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        // 将要提交的数据放入postMethod中
        postMethod.setRequestBody(body);
        try {
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                inputStream = postMethod.getResponseBodyAsStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, postMethod.getResponseCharSet()));
                String inputLine = null;
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
            } else {
                throw new Exception("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            throw new HttpException("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            throw new IOException("发生网络异常", e);
        } catch (Exception e) {
            throw new Exception(String.format("POST方式提交数据异常（%s）", e.getMessage()), e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return response.toString();
    }

    /**
     * POST方式提交数据
     *
     * @param url     待请求的URL
     * @param body    要提交的数据
     * @param charset 编码
     * @param contentType MIME类型
     * @return 响应结果
     * @throws IOException IO异常
     */
    public static String sendPost2Body(String url, String body, String charset, String contentType) throws Exception {
        url = url.replace("https", "http");

        StringBuffer response = new StringBuffer(EMPTY);
        InputStream inputStream = null;
        BufferedReader reader = null;
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", contentType + ";charset=" + charset);
        // 将要提交的数据放入postMethod中
        postMethod.setRequestBody(body);
        try {
            // 执行postMethod
            int statusCode = client.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                inputStream = postMethod.getResponseBodyAsStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, postMethod.getResponseCharSet()));
                String inputLine = null;
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
            } else {
                throw new Exception("响应状态码 = " + postMethod.getStatusCode());
            }
        } catch (HttpException e) {
            throw new HttpException("发生致命的异常，可能是协议不对或者返回的内容有问题", e);
        } catch (IOException e) {
            throw new IOException("发生网络异常", e);
        } catch (Exception e) {
            throw new Exception(String.format("POST方式提交数据异常（%s）", e.getMessage()), e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return response.toString();
    }

}
