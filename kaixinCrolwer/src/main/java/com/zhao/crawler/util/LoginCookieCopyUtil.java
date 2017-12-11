package com.zhao.crawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;

public class LoginCookieCopyUtil {

    public  void test() throws HttpException, IOException{
//        String url = "http://www.kaixin65.com/member.php?mod=logging&action=login&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login";
//        HttpClient httpClient = new HttpClient();
//        DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
//        GetMethod getMethod = new GetMethod(url);
//        getMethod.setRequestHeader("Host", "www.kaixin65.com");
//        getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36");
//        getMethod.setRequestHeader("Accept", "*/*");
//        getMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
//        getMethod.setRequestHeader("Connection", "keep-alive");
////		getMethod.setRequestHeader("Cookie", "Hm_lvt_6e06bb5a029d6c5473951d1079638828=1328777184942; Hm_lvt_e64244e1e591d0337e17a12b714c0996=1328777186856; WT_FPC=id=183.16.35.230-1530895312.30204303:lv=1328174810886:ss=1328174810886; ASP.NET_SessionId=gj25p555exiqjd45kdcqoq55; BIGipServernxt-wz=369797312.20480.0000; Hm_lpvt_6e06bb5a029d6c5473951d1079638828=1328777184942; Hm_lpvt_e64244e1e591d0337e17a12b714c0996=1328777186856; .12582portals=4CF97704261E34DBE12913CBA18211005E960282A771D94FF3709BAFC99610A7397BE8293ADB2E876A0A7380AC4A158016419745F27511D6E79F82A408D009519D6DDFE18A578E5CFC5F48025C75B33B6EBD43953A7DB05AEBAAB856E0AA29112818B4910350AADACD2012F5DE56297B1F6622ED7F4959D31E19C474E48F7773D7966437");
//        getMethod.setRequestHeader("Cache-Control", "max-age=0");
//        int code = httpClient.executeMethod(getMethod);
//        Header header = getMethod.getResponseHeader("Set-cookie");
//        System.out.println(header.getValue());
//        String headerCookie = header.getValue();
//        String SessionId = headerCookie.substring(headerCookie.indexOf("NET_SessionId=") + "NET_SessionId=".length(), headerCookie.indexOf(";"));
//        System.out.println(SessionId);
//        String BIGipServernxt = headerCookie.substring(headerCookie.indexOf("BIGipServernxt-wz=") + "BIGipServernxt-wz=".length(), headerCookie.length());
//        BIGipServernxt = BIGipServernxt.substring(0, BIGipServernxt.indexOf(";"));
//        StringBuffer sb = new StringBuffer();
//        sb.append("Hm_lvt_6e06bb5a029d6c5473951d1079638828=1328778307106; ");
//        sb.append("Hm_lvt_e64244e1e591d0337e17a12b714c0996=1328778308090; ");
//        sb.append("WT_FPC=id=183.16.35.230-1530895312.30204303:lv=1328174810886:ss=1328174810886; ");
//        sb.append("ASP.NET_SessionId=").append(SessionId).append("; ");
//        sb.append("BIGipServernxt-wz=").append(BIGipServernxt).append("; ");
//        sb.append("Hm_lpvt_6e06bb5a029d6c5473951d1079638828=1328778307106; ");
//        sb.append("Hm_lpvt_e64244e1e591d0337e17a12b714c0996=1328778308090");
//        System.out.println(sb.toString());
//        String ValidCode = savegif(getMethod);
//        getMethod.releaseConnection();
//        // 第二次链接
//        httpClient.getHostConfiguration().setHost("12582.10086.cn", 80, "http");
//        PostMethod method = getPostMethod(ValidCode);
//        method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
//        method.setRequestHeader("Host", "12582.10086.cn");
//        method.setRequestHeader("Accept", "application/json, text/javascript, */*");
//        method.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
////		method.setRequestHeader("Accept-Encoding", "gzip, deflate");
//        method.setRequestHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//        method.setRequestHeader("Connection", "keep-alive");
//        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        method.setRequestHeader("X-Requested-With", "XMLHttpRequest");
//        method.setRequestHeader("Referer", "http://12582.10086.cn/user/login/");
//        method.setRequestHeader("Cookie", sb.toString());
//        httpClient.executeMethod(method);
//        System.out.println(method.getStatusCode());
//        System.out.println(method.getResponseBodyAsString());
//        // 第三次链接
//        Header header1 = method.getResponseHeader("Set-cookie");
//        System.out.println("dd=" + header1.getValue());
//        sb.append("; " + header1.getValue());
//        Cookie[] cookies = httpClient.getState().getCookies();
//        method.releaseConnection();
        HttpClient httpClient = new HttpClient();
        String my = "http://kaixin65.com/forum.php?mod=viewthread&tid=7574&extra=page%3D1";

        GetMethod getMethod ;
        getMethod = new GetMethod(my);
        getMethod.setRequestHeader("Host", "kaixin65.com");
        getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
        getMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        getMethod.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
        getMethod.setRequestHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        getMethod.setRequestHeader("Connection", "keep-alive");
        getMethod.setRequestHeader("Referer", "http://12582.10086.cn/user/login");
        getMethod.setRequestHeader("Cookie", "yunsuo_session_verify=0b1ebbb8c7cefb1719cb8d690a1e202c; S3rD_2132_saltkey=nSll7Tsi; S3rD_2132_lastvisit=1497347758; S3rD_2132_adclose_5=1; S3rD_2132_adclose_2=1; S3rD_2132_nofocus_forum=1; S3rD_2132_seccode=127.9c68176f8c812d3f51; S3rD_2132_ulastactivity=21c4Jn3YPaag4FCvh7ZB8ZomqrKXE3AVCTwgprVr1Z8Z7wK1jkav; S3rD_2132_auth=9108nyuzYEcn4Iw%2FEB8M04skeBSjebAYq5MxaHF5yySTC3m1exjqGHRVToMbHUIhhVTSPD5xyT2yxELjKazwOWup; S3rD_2132_lastcheckfeed=4088%7C1497352880; S3rD_2132_lip=210.77.180.14%2C1497351064; S3rD_2132_lastact=1497352885%09forum.php%09; S3rD_2132_nofavfid=1; S3rD_2132_study_nge_extstyle=2; S3rD_2132_study_nge_extstyle_default=2; S3rD_2132_sid=ANNnLS; S3rD_2132_noticeTitle=1; Hm_lvt_63b8f8f4895a04769ec9d88bfd02f566=1496398890,1496659462,1496816549,1497351027; Hm_lpvt_63b8f8f4895a04769ec9d88bfd02f566=1497352846; tjpctrl=1497354646614");
        int code = httpClient.executeMethod(getMethod);
        String content = getMethod.getResponseBodyAsString();
        int start =content.indexOf("zoomfile=");
        int end = content.indexOf("class=\"zoom\"");
        System.out.println(content.substring(start,end));
        System.out.println(getMethod.getStatusCode());
        System.out.println(getMethod.getResponseBodyAsString());
        File storeFile = new File("./rst/2008sohu.html");
        FileOutputStream output = new FileOutputStream(storeFile);
        //得到网络资源的字节数组,并写入文件
        output.write(getMethod.getResponseBody());

        output.close();
    }

    public String savegif(GetMethod getMethod) throws IOException {
        File storeFile = new File("c:/2008sohu.gif");
        FileOutputStream output = new FileOutputStream(storeFile);
        //得到网络资源的字节数组,并写入文件
        output.write(getMethod.getResponseBody());
        output.close();
        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String ValidCode = "";
        try {
            ValidCode = br.readLine();
            br.close();
            is.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ValidCode;
    }

    private PostMethod getPostMethod(String ValidCode) {
        PostMethod post = new PostMethod("/ajax/postlogin");
        NameValuePair[] simcard = {
                new NameValuePair("email", "XXX"),
                new NameValuePair("password", "XXX"),
                new NameValuePair("ValidCode", ValidCode),
                new NameValuePair("rme", "0"),
        };
        post.setRequestBody(simcard);
        return post;
    }

    public static void main(String args[]) {
        LoginCookieCopyUtil _10086 = new LoginCookieCopyUtil();
        try {
            _10086.test();
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
