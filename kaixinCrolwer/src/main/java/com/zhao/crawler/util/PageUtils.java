/*
 * Copyright (C) 2015 zhao
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * 
 * 
 * @author <a href="ls.zhaoxiangyu@gmail.com">zhao</>
 * @date 2015-10-22
 */
public class PageUtils {
	
	/**
	 * 获取webcollector 自带 htmlUnitDriver实例(模拟默认浏览器)
	 *
	 * @param page
	 * @return
	 */
	public static HtmlUnitDriver getDriver(Page page) {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get(page.getUrl());
		return driver;
	}

	/**
	 * 获取webcollector 自带htmlUnitDriver实例 
	 *
	 * @param page
	 * @param browserVersion 模拟浏览器
	 * @return
	 */
	public static HtmlUnitDriver getDriver(Page page,
			BrowserVersion browserVersion) {
		HtmlUnitDriver driver = new HtmlUnitDriver(browserVersion);
		driver.setJavascriptEnabled(true);
		driver.get(page.getUrl());
		return driver;
	}
	
	/**
	 * 获取PhantomJsDriver(可以爬取js动态生成的html)
	 *
	 * @param page
	 * @return
	 */
	public static WebDriver getWebDriver(Page page) {
//    	WebDriver driver = new HtmlUnitDriver(true);
    	
//    	System.setProperty("webdriver.chrome.driver", "./chromedriver_x64.exe");
//    	WebDriver driver = new ChromeDriver();
    	
    	System.setProperty("phantomjs.binary.path", "./phantomjs.exe");
    	WebDriver driver = new PhantomJSDriver();
    	driver.get(page.getUrl());
    	
//    	JavascriptExecutor js = (JavascriptExecutor) driver;
//    	js.executeScript("function(){}");
    	return driver;
    }
	
	/**
	 * 直接调用原生phantomJS(即不通过selenium)
	 *
	 * @param page
	 * @return
	 */
	public static String getPhantomJSDriver(Page page) {
    	Runtime rt = Runtime.getRuntime();
    	Process process = null;
    	try {
			process = rt.exec("C:/nevi/env/phantomjs-2.1.1-windows/bin/phantomjs.exe" +
			"C:/dev/nevi/workspace/WebCollectorDemo/src/main/resources/parser.js " +
			page.getUrl().trim());
			InputStream in = process.getInputStream();
			InputStreamReader reader = new InputStreamReader(
					in, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sbf = new StringBuffer();
			String tmp = "";
			while((tmp = br.readLine())!=null){    
                sbf.append(tmp);    
            }
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
}
