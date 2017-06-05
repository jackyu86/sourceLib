/*
 * Copyright (C) 2015 lj
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.zhao.crawler.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 *
 * @author <a href="ls.zhaoxiangyu@gmail.com">lj</>
 * @date 2017年6月2日
 */
public class HttpURLConnectionUtil {
	
    // 通过get请求得到读取器响应数据的数据流
 public static InputStream getInputStreamByGet(String url) {
     try {
         HttpURLConnection conn = (HttpURLConnection) new URL(url)
                 .openConnection();
         conn.setReadTimeout(5000);
         conn.setConnectTimeout(5000);
         conn.setRequestMethod("GET");

         if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
             InputStream inputStream = conn.getInputStream();
             return inputStream;
         }

     } catch (IOException e) {
         e.printStackTrace();
     }
     return null;
 }

     // 将服务器响应的数据流存到本地文件
 public static void saveData(InputStream is, File file) {
     try (
    		 
     BufferedInputStream bis = new BufferedInputStream(is);
     BufferedOutputStream bos = new BufferedOutputStream(
                     new FileOutputStream(file));) 
     {
         byte[] buffer = new byte[1024];
         int len = -1;
         while ((len = bis.read(buffer)) != -1) {
             bos.write(buffer, 0, len);
             bos.flush();
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
 }

}
