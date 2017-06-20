package ly.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: cnprinces
 * Date: 12-8-14
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public class PhotoDownLoad {
    class Photo {
        private Album album;
        private String url;
        private String name;
        //getter and setter

        public Album getAlbum() {
            return album;
        }

        public void setAlbum(Album album) {
            this.album = album;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Album {
        private String id;
        private String name;
        private int cnt; // 图片张数

        public Album() {
        }

        /**
         * @param id
         * @param name
         * @param cnt
         */
        public Album(String id, String name, int cnt) {
            super();
            this.id = id;
            this.name = name;
            this.cnt = cnt;
        }
        //getter and setter

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCnt() {
            return cnt;
        }

        public void setCnt(int cnt) {
            this.cnt = cnt;
        }
    }


    /*
     * sample uri: album:
     * http://xalist.photo.qq.com/fcgi-bin/fcg_list_album?uin=
     * 419758768&outstyle=2&t=0.7253889227680141&g_tk=5381 photo:
     * http://xaplist.
     * photo.qq.com/fcgi-bin/fcg_list_photo?uin=419758768&albumid=
     * e3b63b1c-2dce-4f86-ad02-c0f81a878615
     * &outstyle=json&t=0.9962191131376609&g_tk=5381
    */
    // private static final String albumbase ="http://xalist.photo.qq.com/fcgi-bin/fcg_list_album?uin=";
    private static final String albumbase1 ="http://alist.photo.qq.com/fcgi-bin/fcg_list_album?uin=";//如果没有设置密保的相册是通过这个地址访问的
    private static final String albumbase2 ="http://xalist.photo.qq.com/fcgi-bin/fcg_list_album?uin=";//设置密保的相册是通过这个地址访问的

    //private static final String photobase ="http://alist.photo.qq.com/fcgi-bin/fcg_list_photo?uin=";
    private static final String photobase1 ="http://plist.photo.qq.com/fcgi-bin/fcg_list_photo?uin=";
    private static final String photobase2 ="http://xaplist.photo.qq.com/fcgi-bin/fcg_list_photo?uin=";

    private static final String charset ="gb2312";
    private static List<Album> albums; // 获取的所有相册
    private static final String savePath ="e://qqPhoto//"; // 图片保存位置的父目录
    private int curIndex = 0; // 每个相册当前正在下载的图片指针

    /**
     * 获取用户相册
     *
     * @param qq
     * @return
     */
    public List<Album> getAlbums(String qq, String url) {
        List<Album> result = new ArrayList<Album>();
        HttpClient client = new HttpClient();
        String getUri = url + qq +"&outstyle=2"; // outstyle!=2服务器将以xml的形式返回结果,
        // 这里只以简单文本解析提取相关信息,不做xml解析了.
        HttpMethod method = new GetMethod(getUri);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                charset);
        int status = 0;
        try {
            status = client.executeMethod(method);
            if (status != HttpStatus.SC_OK) {
                System.err.println("发生网络错误!");
                return null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        InputStream is = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        List<String> ids = new ArrayList<String>();
        List<String> names = new ArrayList<String>();
        List<Integer> totals = new ArrayList<Integer>();
        try {
            is = method.getResponseBodyAsStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String temp = null;
            while ((temp = br.readLine()) != null) {
                if (temp.contains("\"id\":")) {
                    String id = temp.substring(temp.indexOf("\"id\":") + 8,
                            temp.length() - 2);
                    ids.add(id);
                }
                if (temp.contains("\"name\":")) {
                    String name = temp.substring(
                            temp.indexOf("\"name\":") + 10, temp.length() - 3);
                    names.add(name);
                }
                if (temp.contains("\"total\":")) {
                    String total = temp
                            .substring(temp.indexOf("\"total\":") + 10,
                                    temp.length() - 1);
                    totals.add(Integer.parseInt(total));
                }
                if (temp.contains("\"left\":")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < ids.size(); i++) {
            Album album = new Album(ids.get(i), names.get(i), totals.get(i));
            result.add(album);
        }
        return result;
    }

    /**
     * 获取一个相册的图片信息
     *
     * @param album 相册的信息
     * @param qq qq
     * @param phpUrl 地址
     * @return
     */
    public List<Photo> getPhotoByAlbum(Album album, String qq, String phpUrl) {
        List<Photo> result = new ArrayList<Photo>();
        HttpClient client = new HttpClient();
        System.out.println("相册名字:"+ album.getId());
        String getUri = phpUrl + qq +"&albumid="+ album.getId()
                +"&outstyle=json";
        HttpMethod method = new GetMethod(getUri);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
                charset);
        int status = 0;
        try {
            status = client.executeMethod(method);
            if (status != HttpStatus.SC_OK) {
                System.err.println("发生网络错误!");
                return null;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        InputStream is = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        List<String> names = new ArrayList<String>();
        List<String> urls = new ArrayList<String>();
        try {
            is = method.getResponseBodyAsStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String temp = null;
            boolean sign = false;
            while ((temp = br.readLine()) != null) {
                int len = temp.length();
                if (temp.contains("\"pic\": [")) {
                    sign = true;
                }
                if (sign && temp.contains("\"name\":")) {
                    String name = temp.substring(
                            temp.indexOf("\"name\":") + 10, len - 2);
                    names.add(name);
                }
                if (temp.contains("\"url\":")) {
                    String url = temp.substring(temp.indexOf("\"url\":") + 9,
                            len - 3);
                    urls.add(url);
                }
                if (temp.contains("],")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < names.size(); i++) {
            Photo photo = new Photo();
            photo.setName(names.get(i));
            photo.setUrl(urls.get(i));
            photo.setAlbum(album);
            result.add(photo);
        }
        return result;
    }

    /**
     * 通过uri下载远程图片到本地
     *
     * @param
     * @param
     */
    public void saveImgFromUrl(Photo photo, String qq) {
        URL imgurl = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            // imgurl = new URL(photo.getUrl());
            String phpUrl = photo.getUrl();
            phpUrl = phpUrl.replace("","");
            imgurl = new URL(phpUrl);
            bis = new BufferedInputStream(imgurl.openStream());
            byte[] buffer = new byte[512];
            File path = new File(savePath +"//"+ qq +"//"
                    + photo.getAlbum().getName().trim());
            if (!path.exists()) {
                path.mkdirs();
            }
            os = new FileOutputStream(new File(path, photo.getName().trim()
                    +".jpeg"));
            int len = 0;
            while ((len = bis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载一个相册的图片
     *
     * @param index 相册序号
     */
    public void savePhoto(final int index, final String qq) {
        Album album = albums.get(index);
        if(album.getName().indexOf("微博")>=0){
            System.out.println("微博相册不下载");
            return;
        }
        List<Photo> photosTemp = this.getPhotoByAlbum(album, qq, photobase1);
        if (photosTemp == null || photosTemp.size() == 0) {
            photosTemp = this.getPhotoByAlbum(album, qq, photobase2);
        }
        if (photosTemp == null || photosTemp.size() == 0) {
            System.out.println("相册信息为空");
            return;
        } else {
            final List<Photo> photos = photosTemp;


            final int maxThreadCnt = 10; // 每个相册最多开启10个线程进行下载
            final int total = album.getCnt();
            int realThreadCnt = total >= maxThreadCnt ? maxThreadCnt : total; // 实际下载一个相册的线程数
/**
 * 线程驱动下载任务
 *
 * @author wensefu.jerry.Ling<br/>
 * wrote on 2011-1-29
 */
            class DownLoadTask implements Runnable {
                int id; // 线程标识
                int pindex;// 下载的图片指针

                public DownLoadTask(int id, int pindex) {
                    this.id = id;
                    this.pindex = pindex;
                }

                public void run() {
                    while (curIndex <= total - 1) {
                        int temp = pindex;
                        pindex = curIndex;
                        curIndex++;
                        Photo photo = photos.get(temp);
                        System.out.println("线程"+ (index + 1) +"_"+ id +"开始下载第"+ (index + 1) +"个相册第"+ (pindex + 1) +"张图片...");
                        saveImgFromUrl(photo, qq);
                        System.out.println("线程"+ (index + 1) +"_"+ id +"完成第"+ (index + 1) +"个相册第"+ (pindex + 1) +"张图片下载");
                    }
                }
            }
            ExecutorService exec = Executors.newCachedThreadPool();
/*
 * 初始化各线程状态 此处给每个线程分配一个下载起始点
*/
            for (int i = 0; i < realThreadCnt; i++) {
                DownLoadTask task = new DownLoadTask(i + 1, i);
                exec.execute(task);
            }
            exec.shutdown();
        }
    }

    public static void main(String[] args) {
        PhotoDownLoad pdl = new PhotoDownLoad();
        String qq ="312397789";
        albums = pdl.getAlbums(qq, albumbase1);
        if (albums == null || albums.size() == 0) {
            albums = pdl.getAlbums(qq, albumbase2);
        }
        if (albums == null || albums.size() == 0) {
            System.out.println("没有获取到相册");
        }
        int len = albums.size();
        System.out.println("相册信息获取成功,用户共有"+ len +"个相册.");
        for (int i = 0; i < len; i++) { // 考虑到相册数量不会很多,相册采用顺序下载，不使用异步下载
            System.out.println("开始下载第"+ (i + 1) +"个相册...");
            pdl.savePhoto(i, qq);
            pdl.curIndex = 0;
            System.out.println("第"+ (i + 1) +"个相册下载完成.");
        }
    }
}