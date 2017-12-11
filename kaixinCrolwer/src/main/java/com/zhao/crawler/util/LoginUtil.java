package com.zhao.crawler.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class LoginUtil {

    /**
     * @param args
     */

    private Boolean isProxy;

    public LoginUtil(Boolean isProxy) {
        this.isProxy = isProxy;
    }

    // 设置只是输出错误信息*****************begin*******************
    static {
        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime",
                "true");
        System.setProperty("org.apache.commons.logging"
                + ".simplelog.log.org.apache.commons.httpclient", "error");
    }

    public void loginBbs(String bbsUrl,String userName, String userPassword) {
        HttpClient httpClient = new HttpClient();// 定义一个客户端
        System.out.println("-----------------这是模拟登陆论坛的代码---------------------");
// 链接超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(
                10000);
// 读取超时
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(18000);

        if (isProxy) {
            httpClient.getHostConfiguration().setProxy("192.168.1.1", 8080);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
                    "huanghe", "00000");
            httpClient.getState().setProxyCredentials(AuthScope.ANY, creds);
        }

        PostMethod postMethod = new PostMethod(bbsUrl+"/member.php");// 设置一个post请求
        HttpMethodParams params = postMethod.getParams();
        params.setContentCharset("UTF-8"); // 设置编码
        List nameValues = new ArrayList();// 设置参数列表

        nameValues.add(new NameValuePair("mod", "logging"));
        nameValues.add(new NameValuePair("action", "login"));
        nameValues.add(new NameValuePair("loginsubmit", "yes"));
        nameValues.add(new NameValuePair("infloat", "yes"));
        nameValues.add(new NameValuePair("lssubmit", "yes"));
        nameValues.add(new NameValuePair("fastloginfield", "username"));
        nameValues.add(new NameValuePair("username", userName));
        nameValues.add(new NameValuePair("password", userPassword));
        nameValues.add(new NameValuePair("cookietime", "2592000"));

        nameValues.add(new NameValuePair("quickforward", "yes"));
        nameValues.add(new NameValuePair("handlekey", "ls"));

        List headers = new ArrayList();
        headers.add(new Header("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
        httpClient.getHostConfiguration().getParams().setParameter(
                "http.default-headers", headers);
        postMethod.setRequestBody((NameValuePair[]) nameValues.toArray(new NameValuePair[nameValues.size()]));// 添加参数列表
// 解决cookie报错信息
        DefaultHttpParams.getDefaultParams().setParameter(
                "http.protocol.cookie-policy",
                CookiePolicy.BROWSER_COMPATIBILITY);
        try {

            httpClient.executeMethod(postMethod);
            String returnBody = postMethod.getResponseBodyAsString();

            if("".equals(returnBody)){
                System.out.println("登陆成功");
            }else{
                Pattern p1 = Pattern.compile("(.*)");
                Matcher m1 = p1.matcher(returnBody);
                if(m1.find()){
                    System.out.println("提示信息是："+m1.group(1));
                }
                System.out.println("登陆失败");
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
// TODO Auto-generated method stub
        new LoginUtil(false).loginBbs("http://www.kaixin65.com","leegine1", "790819lijun");
    }

}