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

import java.io.InputStream; 
import java.io.OutputStreamWriter; 
import java.net.HttpURLConnection; 
import java.net.URL; 
import java.util.List; 
import java.util.Map; 

public class MyNewTopic { 

private static URL url; 
private static HttpURLConnection con; 
private static String temp; 
private static InputStream is; 
private static byte[] b; 
private static int pos; 
private static String cookie_sid; 
private static String cookie_auth; 
private static String my_cookie; 
private static String login_formhash; 
private static String post_formhash; 
private static String user = "test";// 用户名 
private static String pass = "test";// 密码 
private static String new_fid = "11";// 版块 ID 
private static String subject = "新主题";// 标题 
private static String msg = "这里是新主题的内容";// 帖子内容 

public static void main(String[] args) { 
// TODO Auto-generated method stub 
try { 

// 获取 cookie_sid 和 login_formhash -------------------- 
url = new URL("http://192.168.72.130/bbs/logging.php?action=login"); 
con = (HttpURLConnection) url.openConnection(); 
if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { 
// 获取服务器发给客户端的 Cookie 
temp = con.getHeaderField("Set-Cookie"); 
System.out.println("Set-Cookie:" + temp); 
// 取 Cookie 前面的部分就可以了，后面是过期时间、路径等，不用管它 
cookie_sid = temp.substring(0, 14); 
System.out.println(cookie_sid); 

is = con.getInputStream(); 
b = new byte[is.available()]; 
is.read(b); 
// 服务器会返回一个页面，此页面中包含 formhash 
temp = new String(b); 
// 找出这个 formhash 的位置 
pos = temp.indexOf("name=\"formhash\" value="); 
// System.out.println(temp); 
// 找出这个 formhash 的内容，这是登录用的 formhash 
login_formhash = temp.substring(pos + 23, pos + 23 + 8); 
System.out.println("login_formhash:" + login_formhash); 
System.out 
.println("------------------------------------------------------------"); 
is.close(); 
} 

// 获取cookie_auth ----------------------------------------------- 
url = new URL("http://192.168.72.130/bbs/logging.php"); 
con = (HttpURLConnection) url.openConnection(); 

// 设定以 POST 发送 
con.setRequestMethod("POST"); 
// 加入 Cookie 内容 
con.setRequestProperty("Cookie", cookie_sid); 
// 添加 POST 的内容 
con.setDoOutput(true); 
OutputStreamWriter osw = new OutputStreamWriter(con 
.getOutputStream()); 
osw 
.write("action=login&loginfield=username&questionid=0&answer=&loginsubmit=yes&formhash=" 
+ login_formhash 
+ "&username=" 
+ user 
+ "&password=" + pass); 
osw.flush(); 
osw.close(); 

if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { 
Map<String, List<String>> map = con.getHeaderFields(); 
List<String> list = map.get("Set-Cookie"); 
for (int i = 0; i < list.size(); i++) { 
temp = list.get(i); 
if (temp.contains("CsN_auth")) { 
System.out.println(temp); 
// 取 Cookie 前面的部分就可以了，后面是过期时间、路径等，不用管它 
cookie_auth = temp.split(";")[0]; 
System.out.println("cookie_auth:" + cookie_auth); 
} 
} 

is = con.getInputStream(); 
byte[] b = new byte[is.available()]; 
is.read(b); 
// System.out.println(new String(b)); 
System.out 
.println("------------------------------------------------------------"); 
is.close(); 
} 

// 正式登录 
url = new URL( 
"http://192.168.72.130/bbs/post.php?action=newthread&fid=" 
+ new_fid); 
HttpURLConnection con = (HttpURLConnection) url.openConnection(); 

my_cookie = cookie_sid + ";" + cookie_auth; 
System.out.println(my_cookie); 
// 加入 Cookie 内容 
con.setRequestProperty("Cookie", my_cookie); 

if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { 
is = con.getInputStream(); 
byte[] b = new byte[is.available()]; 
is.read(b); 
temp = new String(b); 
pos = temp.indexOf("id=\"formhash\" value="); 
// System.out.println(temp); 
// 获得发帖用的 formhash 
post_formhash = temp.substring(pos + 21, pos + 21 + 8); 
System.out.println("post_formhash:" + post_formhash); 
System.out 
.println("------------------------------------------------------------"); 
is.close(); 
} 

// 发新帖子 
url = new URL("http://192.168.72.130/bbs/post.php"); 
con = (HttpURLConnection) url.openConnection(); 

con.setRequestMethod("POST"); 
con.setRequestProperty("Cookie", my_cookie); 
con.setDoOutput(true); 
osw = new OutputStreamWriter(con.getOutputStream()); 
temp = "action=newthread&topicsubmit=yes&subject=" + subject 
+ "&formhash=" + post_formhash + "&fid=" + new_fid 
+ "&message=" + msg; 
osw.write(temp); 
osw.flush(); 
osw.close(); 

if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { 
is = con.getInputStream(); 
byte[] b = new byte[is.available()]; 
is.read(b); 
// System.out.println(new String(b)); 
System.out 
.println("------------------------------------------------------------"); 
is.close(); 
} 

} catch (Exception e) { 
e.printStackTrace(); 
} 

} 
} 