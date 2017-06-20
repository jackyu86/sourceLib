package com.zhao.crawler.util;

import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


//http://www.ai010.net/
//http://www.xiancha123.org/forum.php


public class GetPage4xiancha {

    public void test1(){
        for (int i =20000; i<50000;i++) {
            String url = "http://www.xiancha123.org/thread-" + i + "-1-1.html";
            System.out.println(url);
            HttpResponse response = null;

            try {
            HttpRequesterImpl httpRequester = new HttpRequesterImpl();
            httpRequester.addHeader("Host", "www.xiancha123.org");
            httpRequester.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.7 Safari/537.36");
            httpRequester.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpRequester.addHeader("Accept-Language", "zh-cn,zh;q=0.5");
            httpRequester.addHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
            httpRequester.addHeader("Connection", "keep-alive");
            httpRequester.addHeader( "X-DevTools-Emulate-Network-Conditions-Client-Id","C0352484-33D7-4A3A-A4C9-00BCB345B031");
            httpRequester.addHeader( "Upgrade-Insecure-Requests","1");
                httpRequester.addHeader( "DNT","1");
            //httpRequester.addHeader("Cookie", "yunsuo_session_verify=0b1ebbb8c7cefb1719cb8d690a1e202c; S3rD_2132_saltkey=nSll7Tsi; S3rD_2132_lastvisit=1497347758; S3rD_2132_adclose_5=1; S3rD_2132_adclose_2=1; S3rD_2132_nofocus_forum=1; S3rD_2132_seccode=127.9c68176f8c812d3f51; S3rD_2132_ulastactivity=21c4Jn3YPaag4FCvh7ZB8ZomqrKXE3AVCTwgprVr1Z8Z7wK1jkav; S3rD_2132_auth=9108nyuzYEcn4Iw%2FEB8M04skeBSjebAYq5MxaHF5yySTC3m1exjqGHRVToMbHUIhhVTSPD5xyT2yxELjKazwOWup; S3rD_2132_lastcheckfeed=4088%7C1497352880; S3rD_2132_lip=210.77.180.14%2C1497351064; S3rD_2132_lastact=1497352885%09forum.php%09; S3rD_2132_nofavfid=1; S3rD_2132_study_nge_extstyle=2; S3rD_2132_study_nge_extstyle_default=2; S3rD_2132_sid=ANNnLS; S3rD_2132_noticeTitle=1; Hm_lvt_63b8f8f4895a04769ec9d88bfd02f566=1496398890,1496659462,1496816549,1497351027; Hm_lpvt_63b8f8f4895a04769ec9d88bfd02f566=1497352846; tjpctrl=1497354646614");
            httpRequester.setCookie("fDyj_2132_saltkey=C7AR559g; fDyj_2132_lastvisit=1497869644; fDyj_2132_ulastactivity=6dbadeQ%2F1k8VetL%2Fy1INrOh6Z8bNyncskXE9lsJrYHrWPAE%2BpOuA; fDyj_2132_auth=76dfmrWvBseZ9vlYTbb%2FUPIYo%2BSZHGwiIiBZfUz7W3hMyIPK8ceQ5f4iPzdQDt0FTixLNAod0EQr7M3%2FeNXPOMlk7Q; fDyj_2132_lastcheckfeed=72611%7C1497873253; fDyj_2132_lip=210.77.180.14%2C1497871864; fDyj_2132_member_login_status=1; fDyj_2132_home_diymode=1; fDyj_2132_nofavfid=1; fDyj_2132_atarget=1; fDyj_2132_forum_lastvisit=D_40_"+(1497874609+i)+"; fDyj_2132_visitedfid=50D40; fDyj_2132_viewid=tid_24614; fDyj_2132_sid=s1BNWR; Hm_lvt_ef6071de284645b5fb2dee7870f09876=1497871797; Hm_lpvt_ef6071de284645b5fb2dee7870f09876=1497874676; fDyj_2132_smile=1D1; fDyj_2132_noticeTitle=1; tjpctrl=1497876476849; fDyj_2132_lastact=1497874686%09forum.php%09misc; __cfduid=d8249ed69c93d529fdca845d37adce0bf1497871793");
            response = httpRequester.getResponse(url);
            String html = response.getHtml("gbk");
                Document doc = Jsoup.parse(html);
                List<Element> eles =  doc.getElementsByAttributeValue("class","t_fsz");

            if (!eles.isEmpty()) {
                for (Element ele : eles) {

                    System.out.println(" ### " + ele.text());

                }
            } else {
                System.out.println("else is empty");
            }

            //zoom
            //List<WebElement> photoEles = driver.findElements(By.className("zoom"));
                //>ignore_js_op>img
                //List<WebElement> photoEles = driver.findElements(By.cssSelector("ignore_js_op"));
                List<Element> photoEles =  doc.getElementsByAttributeValue("class","zoom");
            //List<WebElement> photoEles = driver.findElements(By.xpath("//ignore_js_op"));

            if (!photoEles.isEmpty()) {
                int j = 0;
                for (Element ele : photoEles) {
                    j++;

                   String phoUrl = ele.attr("file");
                    System.out.println(" ### " + phoUrl);

                    try {
                        //HttpURLConnectionUtil.saveData(HttpURLConnectionUtil.getInputStreamByGet(phoUrl), new File("./rst/" + page.getUrl().split("&")[1].split("=")[1] + "_" + j + ".jpg"));
                        savegif("http://www.xiancha123.org/"+phoUrl,"xianchatid_"+i+"_"+j+".jpg");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else {
                System.out.println("else is empty");
            }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



}

    public  void test() throws HttpException, IOException{

        HttpClient httpClient = new HttpClient();

        for (int i =5000; i<9000;i++) {
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
            savegif("http://kaixin65.com/"+url,i+"");

            getMethod.releaseConnection();


        }
    }

    public void savegif(String url,String filename) throws IOException {
        try {
            HttpURLConnectionUtil.saveData(HttpURLConnectionUtil.getInputStreamByGet(url),new File("./rst/"+filename));
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
        GetPage4xiancha _10086 = new GetPage4xiancha();
        try {
            _10086.test1();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
