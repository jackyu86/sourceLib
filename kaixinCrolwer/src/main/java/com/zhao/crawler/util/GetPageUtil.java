package com.zhao.crawler.util;

import cn.edu.hfut.dmic.webcollector.net.HttpRequesterImpl;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

public class GetPageUtil {

    public void getPage(String urlHead, String urlTail,  Map<String,String> header,String cookie,int pageStart,int pageEnd){
        for (int i =pageStart; i<pageEnd;i++) {
            String url = urlHead + i + urlTail;
            System.out.println(url);
            HttpResponse response = null;

            try {
            HttpRequesterImpl httpRequester = new HttpRequesterImpl();

                for (Map.Entry<String, String> entry : header.entrySet())
                {
                    httpRequester.addHeader( (String)entry.getKey(), (String)entry.getValue());
                }


                 httpRequester.setCookie( "4bd54_threadlog=%2C18%2C; 4bd54_c_stamp="+1498470782+i*1000+"; 4bd54_lastpos=T1528794; 4bd54_lastvisit=74%091498470782%09%2Fbbs%2Fread.php%3Ftid1528794; 4bd54_ol_offset=306908; 4bd54_ipstate=1498470782; 4bd54_readlog=%2C1528049%2C1528794%2C; sc_is_visitor_unique=rx4629288.1498470903.AA90513BD9364F6617BBC6B1CA34EEAB.3.3.3.3.3.3.3.3.2");


                 response = httpRequester.getResponse(url);

            String html = response.getHtml("gbk");
               // System.out.println(html);
                Document doc = Jsoup.parse(html);
                String body = doc.getElementsByTag("body").toString();
               // System.out.println(body);
            if(body.indexOf("请刷新页面或按键盘F5")>0)
            {
                i--;
                continue;
            }

                String title = doc.getElementsByTag("title").toString();
            if (title.indexOf("网友自拍")>0)
            {
                //List<Element> eles =  doc.getElementsByAttributeValue("class","t_fsz");
                List<Element> eles = doc.getElementsByAttributeValue("class", "f14 mb10");

                if (!eles.isEmpty()) {
                    int j = 1;
                    for (Element ele : eles) {

                        Iterator iterator =  ele.children().iterator();
                        String gif="";
                        Element el = null;
                        while( iterator.hasNext()) {
                           try {
                               el = ((Element)iterator.next()).getElementsByAttribute("src").get(0);
                               j++;
                               gif =el.toString().split("\"")[1];
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                           System.out.println(" ### " + gif);
                          savegif(gif, "xiao77tid_" + i + "_" + j + ".jpg");
                       }
                    }
                } else {
                    System.out.println("else is empty");
                }

                //zoom
                //List<WebElement> photoEles = driver.findElements(By.className("zoom"));
                //>ignore_js_op>img
                //List<WebElement> photoEles = driver.findElements(By.cssSelector("ignore_js_op"));
//                List<Element> photoEles = doc.getElementsByAttributeValue("class", "zoom");
//                //List<WebElement> photoEles = driver.findElements(By.xpath("//ignore_js_op"));
//
//                if (!photoEles.isEmpty()) {
//                    int j = 0;
//                    for (Element ele : photoEles) {
//                        j++;
//
//                        String phoUrl = ele.attr("file");
//                        System.out.println(" ### " + phoUrl);
//
//                        try {
//                            //HttpURLConnectionUtil.saveData(HttpURLConnectionUtil.getInputStreamByGet(phoUrl), new File("./rst/" + page.getUrl().split("&")[1].split("=")[1] + "_" + j + ".jpg"));
//                            savegif("http://kaixin65.com/" + phoUrl, "tid_" + i + "_" + j + ".jpg");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                } else {
//                    System.out.println("else is empty");
//                }
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
        if (!"".equals(url)
                &&url != null) {
            try {
                HttpURLConnectionUtil.saveData(HttpURLConnectionUtil.getInputStreamByGet(url), new File("./rst/" + filename));
            } catch (Exception e) {
                e.printStackTrace();
            }
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


//
//    Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//Accept-Encoding:gzip, deflate
//Accept-Language:zh-CN,zh;q=0.8
//Cache-Control:max-age=0
//Connection:keep-alive
//Cookie:4bd54_lastpos=T1527108; 4bd54_ol_offset=616629; 4bd54_c_stamp=1497847174; 4bd54_lastvisit=920%091497847174%09%2Fbbs%2Fread.php%3Ftid1527108; sc_is_visitor_unique=rx4629288.1497847292.2A51302650114FDACD988C1AD3E3732A.1.1.1.1.1.1.1.1.1


//DNT:1
//Host:x77320.com
//Upgrade-Insecure-Requests:1
//User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.7 Safari/537.36
//X-DevTools-Emulate-Network-Conditions-Client-Id:3D9E4A2C-BA81-495E-B078-EB8FB3F478D2




    public static void main(String args[]) {
        GetPageUtil _10086 = new GetPageUtil();
        Map<String, String>  header = new HashedMap();

//        header.put("Host", "kaixin65.com");
//        header.put("User-Agent", "Mozilla/5.0 (Windows NT 5.2; rv:5.0.1) Gecko/20100101 Firefox/5.0.1");
//        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        header.put("Accept-Language", "zh-cn,zh;q=0.5");
//        header.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
//        header.put("Connection", "keep-alive");
//
//        String cookie = "yunsuo_session_verify=0b1ebbb8c7cefb1719cb8d690a1e202c; S3rD_2132_saltkey=nSll7Tsi; S3rD_2132_lastvisit=1497347758; S3rD_2132_adclose_5=1; S3rD_2132_adclose_2=1; S3rD_2132_nofocus_forum=1; S3rD_2132_seccode=127.9c68176f8c812d3f51; S3rD_2132_ulastactivity=21c4Jn3YPaag4FCvh7ZB8ZomqrKXE3AVCTwgprVr1Z8Z7wK1jkav; S3rD_2132_auth=9108nyuzYEcn4Iw%2FEB8M04skeBSjebAYq5MxaHF5yySTC3m1exjqGHRVToMbHUIhhVTSPD5xyT2yxELjKazwOWup; S3rD_2132_lastcheckfeed=4088%7C1497352880; S3rD_2132_lip=210.77.180.14%2C1497351064; S3rD_2132_lastact=1497352885%09forum.php%09; S3rD_2132_nofavfid=1; S3rD_2132_study_nge_extstyle=2; S3rD_2132_study_nge_extstyle_default=2; S3rD_2132_sid=ANNnLS; S3rD_2132_noticeTitle=1; Hm_lvt_63b8f8f4895a04769ec9d88bfd02f566=1496398890,1496659462,1496816549,1497351027; Hm_lpvt_63b8f8f4895a04769ec9d88bfd02f566=1497352846; tjpctrl=1497354646614";


        header.put("Host", "x77320.com");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.7 Safari/537.36");
        header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Language", "zh-cn,zh;q=0.8");
        header.put("Connection", "keep-alive");




        try {
            _10086.getPage("http://x77320.com/bbs/read.php?tid=","",
                    header,
                    null,
                    1389476,
                    1489476);
           //sleep(10000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
