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

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 *
 * @author <a href="ls.zhaoxiangyu@gmail.com">lj</>
 * @date 2017年6月2日
 */
public class CookieUtil {
	
	
	 /**
     * 模拟csdn登录，获取登录后的cookies保存到文件
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param geckodriverpath
     *            gecko的路径（见https://github.com/mozilla/geckodriver）
     * @param savecookiepath
     *            cookies保存的路径
     * @throws Exception
     */
    public static void firfoxDriverGetCookies(String username, String password, String geckodriverpath,
            String savecookiepath) throws Exception {
        // 初始化参数据
        System.setProperty("webdriver.gecko.driver", geckodriverpath);
        FirefoxDriver driver = new FirefoxDriver();
        String baseUrl = "http://kaixin65.com/member.php?mod=logging&action=login&infloat=yes&handlekey=login&inajax=1&ajaxtarget=fwin_content_login";
        // 加载url
        driver.get(baseUrl);
        // 等待加载完成
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // 获取页面元素
        WebElement elemUsername = driver.findElement(By.name("username"));
        WebElement elemPassword = driver.findElement(By.name("password"));
        WebElement btn = driver.findElement(By.className("logging"));
        WebElement rememberMe = driver.findElement(By.id("rememberMe"));
        // 操作页面元素
        elemUsername.sendKeys(username);
        elemPassword.sendKeys(password);
        rememberMe.click();
        // 提交表单
        btn.submit();
        Thread.sleep(5000);
        driver.get("http://msg.csdn.net/");
        Thread.sleep(5000);
        // 获取cookies
        driver.manage().getCookies();
        Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
        System.out.println("Size: " + cookies.size());
        Iterator<org.openqa.selenium.Cookie> itr = cookies.iterator();

        CookieStore cookieStore = new BasicCookieStore();

        while (itr.hasNext()) {
            Cookie cookie = itr.next();
            BasicClientCookie bcco = new BasicClientCookie(cookie.getName(), cookie.getValue());
            bcco.setDomain(cookie.getDomain());
            bcco.setPath(cookie.getPath());
            cookieStore.addCookie(bcco);
        }
        // 保存到文件
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(savecookiepath)));
        oos.writeObject(cookieStore);
        oos.close();

    }

}
