package com.zhao.crawler.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.*;

public class GetPageByCookie {

    public  void test() throws HttpException, IOException{

        HttpClient httpClient = new HttpClient();

        for (int i =1120; i<5000;i++) {
            String my = "http://kaixin65.com/forum.php?mod=viewthread&tid=" + i + "&extra=page%3D1";

            GetMethod getMethod=new GetMethod(my);
            String url = null;
            try {
                System.out.println(my);
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
                int start = content.indexOf("zoomfile=");
                int end = content.indexOf("class=\"zoom\"");
                url = content.substring(start, end).split("\"")[1];
                System.out.println(content.substring(start,end));
                File storeFile = new File("./rst/"+i+".html");
                FileOutputStream output = new FileOutputStream(storeFile);
                //得到网络资源的字节数组,并写入文件
                output.write(getMethod.getResponseBody());

                output.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                getMethod.releaseConnection();
            }


//            getMethod = new GetMethod("http://kaixin65.com/"+url );
//            getMethod.setRequestHeader("Host", "kaixin65.com");
//            getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
//            getMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            getMethod.setRequestHeader("Accept-Language", "zh-cn,zh;q=0.5");
//            getMethod.setRequestHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//            getMethod.setRequestHeader("Connection", "keep-alive");
//            getMethod.setRequestHeader("Referer", "http://12582.10086.cn/user/login");
//            getMethod.setRequestHeader("Cookie", "yunsuo_session_verify=0b1ebbb8c7cefb1719cb8d690a1e202c; S3rD_2132_saltkey=nSll7Tsi; S3rD_2132_lastvisit=1497347758; S3rD_2132_adclose_5=1; S3rD_2132_adclose_2=1; S3rD_2132_nofocus_forum=1; S3rD_2132_seccode=127.9c68176f8c812d3f51; S3rD_2132_ulastactivity=21c4Jn3YPaag4FCvh7ZB8ZomqrKXE3AVCTwgprVr1Z8Z7wK1jkav; S3rD_2132_auth=9108nyuzYEcn4Iw%2FEB8M04skeBSjebAYq5MxaHF5yySTC3m1exjqGHRVToMbHUIhhVTSPD5xyT2yxELjKazwOWup; S3rD_2132_lastcheckfeed=4088%7C1497352880; S3rD_2132_lip=210.77.180.14%2C1497351064; S3rD_2132_lastact=1497352885%09forum.php%09; S3rD_2132_nofavfid=1; S3rD_2132_study_nge_extstyle=2; S3rD_2132_study_nge_extstyle_default=2; S3rD_2132_sid=ANNnLS; S3rD_2132_noticeTitle=1; Hm_lvt_63b8f8f4895a04769ec9d88bfd02f566=1496398890,1496659462,1496816549,1497351027; Hm_lpvt_63b8f8f4895a04769ec9d88bfd02f566=1497352846; tjpctrl=1497354646614");
            savegif(url,i);

            getMethod.releaseConnection();


        }
    }

    public void savegif(String url,int i) throws IOException {
        try {
            HttpURLConnectionUtil.saveData(HttpURLConnectionUtil.getInputStreamByGet("http://kaixin65.com/"+url),new File("./rst/oooo"+i+".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        GetPageByCookie _10086 = new GetPageByCookie();
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
